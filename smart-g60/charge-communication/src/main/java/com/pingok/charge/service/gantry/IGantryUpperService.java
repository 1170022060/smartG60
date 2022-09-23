package com.pingok.charge.service.gantry;

import cn.hutool.core.net.multipart.MultipartFormData;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.gantry.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public interface IGantryUpperService {

    /**
     * 上传门架异常事件数据
     * @param reqFileName
     * @param data
     */
    void updateSpecialEvent(String reqFileName, JSONObject data);

    /**
     * 上传门架运行状态数据
     * @param reqFileName
     * @param data
     */
    void updateTghbu(String reqFileName, JSONObject data);

    /**
     * 上传门架基础信息数据
     * @param reqFileName
     * @param data
     */
    void updateBaseInfo(String reqFileName, JSONObject data);

    /**
     * 上传门架日志文件
     * @param reqFileName
     * @param data
     */
    void updateLog(String reqFileName, String data);

    /**
     * 上传ETC 门架交易小时批次汇总
     * @param reqFileName
     * @param data
     */
    void updateVisu(String reqFileName, List<TblGantrySumTravelImage> data);

    /**
     * 上传ETC 门架交易小时批次汇总
     * @param reqFileName
     * @param data
     */
    void updateEtcsu(String reqFileName, List<TblGantrySumTransaction> data);

    /**
     * 上传交易流水
     * @param reqFileName
     * @param data
     */
    void updateEtctu(String reqFileName, List<TblGantryTransaction> data);

    /**
     *上传ETC 门架牌识图片（交易失败或未匹配）
     * @param reqFileName
     * @param data
     */
    void updateSvipu(String reqFileName, List<TblGantryPictureFail> data);

    /**
     * 上传ETC 门架牌识图片（按需调取、全量）
     * @param data
     */
    void updateVipu(String reqFileName, List<TblGantryPicture> data);

    /**
     * 上传门架牌识流水
     * @param data
     */
    void updateViu(String reqFileName, List<TblGantryTravelImage> data);

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
}
