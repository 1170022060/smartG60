package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.IntransitRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.mapper.report.TblIntransitRecordMapper;
import com.pingok.vocational.service.report.IIntransitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在途总流量记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class IntransitRecordServiceImpl implements IIntransitRecordService {
    @Autowired
    private TblIntransitRecordMapper tblIntransitRecordMapper;


    @Override
    public List<Map> selectIntransitRecord(Date startDate, Date endDate) {
        return tblIntransitRecordMapper.selectIntransitRecord(startDate,  endDate);
    }

    @Override
    public List<IntransitRecordVo> selectIntransitRecordList(Date startDate, Date endDate) {
        return tblIntransitRecordMapper.selectIntransitRecordList(startDate,endDate);
    }
}
