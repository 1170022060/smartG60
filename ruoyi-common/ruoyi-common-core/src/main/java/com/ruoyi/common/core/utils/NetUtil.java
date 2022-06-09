package com.ruoyi.common.core.utils;

import java.io.IOException;
import java.net.InetAddress;

public class NetUtil {

    public static Boolean ping(String ip) throws IOException {
        if (ip == null || ip.isEmpty()) return true;
        int timeout = 1000;
        boolean reachable = false;
        reachable = InetAddress.getByName(ip).isReachable(timeout);
        return reachable;
    }
}
