package com.pingok.datacenter.service.lane.impl;

import com.pingok.datacenter.domain.lane.TblLaneStatus;
import com.pingok.datacenter.domain.lane.TblLaneStatusLog;
import com.pingok.datacenter.mapper.lane.TblLaneStatusLogMapper;
import com.pingok.datacenter.mapper.lane.TblLaneStatusMapper;
import com.pingok.datacenter.service.lane.ILaneStatusService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
    }
}
