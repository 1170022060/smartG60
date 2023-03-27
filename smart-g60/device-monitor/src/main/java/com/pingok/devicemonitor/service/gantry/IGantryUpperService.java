package com.pingok.devicemonitor.service.gantry;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.*;
import com.pingok.devicemonitor.domain.gantry.vo.*;
import com.ruoyi.common.core.web.domain.AjaxResult;

import java.util.List;

public interface IGantryUpperService {

    /**
     * 解析请求类型
     *
     * @param reqFileName 请求文件名
     * @return
     */
    String getReqType(String reqFileName);

    /**
     * 生成返回body
     *
     * @param reqType 请求类型
     * @param code    状态码 200-成功，其他异常
     * @return
     */
    JSONObject genResponse(String reqType, Integer code);

    void handleBaseInfoUpload(JSONObject body);
    void handleSpecialEventUpload(JSONObject body);
    void handleTghbu(JSONObject body);

    /**
     *存储门架牌识数据
     * @param data
     */
    void handleViu(TblGantryTravelImage data);
    /**
     *存储门架牌识数据
     * @param data
     */
    void handleViu(List<TblGantryTravelImage> data);

    /**
     * 存储ETC 门架牌识图片（按需调取、全量）上传
     * @param data
     */
    void handleVipu(TblGantryPicture data);

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
    void handleEtctu(TblGantryTransaction data);
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
