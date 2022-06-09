package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.vo.OdRecordStaVo;
import com.pingok.vocational.domain.report.vo.OdRecordVelVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.mapper.report.TblOdRecordMapper;
import com.pingok.vocational.service.report.IOdRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车辆OD统计记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OdRecordServiceImpl implements IOdRecordService {
    @Autowired
    private TblOdRecordMapper tblOdRecordMapper;

    @Override
    public List<Map> selectOdRecordBySta(String stationId,  Date startDate, Date endDate, Integer hour) {
        return tblOdRecordMapper.selectOdRecordBySta( stationId,  startDate,  endDate,  hour);
    }

    @Override
    public List<OdRecordStaVo> selectOdRecordByStaList(ReportVo reportVo) {
        return tblOdRecordMapper.selectOdRecordByStaList(reportVo);
    }

    @Override
    public List<Map> selectOdRecordByClass(Integer vehClass, Date startDate, Date endDate, Integer hour) {
        return tblOdRecordMapper.selectOdRecordByClass( vehClass,  startDate,  endDate,  hour);
    }

    @Override
    public List<OdRecordVelVo> selectOdRecordByClassList(ReportVo reportVo) {

        return tblOdRecordMapper.selectOdRecordByClassList(reportVo);
    }
}
