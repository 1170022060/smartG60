package com.pingok.charge.service.specialRecord;


import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.specialRecord.TblSpecialRecord;

/**
 * 特情 业务层
 *
 * @author qiumin
 */
public interface ISpecialRecordService {
    /**
     * 特情处置
     * @param jsonObject
     */
    void handleSpecial(JSONObject jsonObject);

    /**
     * 临时存储特情信息
     * @param tblSpecialRecord
     */
    void add(TblSpecialRecord tblSpecialRecord);

    /**
     * 上传特情信息
     * @param tblSpecialRecord
     */
    void update(TblSpecialRecord tblSpecialRecord);
}
