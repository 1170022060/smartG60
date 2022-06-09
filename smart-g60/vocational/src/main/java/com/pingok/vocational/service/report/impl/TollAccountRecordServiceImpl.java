package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.TollAccountRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.mapper.report.TblTollAccountRecordMapper;
import com.pingok.vocational.service.report.ITollAccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通行费核算记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TollAccountRecordServiceImpl implements ITollAccountRecordService {

    @Autowired
    private TblTollAccountRecordMapper tblTollAccountRecordMapper;

    @Override
    public List<Map> selectAccountRecord(Date startDate, Date endDate) {

        return tblTollAccountRecordMapper.selectAccountRecord(startDate, endDate);
    }

    @Override
    public List<TollAccountRecordVo> selectAccountRecorList(ReportVo reportVo) {
        return tblTollAccountRecordMapper.selectAccountRecorList(reportVo);
    }
}
