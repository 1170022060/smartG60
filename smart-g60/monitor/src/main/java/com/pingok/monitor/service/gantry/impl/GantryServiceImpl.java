package com.pingok.monitor.service.gantry.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.monitor.domain.gantry.TblGantryDayTrading;
import com.pingok.monitor.domain.gantry.TblGantryStatus;
import com.pingok.monitor.domain.gantry.TblGantryStatusDtl;
import com.pingok.monitor.domain.gantry.vo.GantryEnum;
import com.pingok.monitor.domain.gantry.vo.GantryV2X;
import com.pingok.monitor.mapper.gantry.TblGantryDayTradingMapper;
import com.pingok.monitor.mapper.gantry.TblGantryEventReleaseMapper;
import com.pingok.monitor.mapper.gantry.TblGantryStatusDtlMapper;
import com.pingok.monitor.mapper.gantry.TblGantryStatusMapper;
import com.pingok.monitor.service.gantry.IGantryService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架 服务层处理
 *
 * @author qiumin
 */
@Service
public class GantryServiceImpl implements IGantryService {

    @Autowired
    private TblGantryStatusMapper tblGantryStatusMapper;
    @Autowired
    private TblGantryDayTradingMapper tblGantryDayTradingMapper;
    @Autowired
    private TblGantryStatusDtlMapper tblGantryStatusDtlMapper;
    @Autowired
    private TblGantryEventReleaseMapper tblGantryEventReleaseMapper;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public GantryEnum gantryStatus(String startTime,String endTime) {
        GantryEnum gantryEnum = new GantryEnum();
        gantryEnum.setTransactionNumber(tblGantryStatusMapper.findAllTransactionNumber());
        gantryEnum.setTravelimageNumber(tblGantryStatusMapper.findAllTravelimageNumber());
        gantryEnum.setGantryInfos(tblGantryStatusMapper.gantryStatus(startTime,endTime));
        return gantryEnum;
    }

    @Override
    public TblGantryStatus findById(Long id) {
        Example example = new Example(TblGantryStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", id);
        TblGantryStatus gantryStatus = tblGantryStatusMapper.selectOneByExample(example);
        if (gantryStatus != null) {
            example = new Example(TblGantryDayTrading.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("gantryId", id);
            TblGantryDayTrading gantryDayTrading = tblGantryDayTradingMapper.selectOneByExample(example);
            gantryStatus.setDayTrading(gantryDayTrading);

            example = new Example(TblGantryStatusDtl.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("statusId", id);
            List<TblGantryStatusDtl> gantryStatusDtls = tblGantryStatusDtlMapper.selectByExample(example);
            gantryStatus.setGantryStatusDtls(gantryStatusDtls);
        }
        return gantryStatus;
    }

    @Override
    public boolean gantryV2X(GantryV2X data) {
        boolean ret = true;

        //kafka
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.CHARGE_SIGNAL_GANTRY_V2X);
        kafkaEnum.setData(JSON.toJSONString(data));
        remoteKafkaService.send(kafkaEnum);

        return ret;
    }

    @Override
    public List<Map> getRecord(String gantryId, String eventType, Integer status, Date startTime, Date endTime) {
        return tblGantryEventReleaseMapper.getRecord(gantryId,eventType,status,startTime,endTime);
    }
}
