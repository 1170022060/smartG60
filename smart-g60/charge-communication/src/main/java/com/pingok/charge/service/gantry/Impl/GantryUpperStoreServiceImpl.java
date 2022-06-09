package com.pingok.charge.service.gantry.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.gantry.*;
import com.pingok.charge.mapper.gantry.*;
import com.pingok.charge.service.gantry.IGantryUpperStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @time 2022/5/20 11:52
 */
@Slf4j
@Service
public class GantryUpperStoreServiceImpl implements IGantryUpperStoreService {

    @Autowired
    private TblGantryBaseInfoStoreMapper tblGantryBaseInfoStoreMapper;
    @Autowired
    private TblGantryPictureMapper tblGantryPictureMapper;
    @Autowired
    private TblGantryPictureFailMapper tblGantryPictureFailMapper;
    @Autowired
    private TblGantryMonitorStoreMapper tblGantryMonitorStoreMapper;
    @Autowired
    private TblGantrySumTransactionMapper tblGantrySumTransactionMapper;
    @Autowired
    private TblGantrySumTravelImageMapper tblGantrySumTravelImageMapper;
    @Autowired
    private TblGantryTransactionMapper tblGantryTransactionMapper;
    @Autowired
    private TblGantryTravelImageMapper tblGantryTravelImageMapper;
    @Autowired
    private TblGantryErrorInfoMapper tblGantryErrorInfoMapper;

    @Override
    public List<TblGantryTravelImage> saveViu(String reqFileName, JSONObject data) {
        List<TblGantryTravelImage> list = new ArrayList<>();
        try {
            if (data.containsKey("picInfoList")) {
                JSONArray picInfoList = data.getJSONArray("picInfoList");
                TblGantryTravelImage tblGantryTravelImage;
                int size = picInfoList.size();
                for (int i = 0; i < size; i++) {
                    tblGantryTravelImage = JSON.parseObject(picInfoList.getString(i), TblGantryTravelImage.class);
                    tblGantryTravelImageMapper.insert(tblGantryTravelImage);
                    list.add(tblGantryTravelImage);
                }
            }
        } catch (Exception ex) {
            log.error(reqFileName + "暂存牌识流水异常：" + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<TblGantryPicture> saveVipu(String reqFileName, JSONArray data) {
        List<TblGantryPicture> list = new ArrayList<>();
        try {
            TblGantryPicture gantryPicture;
            int size = data.size();
            for (int i = 0; i < size; i++) {
                gantryPicture = JSON.parseObject(data.getString(i), TblGantryPicture.class);
                tblGantryPictureMapper.insert(gantryPicture);
                list.add(gantryPicture);
            }
        } catch (Exception ex) {
            log.error("暂存牌识图片异常：" + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<TblGantryPictureFail> saveSvipu(String reqFileName, JSONArray data) {
        List<TblGantryPictureFail> list = new ArrayList<>();
        try {
            TblGantryPictureFail gantryPictureFail;
            int size = data.size();
            for (int i = 0; i < size; i++) {
                gantryPictureFail = JSON.parseObject(data.getString(i), TblGantryPictureFail.class);
                tblGantryPictureFailMapper.insert(gantryPictureFail);
                list.add(gantryPictureFail);
            }
        } catch (Exception ex) {
            log.error("暂存牌识图片（交易失败或未匹配）异常：" + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<TblGantryTransaction> saveEtctu(String reqFileName, JSONObject data) {
        List<TblGantryTransaction> list = new ArrayList<>();
        try {
            if (data.containsKey("tradeInfoList")) {
                JSONArray tradeInfoList = data.getJSONArray("tradeInfoList");
                TblGantryTransaction gantryTransaction;
                int size = tradeInfoList.size();
                for (int i = 0; i < size; i++) {
                    gantryTransaction = JSON.parseObject(tradeInfoList.getString(i), TblGantryTransaction.class);
                    tblGantryTransactionMapper.insert(gantryTransaction);
                    list.add(gantryTransaction);
                }
            }
        } catch (Exception ex) {
            log.error(reqFileName + "暂存交易流水异常：" + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<TblGantrySumTransaction> saveEtcsu(String reqFileName, JSONArray data) {
        List<TblGantrySumTransaction> list = new ArrayList<>();
        try {
            TblGantrySumTransaction gantrySumTransaction;
            int size = data.size();
            for (int i = 0; i < size; i++) {
                gantrySumTransaction = JSON.parseObject(data.getString(i), TblGantrySumTransaction.class);
                tblGantrySumTransactionMapper.insert(gantrySumTransaction);
                list.add(gantrySumTransaction);
            }
        } catch (Exception ex) {
            log.error("暂存ETC 门架交易小时批次汇总异常：" + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<TblGantrySumTravelImage> saveVisu(String reqFileName, JSONArray data) {
        List<TblGantrySumTravelImage> list = new ArrayList<>();
        try {
            TblGantrySumTravelImage gantrySumTravelImage;
            int size = data.size();
            for (int i = 0; i < size; i++) {
                gantrySumTravelImage = JSON.parseObject(data.getString(i), TblGantrySumTravelImage.class);
                tblGantrySumTravelImageMapper.insert(gantrySumTravelImage);
                list.add(gantrySumTravelImage);
            }
        } catch (Exception ex) {
            log.error("暂存ETC 门架牌识小时批次汇总异常：" + ex.getMessage());
        }
        return list;
    }

    @Override
    public void saveBaseInfo(String data) {
        JSONObject jo = JSONObject.parseObject(data);
        JSONObject joStore = new JSONObject();
        joStore.put("chargeUnitId", jo.getString("chargeUnitId"));
        joStore.put("infoVersion", jo.getString("infoVersion"));
        joStore.put("gantryInfoList", jo.getJSONArray("gantryInfoList").toJSONString());
        joStore.put("chargeUnitInfoList", jo.getJSONArray("chargeUnitInfoList").toJSONString());
        joStore.put("cameraInfoList", jo.getJSONArray("cameraInfoList").toJSONString());
        joStore.put("RSUInfoList", jo.getJSONArray("RSUInfoList").toJSONString());
        joStore.put("vehicleDetectorInfoList", jo.getJSONArray("vehicleDetectorInfoList").toJSONString());
        joStore.put("vehicleCountCollect", jo.getJSONArray("vehicleCountCollect").toJSONString());
        joStore.put("otherInfoList", jo.getJSONArray("otherInfoList").toJSONString());

        try {
            TblGantryBaseInfoStore tblGantryBaseInfoStore = JSONObject.parseObject(joStore.toJSONString(), TblGantryBaseInfoStore.class);
            tblGantryBaseInfoStoreMapper.insert(tblGantryBaseInfoStore);
        } catch (Exception ex) {
            log.error("暂存门架报文异常：" + ex.getMessage());
        }
    }

    @Override
    public void saveTghbu(String data) {
        JSONObject jo = JSONObject.parseObject(data);
        JSONObject joStore = new JSONObject();
        joStore.put("chargeUnitId", jo.getString("chargeUnitId"));
        joStore.put("heatVersion", jo.getString("heatVersion"));
        joStore.put("gantryHeartbeatList", jo.getJSONArray("gantryHeartbeatList").toJSONString());
        joStore.put("transProFitList", jo.getJSONArray("transProFitList").toJSONString());
        joStore.put("chargeUnitHeartbeatList", jo.getJSONArray("chargeUnitHeartbeatList").toJSONString());
        joStore.put("vehicleDetectorHeartbeatList", jo.getJSONArray("vehicleDetectorHeartbeatList").toJSONString());
        joStore.put("otherHeartbeatList", jo.getJSONArray("otherHeartbeatList").toJSONString());
        joStore.put("vehicleCountCollect", jo.getJSONArray("vehicleCountCollect").toJSONString());
        joStore.put("cameraHeartbeatList", jo.getJSONArray("cameraHeartbeatList").toJSONString());
        joStore.put("RSUHeartbeatList", jo.getJSONArray("RSUHeartbeatList").toJSONString());
        joStore.put("PSAMInfoList", jo.getJSONArray("PSAMInfoList").toJSONString());
        joStore.put("antennalInfoList", jo.getJSONArray("antennalInfoList").toJSONString());

        try {
            TblGantryMonitorStore tblGantryMonitorStore = JSONObject.parseObject(joStore.toJSONString(), TblGantryMonitorStore.class);
            tblGantryMonitorStoreMapper.insert(tblGantryMonitorStore);
        } catch (Exception ex) {
            log.error("暂存门架报文异常：" + ex.getMessage());
        }
    }

    @Override
    public void saveSpecialEvent(String data) {
        TblGantryErrorInfo tblGantryErrorInfo = JSON.parseObject(data, TblGantryErrorInfo.class);
        try {
            tblGantryErrorInfoMapper.insert(tblGantryErrorInfo);
        } catch (Exception ex) {
            log.error("暂存门架报文异常：" + ex.getMessage());
        }
    }
}
