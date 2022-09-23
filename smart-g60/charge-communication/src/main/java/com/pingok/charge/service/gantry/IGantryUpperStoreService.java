package com.pingok.charge.service.gantry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.gantry.*;

import java.util.List;

public interface IGantryUpperStoreService {
    /**
     * 门架牌识流水暂存
     * @param data
     */
    List<TblGantryTravelImage> saveViu(String reqFileName, JSONObject data);

    /**
     * ETC 门架牌识图片（按需调取、全量）上传
     * @param reqFileName
     * @param data
     * @return
     */
    List<TblGantryPicture> saveVipu(String reqFileName, JSONArray data);

    /**
     * ETC 门架牌识图片（交易失败或未匹配）上传
     * @param data
     */
    List<TblGantryPictureFail> saveSvipu(String reqFileName, JSONArray data);

    /**
     * 交易流水
     * @param data
     */
    List<TblGantryTransaction> saveEtctu(String reqFileName, JSONObject data);

    /**
     * ETC 门架交易小时批次汇总
     * @param data
     */
    List<TblGantrySumTransaction> saveEtcsu(String reqFileName, JSONArray data);

    /**
     *ETC 门架牌识小时批次汇总
     * @param data
     */
    List<TblGantrySumTravelImage> saveVisu(String reqFileName, JSONArray data);

    /**
     * 门架基础信息上传
     * @param reqFileName
     * @param data
     */
    void saveBaseInfo(String reqFileName,String data);

    /**
     * 暂存门架运行状态数据
     * @param reqFileName
     * @param data
     */
    void saveTghbu(String reqFileName,String data);

    /**
     * 暂存门架异常事件
     * @param reqFileName
     * @param data
     */
    void saveSpecialEvent(String reqFileName,String data);
}
