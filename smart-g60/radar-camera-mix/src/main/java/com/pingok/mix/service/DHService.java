package com.pingok.mix.service;

import com.company.NetSDK.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 大华雷视通讯服务
 * @author
 * @time 2022/7/18 16:20
 */
@Slf4j
@Service
public class DHService {

    @Value("${linux-sdk-path}")
    private String linuxSdkPath;
    @Value("${windows-sdk-path}")
    private String windowsSdkPath;
    @Value("${dhListenPort}")
    private Short dhListenPort;
    @Value("${picPath}")
    private String picPath;
    private static String _picPath;
    //    @Value("${dass.host}")
    private String dassHost = "localhost:9308";
    private static String _dassHost;

    static INetSDK dhNetSDK = null;
    static BufferedImage gBuffImage = null;

    @PostConstruct
    public void init() {
        _picPath = picPath;
        _dassHost = dassHost;

        run();
    }

    //初始化
    public boolean run() {
        //初始化SDK + 断线回调
        if(!INetSDK.Init(DisConnCB.getInstance())) {
            log.error("【大华】SDK 初始化失败！");
            return false;
        }
        //断线重连回调
        INetSDK.SetAutoReconnect(ReConnCB.getInstance());
        //打开日志
        LOG_SET_PRINT_INFO sdkLog = new LOG_SET_PRINT_INFO();
        String logPath = new File(".").getAbsoluteFile().getParent() + File.separator + "sdk_log" + File.separator
                + "sdk.log";
        sdkLog.bSetFilePath = true;
        System.arraycopy(logPath.getBytes(), 0, sdkLog.szLogFilePath, 0, logPath.getBytes().length);
        sdkLog.bSetPrintStrategy = true;
        sdkLog.nPrintStrategy = 0;
        if (!INetSDK.LogOpen(sdkLog)) {
            log.error("【大华】打开日志失败!");
        }

        //开启监听

        return true;
    }

    //获取SDK实例
//    private boolean CreateSDKInstance() {
//        return true;
//    }

    //加载组件库
//    private void LoadComponentLib() {
//
//    }

    //报警回调
    private static class EventCB {}

    //断线回调
    private static class DisConnCB implements CB_fDisConnect {

        public static DisConnCB getInstance() {
            return CallBackHolder.instance;
        }

        private DisConnCB() {}

        @Override
        public void invoke(long l, String s, int i) {

        }

        private static class CallBackHolder {
            private static DisConnCB instance = new DisConnCB();
        }
    }

    //断线重连回调
    private static class ReConnCB implements CB_fHaveReConnect {

        public static ReConnCB getInstance() {
            return CallBackHolder.instance;
        }

        private ReConnCB() {}

        @Override
        public void invoke(long l, String s, int i) {

        }

        private static class CallBackHolder {
            private static ReConnCB instance = new ReConnCB();
        }
    }
}
