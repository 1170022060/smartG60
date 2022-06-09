package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.GantryRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.mapper.report.TblGantryRecordMapper;
import com.pingok.vocational.service.report.IGantryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架段面记录表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GantryRecordServiceImpl implements IGantryRecordService {
    @Autowired
    private TblGantryRecordMapper tblGantryRecordMapper;

    @Override
    public List<Map> selectGantryRecord(String gantryId, Date startDate, Date endDate) {
        return tblGantryRecordMapper.selectGantryRecord(gantryId,  startDate,  endDate);
    }

    @Override
    public List<GantryRecordVo> selectGantryRecordList(ReportVo reportVo) {
            return tblGantryRecordMapper.selectGantryRecordList(reportVo);
    }
}
