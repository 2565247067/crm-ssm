package com.bjpowernode.crm.settings.web.controller;


import com.bjpowernode.crm.commons.constant.Constants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * url要和controller方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //封装参数
        Map<String,Object> map =new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service方法去查询用户
        User user = userService.queryUserByLoginActAndPwd(map);
        //根据查询结果生成响应信息
        ReturnObject returnObject = new ReturnObject();
        if(user==null){
            //用户名或密码错误
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        }else {
            //进一步判断账号是否合法

            //格式化当前时间
            String nowday = DateUtils.formateDateTime(new Date());

            if(nowday.compareTo(user.getExpireTime())>0){
                //登陆失败---账号过期
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号过期");

            }else if("0".equals(user.getLockState())){
                //登陆失败---状态被锁定
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("状态被锁定");

            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //登陆失败---IP受限
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("IP受限");
            }else {
                //登陆成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);

                //将user对象放入session中
                session.setAttribute(Constants.SESSION_USER,user);

                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie cookie1 = new Cookie("loginAct", loginAct);
                    cookie1.setMaxAge(24*10*60*60);
                    Cookie cookie2 = new Cookie("loginPwd", loginPwd);
                    cookie2.setMaxAge(24*10*60*60);
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }else {
                    //把没有过期的cookie删除
                    Cookie cookie1 = new Cookie("loginAct", loginAct);
                    cookie1.setMaxAge(0);
                    Cookie cookie2 = new Cookie("loginPwd", loginPwd);
                    cookie2.setMaxAge(0);
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }
            }
        }
        return  returnObject;
    }

}
