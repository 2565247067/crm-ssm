package com.bjpowernode.crm.commons.domain;

/**
 * @description:
 * @author: 25652
 * @time: 2022/5/27 21:49
 */
public class ReturnObject {

    private String code; //处理成功或者失败的标记 1--成功  0--失败

    private String message; //提示信息

    private Object reData;//其他数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReData() {
        return reData;
    }

    public void setReData(Object reData) {
        this.reData = reData;
    }
}
