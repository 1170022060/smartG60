package com.pingok.vocational.service.business.impl;

import com.pingok.vocational.domain.business.TblOptWorkInfo;
import com.pingok.vocational.mapper.business.TblOptWorkInfoMapper;
import com.pingok.vocational.service.business.IOptWorkInfoService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作员工班信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OptWorkInfoServiceImpl implements IOptWorkInfoService {
    @Autowired
    private TblOptWorkInfoMapper tblOptWorkInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<Map> selectOptWorkInfo(Date startDate, Date endDate, String stationId, String optName, Integer shift) {
        return tblOptWorkInfoMapper.selectOptWorkInfo( startDate,  endDate,  stationId,  optName,  shift);
    }

    @Override
    public int insertOptWorkInfo(TblOptWorkInfo tblOptWorkInfo) {
        tblOptWorkInfo.setId(remoteIdProducerService.nextId());
        if(tblOptWorkInfo.getShift()==1)
        {
            tblOptWorkInfo.setInTime(DateUtils.getPreTime(tblOptWorkInfo.getWorkDate(),-300));
            tblOptWorkInfo.setOutTime(DateUtils.getPreTime(tblOptWorkInfo.getWorkDate(),-300));
        }
        if(tblOptWorkInfo.getShift()==2)
        {
            tblOptWorkInfo.setInTime(DateUtils.getPreTime(tblOptWorkInfo.getWorkDate(),420));
            tblOptWorkInfo.setOutTime(DateUtils.getPreTime(tblOptWorkInfo.getWorkDate(),420));
        }
        tblOptWorkInfo.setCash(0);
        tblOptWorkInfo.setCard(0);
        tblOptWorkInfo.setBadCard(0);
        tblOptWorkInfo.setNoCard(0);
        tblOptWorkInfo.setReceipt(0);
        tblOptWorkInfo.setMPay(0);
        tblOptWorkInfo.setObu(0);
        tblOptWorkInfo.setSptcc(0);
        tblOptWorkInfo.setTransStatus(0);
        return tblOptWorkInfoMapper.insert(tblOptWorkInfo);
    }
}
