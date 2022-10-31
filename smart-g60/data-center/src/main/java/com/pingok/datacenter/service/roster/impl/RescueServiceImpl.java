package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.rescue.TblRescueStationUsed;
import com.pingok.datacenter.mapper.roster.rescue.TblRescueListRecordMapper;
import com.pingok.datacenter.mapper.roster.rescue.TblRescueStationUsedMapper;
import com.pingok.datacenter.service.roster.IRescueService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 追缴名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RescueServiceImpl implements IRescueService {

    @Autowired
    private TblRescueListRecordMapper tblRescueListRecordMapper;
    @Autowired
    private TblRescueStationUsedMapper tblRescueStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void rescue(JSONObject obj) {
        Example example = new Example(TblRescueStationUsed.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("version", obj.getString("version"));
        criteria.andEqualTo("stationHex", obj.getString("stationHex"));
        TblRescueStationUsed stationUsed = tblRescueStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblRescueStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblRescueStationUsedMapper.insert(stationUsed);
        }
    }
}
