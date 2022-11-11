package com.pingok.devicemonitor.service.gantry.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.*;
import com.pingok.devicemonitor.service.gantry.IGantryUpperStoreService;
import lombok.extern.slf4j.Slf4j;
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
                list.add(gantrySumTravelImage);
            }
        } catch (Exception ex) {
            log.error("暂存ETC 门架牌识小时批次汇总异常：" + ex.getMessage());
        }
        return list;
    }

}