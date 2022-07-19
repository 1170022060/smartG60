package com.pingok.mix.utils;

/**
 * @author
 * @time 2022/7/13 17:07
 */
public class HKUtils {
    //SDK时间解析
    public static String parseTime(int time)
    {
        int year=(time>>26)+2000;
        int month=(time>>22)&15;
        int day=(time>>17)&31;
        int hour=(time>>12)&31;
        int min=(time>>6)&63;
        int second=(time>>0)&63;
        String sTime=year+"-"+month+"-"+day+"-"+hour+":"+min+":"+second;
        return sTime;
    }
}
