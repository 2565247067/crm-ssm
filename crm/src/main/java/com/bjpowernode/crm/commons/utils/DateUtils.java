package com.bjpowernode.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:对Date类型数据进行处理
 * @author: 25652
 * @time: 2022/5/28 11:19
 */
public class DateUtils {

    /**
     * @description: 对指定对象进行格式化 yyyy-MM-dd HH:mm:ss
     * @param date
            * @return: java.lang.String
            * @author: 25652
            * @time: 2022/5/28 11:22
     */
    public static String formateDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * @description: 对指定对象进行格式化 yyyy-MM-dd
     * @param date
     * @return: java.lang.String
     * @author: 25652
     * @time: 2022/5/28 11:22
     */
    public static String formateDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * @description: 对指定对象进行格式化 HH:mm:ss
     * @param date
     * @return: java.lang.String
     * @author: 25652
     * @time: 2022/5/28 11:22
     */
    public static String formateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

}
