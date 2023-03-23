package com.pingok.devicemonitor.service.gantry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.*;

import java.util.List;

public interface IGantryUpperStoreService {
    /**
     * 门架牌识流水转换
     * @param data
     */
    List<TblGantryTravelImage> changeViu(String reqFileName, JSONObject data);

    /**
     * ETC 门架牌识图片（按需调取、全量）上传
     * @param reqFileName
     * @param data
     * @return
     */
    List<TblGantryPicture> changeVipu(String reqFileName, JSONArray data);

    /**
     * ETC 门架牌识图片（交易失败或未匹配）上传
     * @param data
     */
    List<TblGantryPictureFail> changeSvipu(String reqFileName, JSONArray data);

    /**
     * 交易流水
     * @param data
     */
    List<TblGantryTransaction> changeEtctu(String reqFileName, JSONObject data);

    /**
     * ETC 门架交易小时批次汇总
     * @param data
     */
    List<TblGantrySumTransaction> changeEtcsu(String reqFileName, JSONArray data);

    /**
     *ETC 门架牌识小时批次汇总
     * @param data
     */
    List<TblGantrySumTravelImage> changeVisu(String reqFileName, JSONArray data);


}
