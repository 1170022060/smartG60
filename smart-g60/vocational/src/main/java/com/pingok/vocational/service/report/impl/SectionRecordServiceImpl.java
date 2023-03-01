package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SectionRecordVo;
import com.pingok.vocational.mapper.report.TblSectionRecordMapper;
import com.pingok.vocational.service.report.ISectionRecordService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出入口段面记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SectionRecordServiceImpl implements ISectionRecordService {
    @Autowired
    private TblSectionRecordMapper tblSectionRecordMapper;

    @Override
    public List<Map> selectSectionRecord(String stationId, Date startDate, Date endDate, Integer direction) {

        return tblSectionRecordMapper.selectSectionRecord( stationId,  startDate,  endDate,  direction);
    }

    @Override
    public List<SectionRecordVo> selectSectionRecordList(ReportVo reportVo) {

        return tblSectionRecordMapper.selectSectionRecordList(reportVo);
    }

    @Override
    public List<Map> selectEnAnExFlow(String stationId, Date startDate, Date endDate, Integer direction) {
        return tblSectionRecordMapper.selectEnAndExFlow(DateUtils.getTimeDay(startDate).substring(0,4),stationId,startDate,endDate,direction);
    }
}
