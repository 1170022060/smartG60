package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.EventRecordClassVo;
import com.pingok.vocational.domain.report.vo.EventRecordSiteVo;
import com.pingok.vocational.domain.report.vo.EventRecordTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.mapper.event.TblEventRecordMapper;
import com.pingok.vocational.service.report.IEventRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 事件记录表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class EventRecordServiceImpl implements IEventRecordService {

    @Autowired
    private TblEventRecordMapper tblEventRecordMapper;

    @Override
    public List<Map> selectEventRecordByType(String eventType, Date startTime, Date endTime) {
        return tblEventRecordMapper.selectEventRecordByType(eventType,  startTime,  endTime);
    }

    @Override
    public List<EventRecordTypeVo> selectEventRecordByTypeList(ReportVo reportVo) {
        return tblEventRecordMapper.selectEventRecordByTypeList(reportVo);
    }

    @Override
    public List<Map> selectEventRecordBySite(String locationInterval, Date startTime, Date endTime) {
        return tblEventRecordMapper.selectEventRecordBySite(locationInterval,  startTime,  endTime);
    }

    @Override
    public List<EventRecordSiteVo> selectEventRecordBySiteList(ReportVo reportVo) {
        return tblEventRecordMapper.selectEventRecordBySiteList(reportVo);
    }

    @Override
    public List<Map> selectEventRecordByClass(Integer vehClass, Date startTime, Date endTime) {
        return tblEventRecordMapper.selectEventRecordByClass(vehClass,  startTime,  endTime);
    }

    @Override
    public List<EventRecordClassVo> selectEventRecordByClassList(ReportVo reportVo) {
        return tblEventRecordMapper.selectEventRecordByClassList(reportVo);
    }
}
