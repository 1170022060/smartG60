package com.pingok.datacenter.service.rush.impl;

import com.pingok.datacenter.domain.rush.TblRushRecord;
import com.pingok.datacenter.mapper.rush.TblRushRecordMapper;
import com.pingok.datacenter.service.rush.IRushService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 闯关确认 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RushServiceImpl implements IRushService {

    @Autowired
    private TblRushRecordMapper tblRushRecordMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;


    @Override
    public void rushConfirm(Long id, Integer status) {
        TblRushRecord tblRushRecord = tblRushRecordMapper.selectByPrimaryKey(id);
        if (StringUtils.isNull(tblRushRecord)) {
            throw new ServiceException("id错误");
        }
        tblRushRecord.setStatus(status);
        tblRushRecord.setConfirmTime(DateUtils.getNowDate());
        tblRushRecord.setConfirmUserId(SecurityUtils.getUserId());
        tblRushRecordMapper.updateByPrimaryKey(tblRushRecord);
    }

    @Override
    public void rushRecord(String year, String startTime, String endTime, String twoHours) {
        List<TblRushRecord> list = tblRushRecordMapper.rushRecord(year, startTime, endTime, twoHours);
        for (TblRushRecord r : list) {
            r.setId(remoteIdProducerService.nextId());
            r.setReliability(new BigDecimal(50));
            tblRushRecordMapper.insert(r);
        }
    }

    @Override
    public List<TblRushRecord> list(String stationName,String vehPlate, String startTime, String endTime) {
        return tblRushRecordMapper.list(stationName, vehPlate,startTime, endTime);
    }
}
