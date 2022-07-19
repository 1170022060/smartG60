package com.pingok.monitor.utils;

/**
 * @author
 * @time 2022/7/6 13:54
 */
public class ByteUtils {
    /**
     * byte[] 转 BCD码
     */
    public static String toBCDStr(byte[] ba, int len, int pos)
    {
        if (ba.length < len) return "";
//        byte[] sub = ArrayUtil.sub(ba, pos, pos + len);
//        return Arrays.toString(sub);
        StringBuffer sb = new StringBuffer();
        for(int i = pos; i < len + pos; ++i) {
            Integer n = Integer.valueOf(ba[i]);
            sb.append(Integer.toHexString(n));
        }
        return sb.toString();
    }

    /**
     * byte转int（禁止负数）
     */
    public static Integer b2i(byte b) {
        return b & 0xff;
    }
}
