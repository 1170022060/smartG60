package com.pingok.charge.service.gantry.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.gantry.*;
import com.pingok.charge.mapper.gantry.*;
import com.pingok.charge.service.gantry.IGantryUpperService;
import com.pingok.charge.service.gantry.IGantryUpperStoreService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author
 * @time 2022/5/19 20:41
 * 构造返回json：https://www.cnblogs.com/seanRay/p/15388523.html
 */
@Slf4j
@Service
public class GantryUpperServiceImpl implements IGantryUpperService {

    @Value("${daas.host}")
    private String host;


    @Autowired
    private IGantryUpperStoreService iGantryUpperStoreService;
    @Autowired
    private TblGantryBaseInfoStoreMapper tblGantryBaseInfoStoreMapper;
    @Autowired
    private TblGantryPictureMapper tblGantryPictureMapper;
    @Autowired
    private TblGantrySumTransactionMapper tblGantrySumTransactionMapper;
    @Autowired
    private TblGantrySumTravelImageMapper tblGantrySumTravelImageMapper;
    @Autowired
    private TblGantryTransactionMapper tblGantryTransactionMapper;
    @Autowired
    private TblGantryTravelImageMapper tblGantryTravelImageMapper;
    @Autowired
    private TblGantryPictureFailMapper tblGantryPictureFailMapper;


    @Async
    void handleVehicleMonitor(String data) {
        // 目前门架无外接车检器
        log.info("收到门架车检器数据：" + data);
    }

    @Async
    void handleBaseInfoUpload(JSONObject data) {
        String chargeUnitId = data.getString("chargeUnitId");
        if (!chargeUnitId.isEmpty()) {
            if (sendToDeviceMonitor("baseInfoUpload", data.toJSONString())) {
                Example example = new Example(TblGantryBaseInfoStore.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("chargeUnitId", chargeUnitId);
                tblGantryBaseInfoStoreMapper.deleteByExample(example);
            }
        }
    }

    @Async
    void handleTghbu(JSONObject data) {
        String chargeUnitId = data.getString("chargeUnitId");
        if (sendToDeviceMonitor("tghbu", data.toJSONString())) {
            Example example = new Example(TblGantryMonitorStore.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("chargeUnitId", chargeUnitId);
            tblGantryBaseInfoStoreMapper.deleteByExample(example);
        }
    }


    void handleSpecialEventUpload(String data) {
        TblGantryErrorInfo tblGantryErrorInfo = JSON.parseObject(data, TblGantryErrorInfo.class);
        if (!tblGantryErrorInfo.getEventId().isEmpty()) {
            if (sendToDeviceMonitor("specialEventUpload", data)) {
                Example example = new Example(TblGantrySumTravelImage.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("eventId", tblGantryErrorInfo.getEventId());
                tblGantrySumTravelImageMapper.deleteByExample(example);
            }
        }
    }


    @Override
    public void updateLog(String reqFileName, String data) {
        String route = "/data-center/gantryUpper/log";
        update(route, reqFileName, data);
    }

    @Async
    @Override
    public void updateVisu(String reqFileName, List<TblGantrySumTravelImage> data) {
        String route = "/data-center/gantryUpper/visu";
        if (update(route, reqFileName, JSON.toJSONString(data))) {
            for (TblGantrySumTravelImage g : data) {
                tblGantrySumTravelImageMapper.delete(g);
            }
        }
    }

    @Async
    @Override
    public void updateEtcsu(String reqFileName, List<TblGantrySumTransaction> data) {
        String route = "/data-center/gantryUpper/etcsu";
        if (update(route, reqFileName, JSON.toJSONString(data))) {
            for (TblGantrySumTransaction g : data) {
                tblGantrySumTransactionMapper.delete(g);
            }
        }
    }

    @Async
    @Override
    public void updateEtctu(String reqFileName, List<TblGantryTransaction> data) {
        String route = "/data-center/gantryUpper/etctu";
        if (update(route, reqFileName, JSON.toJSONString(data))) {
            for (TblGantryTransaction g : data) {
                tblGantryTransactionMapper.delete(g);
            }
        }
    }

    @Async
    @Override
    public void updateSvipu(String reqFileName, List<TblGantryPictureFail> data) {
        String route = "/data-center/gantryUpper/svipu";
        if (update(route, reqFileName, JSON.toJSONString(data))) {
            for (TblGantryPictureFail g : data) {
                tblGantryPictureFailMapper.delete(g);
            }
        }

    }

    @Async
    @Override
    public void updateVipu(String reqFileName, List<TblGantryPicture> data) {
        String route = "/data-center/gantryUpper/vipu";
        if (update(route, reqFileName, JSON.toJSONString(data))) {
            for (TblGantryPicture g : data) {
                tblGantryPictureMapper.delete(g);
            }
        }

    }

    @Async
    @Override
    public void updateViu(String reqFileName, List<TblGantryTravelImage> data) {
        String route = "/data-center/gantryUpper/viu";
        if (update(route, reqFileName, JSON.toJSONString(data))) {
            for (TblGantryTravelImage g : data) {
                tblGantryTravelImageMapper.delete(g);
            }
        }
    }


    @Override
    public String getReqType(String reqFileName) {
        int pos = reqFileName.indexOf('_');
        pos = reqFileName.indexOf('_', pos + 1);
        return reqFileName.substring(0, pos);
    }


    @Override
    public JSONObject genResponse(String reqType, Integer code) {
        JSONObject jo = new JSONObject();
        jo.put("subCode", code);
        jo.put("info", code == 200 ? "成功" : "失败");
        jo.put("receiveTime", DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));

        switch (reqType) {
            case "GBUPLOAD_VIU": {
                jo.put("successCount", 0);
                jo.put("failList", new JSONArray());
                break;
            }
            default:
                break;
        }
        return jo;
    }

    /**
     * 上传至Daas
     *
     * @param route 接口路由
     * @return true 妆发成功 false 失败
     */
    boolean update(String route, String reqFileName, String data) {
        String url = host + route;
        String post;
        R ret = null;
        int[] sleep = new int[]{1, 3, 6};
        int i = 0;
        while (i++ < sleep.length) {
            try {
                post = HttpUtil.post(url, data);
                if (!StringUtils.isEmpty(post)) {
                    ret = JSON.parseObject(post, R.class);
                    if (R.FAIL == ret.getCode()) {
                        log.error(reqFileName + "门架数据上传失败：" + ret.getMsg());
                    } else {
                        break;
                    }
                    Thread.sleep(sleep[i++] * 1000);
                }
            } catch (Exception e) {
                log.error(reqFileName + "门架数据上传失败：" + e.getMessage());
            }
        }
        return R.SUCCESS == ret.getCode();
    }
}
