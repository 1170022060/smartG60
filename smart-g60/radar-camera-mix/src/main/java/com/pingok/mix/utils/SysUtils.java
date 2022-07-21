package com.pingok.mix.utils;

/**
 * @author
 * @time 2022/7/13 16:30
 */
public class SysUtils {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
