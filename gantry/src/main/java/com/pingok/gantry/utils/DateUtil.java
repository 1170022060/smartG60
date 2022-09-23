package com.pingok.gantry.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author
 * @time 2022/7/7 16:27
 */
public class DateUtil {
    /**
     * 获取今天开始时间戳
     */
    public static String getTodayStart() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(c.getTime());
    }

    /**
     * 获取今天开始时间戳
     */
    public static String getTodayEnd() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(c.getTime());
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreMinute(int jj) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE,c.get(Calendar.MINUTE)+jj);
        c.set(Calendar.SECOND,0);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(c.getTime());
    }
}
