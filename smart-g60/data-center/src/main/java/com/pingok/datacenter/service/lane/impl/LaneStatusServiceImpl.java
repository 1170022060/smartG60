package com.pingok.datacenter.service.lane.impl;

import com.pingok.datacenter.domain.lane.TblDeviceFault;
import com.pingok.datacenter.domain.lane.TblLaneStatus;
import com.pingok.datacenter.domain.lane.TblLaneStatusLog;
import com.pingok.datacenter.mapper.lane.TblDeviceFaultMapper;
import com.pingok.datacenter.mapper.lane.TblLaneStatusLogMapper;
import com.pingok.datacenter.mapper.lane.TblLaneStatusMapper;
import com.pingok.datacenter.service.lane.ILaneStatusService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

/**
 * 车道状态 服务层处理
 *
 * @author qiumin
 */
@Service
public class LaneStatusServiceImpl implements ILaneStatusService {

    @Autowired
    private TblLaneStatusMapper tblLaneStatusMapper;

    @Autowired
    private TblLaneStatusLogMapper tblLaneStatusLogMapper;

    @Autowired
    private TblDeviceFaultMapper tblDeviceFaultMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void update(TblLaneStatus tblLaneStatus) {
        TblLaneStatusLog laneStatusLog = new TblLaneStatusLog();
        laneStatusLog.setId(remoteIdProducerService.nextId());
        BeanUtils.copyNotNullProperties(tblLaneStatus, laneStatusLog);
        tblLaneStatusLogMapper.insert(laneStatusLog);

        Example example = new Example(TblLaneStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("laneHex", tblLaneStatus.getLaneHex());
        TblLaneStatus laneStatus = tblLaneStatusMapper.selectOneByExample(example);
        if (laneStatus == null) {
            laneStatus = new TblLaneStatus();
            laneStatus.setId(remoteIdProducerService.nextId());
            laneStatus.setCreateTime(DateUtils.getNowDate());
            BeanUtils.copyNotNullProperties(tblLaneStatus, laneStatus);
            tblLaneStatusMapper.insert(laneStatus);
        } else {
            laneStatus.setUpdateTime(DateUtils.getNowDate());
            BeanUtils.copyNotNullProperties(tblLaneStatus, laneStatus);
            tblLaneStatusMapper.updateByPrimaryKey(laneStatus);
        }

        if(tblLaneStatus.getDisks().compareTo(tblDeviceFaultMapper.value("disk.threshold")) < 0)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("磁盘可用空间不足");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"磁盘可用空间不足"+tblDeviceFaultMapper.value("disk.threshold")+"G");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

        if(tblLaneStatus.getCpu().compareTo(tblDeviceFaultMapper.value("cup.threshold")) >= 0)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("Cpu使用率过高");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"工控机当前Cpu使用率达到"+tblDeviceFaultMapper.value("cup.threshold")+"%");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

//        if(tblLaneStatus.getMemory().compareTo(tblDeviceFaultMapper.value("memory.threshold")) >= 0)
//        {
//            TblDeviceFault tblDeviceFault=new TblDeviceFault();
//            tblDeviceFault.setId(remoteIdProducerService.nextId());
//            tblDeviceFault.setFaultType("内存使用率过高");
//            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"工控机当前内存使用率达到"+tblDeviceFaultMapper.value("memory.threshold")+"%");
//            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
//            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
//            tblDeviceFault.setRegisterType(2);
//            tblDeviceFault.setStatus(0);
//            tblDeviceFaultMapper.insert(tblDeviceFault);
//        }

        if(tblLaneStatus.getLprStatus()==1)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("车牌识别状态异常");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"车牌识别状态异常");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

        if(tblLaneStatus.getCameraStatus()==1)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("摄像机状态异常");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"摄像机状态异常");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

        if(tblLaneStatus.getOverLoadStatus()==1)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("治超状态异常");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"治超状态异常");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

        if(tblLaneStatus.getNetStatus()==1)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("网络状态异常");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"网络状态异常");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

        if(tblLaneStatus.getRsuStatus()==1)
        {
            TblDeviceFault tblDeviceFault=new TblDeviceFault();
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setFaultType("天线状态异常");
            tblDeviceFault.setFaultDescription(tblDeviceFaultMapper.lane(tblLaneStatus.getLaneHex())+"天线状态异常");
            tblDeviceFault.setFaultTime(tblLaneStatus.getTime());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setRegisterType(2);
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);
        }

    }
}
