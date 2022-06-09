package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SpecialRecordVo;
import com.pingok.vocational.mapper.report.TblSpecialRecordMapper;
import com.pingok.vocational.service.report.ISpecialRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 特情记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SpecialRecordServiceImpl implements ISpecialRecordService {
    @Autowired
    private TblSpecialRecordMapper tblSpecialRecordMapper;

    @Override
    public List<Map> selectSpecialRecord(Date startTime, Date endTime,String stationId, String laneId) {
        return tblSpecialRecordMapper.selectSpecialRecord(startTime, endTime, stationId,laneId);
    }

    @Override
    public List<Map> selectSpecialStatistic(Date startDate, Date endDate) {
        return tblSpecialRecordMapper.selectSpecialStatistic(startDate,endDate);
    }

    @Override
    public List<SpecialRecordVo> selectSpecialStatisticList(ReportVo reportVo) {
        return tblSpecialRecordMapper.selectSpecialStatisticList(reportVo);
    }
}
