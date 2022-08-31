package com.pingok.vocational.service.business.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.vocational.domain.business.TblOptWorkInfo;
import com.pingok.vocational.mapper.business.TblOptWorkInfoMapper;
import com.pingok.vocational.service.business.IOptWorkInfoService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public List<Map> selectOptWorkInfo(Date startDate, Date endDate, String stationId, String optName, Integer shift ,Integer optId) {
        return tblOptWorkInfoMapper.selectOptWorkInfo( startDate,  endDate,  stationId,  optName,  shift ,optId);
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
        tblOptWorkInfo.setResetStatus(1);
        tblOptWorkInfo.setIssueStatus(0);
        return tblOptWorkInfoMapper.insert(tblOptWorkInfo);
    }

    @Override
    public int updateOptWorkInfo(Long id) {
        TblOptWorkInfo tblOptWorkInfo= tblOptWorkInfoMapper.selectByPrimaryKey(id);
        tblOptWorkInfo.setResetStatus(0);
        tblOptWorkInfo.setIssueStatus(0);
        return tblOptWorkInfoMapper.updateByPrimaryKeySelective(tblOptWorkInfo);
    }

    @Override
    public void issueOptWorkInfo() {
        Example example = new Example(TblOptWorkInfo.class);
        example.createCriteria().andEqualTo("issueStatus", 0);
        List<TblOptWorkInfo> optWorkInfoArray = tblOptWorkInfoMapper.selectByExample(example);
        KafkaEnum kafkaEnum;
        if (optWorkInfoArray != null && optWorkInfoArray.size() > 0) {
            kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.OPT_WORK_INFO);
            kafkaEnum.setData(JSON.toJSONString(optWorkInfoArray));
            remoteKafkaService.send(kafkaEnum);
            for(TblOptWorkInfo tblOptWorkInfo: optWorkInfoArray)
            {
                tblOptWorkInfo.setIssueStatus(1);
                tblOptWorkInfo.setIssueTime(new Date());
                tblOptWorkInfoMapper.updateByPrimaryKeySelective(tblOptWorkInfo);
            }
        }
    }

}
