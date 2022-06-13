package com.pingok.datacenter.service.gantry;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.gantry.*;
import com.pingok.datacenter.domain.gantry.model.*;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;

import java.util.List;

public interface IGantryUpperService {
    /**
     *存储门架牌识数据
     * @param data
     */
    void handleViu(List<TblGantryTravelImage> data);

    /**
     * 存储ETC 门架牌识图片（按需调取、全量）上传
     * @param data
     */
    void handleVipu(List<TblGantryPicture> data);

    /**
     *ETC 门架牌识图片（交易失败或未匹配）
     * @param data
     */
    void handleSvipu(List<TblGantryPictureFail> data);

    /**
     *存储交易流水
     * @param data
     */
    void handleEtctu(List<TblGantryTransaction> data);

    /**
     * 存储 ETC 门架交易小时批次汇总
     * @param data
     */
    void handleEtcsu(List<TblGantrySumTransaction> data);

    /**
     * 存储 ETC 门架牌识小时批次汇总
     * @param data
     */
    void handleVisu(List<TblGantrySumTravelImage> data);

    void handleLog(JSONObject data);

    AjaxResult GetErrorData(GetErrorDataModel data);
    AjaxResult FixErrorData(FixErrorDataModel data);
    AjaxResult PicRealTransfer(PicRealTransferModel data);
    AjaxResult VIUpload(VIUploadModel data);
    AjaxResult VIPUpload(VIPUploadModel data);
    AjaxResult ETCTUpload(ETCTUploadModel data);
    AjaxResult HourSumUpload(HourSumUploadModel data);
    AjaxResult LogBUpload(LogBUploadModel data);
}
