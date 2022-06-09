package com.pingok.datacenter.service.specialRecord;

import com.pingok.datacenter.domain.specialRecord.TblSpecialRecord;

/**
 * 特情信息 业务层
 *
 * @author qiumin
 */
public interface ISpecialRecordService {
    void add(TblSpecialRecord tblSpecialRecord);

    void handleSpecial(Long id,Integer optType,String message,String ip,String port);
}
