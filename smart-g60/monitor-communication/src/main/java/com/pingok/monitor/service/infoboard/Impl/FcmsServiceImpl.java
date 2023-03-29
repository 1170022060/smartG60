package com.pingok.monitor.service.infoboard.Impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.infoboard.SanSiInfo;
import com.pingok.monitor.service.infoboard.IFcmsService;
import com.sansi.fcms.devinfor.DeviceControl;
import com.sansi.fcms.devinfor.DeviceVar;
import com.sansi.playlist.AreaItem;
import com.sansi.playlist.PageItem;
import com.sansi.playlist.PlayItem;
import com.sansi.playlist.entry.AreaPositon;
import com.sansi.playlist.entry.BaseColour;
import com.sansi.playlist.entry.PlayTimeBase;
import com.sansi.playlist.entry.TextBase;
import com.sansi.playlist.fcms.PlayListFcms;
import com.sansi.version.SdkVersion;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.sansi.tools.RetrunData;
import com.sansi.playlist.AreaItem;
import com.sansi.playlist.PageItem;
import com.sansi.playlist.PlayItem;
import com.sansi.playlist.entry.*;
import com.sansi.playlist.fcms.PlayListFcms;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author
 * @time 2022/5/2 8:59
 */
@Slf4j
@Service
public class FcmsServiceImpl implements IFcmsService {

    @Override
    public JSONObject sendData(SanSiInfo demo) {

        String sdkVerison = SdkVersion.getSdkVersion();
        System.out.println("version:" + sdkVerison);

        String devId = "1";
        SanSiInfo sanSiData = new SanSiInfo(devId, "10.31.42.68", "1");

        //设备初始化
//        RetrunData result= DeviceVar.deviceInforInit(sanSiData.getDeviceId(),sanSiData.getFcmsVersion(),
//                sanSiData.getIpAddr(),sanSiData.getIpPort(),sanSiData.getBlockSize());
        RetrunData result= DeviceVar.deviceInforInit(devId,1,
                "10.31.42.68",3434,2048);

        result = DeviceControl.fcmsFaultQurey(devId);
        result = DeviceControl.fcmsLightAndModeQurey(devId);//读取调节模式和亮度值
        result = DeviceControl.fcmsNowPictureQuery(devId,"D:\\temp\\DASS\\2.bmp");
        result = DeviceControl.fcmsDownFile(devId,"D:\\temp\\DASS\\r64.bmp","r64.bmp");//下载文件
        result = DeviceControl.fcmsActivePlayList(devId,"000");
        result = DeviceControl.fcmsFileInfor(devId);
        result = DeviceControl.fcmsVersionRead(devId);

        //打包文件
        Animation animation = new Animation(); //FCMS只支持入动画效果，且必须填写入动画效果，其它停留动画和出动画效果设置无效
        animation.setInAnimation(sanSiData.getInAnimation());
        animation.setInAnimationSpeed(sanSiData.getInAnimationSpeed());

        TextBase textBase = new TextBase(0,sanSiData.getTextPath());
        textBase.setFontSize(sanSiData.getFontSize());
        textBase.setFontName(sanSiData.getFontName());
        textBase.setFontColour(sanSiData.getFontColour());

        PlayTimeBase textPlayTime = new PlayTimeBase(sanSiData.getTotalTime());
        PlayItem textPlayItem = new PlayItem(1, UUID.randomUUID().toString(),sanSiData.getPlayName(),textBase,textPlayTime);////设置文字播放项
        textPlayItem.setAnimation(animation);//设置播放项入动画效果，必须设置
        textPlayItem.setX(sanSiData.getPosX());
        textPlayItem.setY(sanSiData.getPosY());

        Background background = new Background();
        BaseColour backColour = sanSiData.getBaseColour();
        background.setBackColour(backColour);

        //播放项集合
        List<PlayItem> playItemList = new ArrayList<>();
        playItemList.add(textPlayItem);

        AreaPositon areaPositon = new AreaPositon(0,0,192,500,0);
        AreaItem areaItem = new AreaItem(UUID.randomUUID().toString(),sanSiData.getPlayName(),areaPositon,playItemList);

        //窗口列表
        List<AreaItem> areaItemList = new ArrayList<>();
        areaItemList.add(areaItem);

        /**
         * 页面（场景）类
         * 页面（场景）可以认为是显示屏播放后看到的画面，画面可以由多个区域（窗口）组成。
         * 注意：区域（窗口）位置除特殊需要外不要发生覆盖或重合，需要提前规划好显示区域（窗口）；若区域（窗口）有覆盖或重合，则覆盖或重合的位置显示最后一个添加区域（窗口）内容
         * 备注：一个播放表只能支持一个页面（场景）
         * */
        PageItem pageItem = new PageItem(UUID.randomUUID().toString(),sanSiData.getPlayName(),areaItemList);

        PlayListFcms plf = new PlayListFcms();
        result = plf.createFcmsPlayList("D:\\temp\\DASS\\play.lst",pageItem);

        //上传文件
        result = DeviceControl.fcmsUploadFile(sanSiData.getDeviceId(),"D:\\temp\\DASS\\play.lst" ,sanSiData.getPlayName());

        //激活播放表
        result = DeviceControl.fcmsActivePlayList(sanSiData.getDeviceId(),sanSiData.getPlayName());
        return null;
    }


}

@Data
class SanSiResult{
    private int code;
    private Object data;
    private String message;
    private String time;
}
