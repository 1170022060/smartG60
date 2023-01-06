package com.pingok.vocational.service.business.impl;

import com.pingok.vocational.domain.business.vo.SummaryVo;
import com.pingok.vocational.domain.business.vo.TransSummaryVo;
import com.pingok.vocational.mapper.business.TblTransSummaryMapper;
import com.pingok.vocational.service.business.ITransSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流水汇总 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TransSummaryServiceImpl implements ITransSummaryService {
    @Autowired
    private TblTransSummaryMapper tblTransSummaryMapper;

    @Override
    public List<Map> selectTransSummary(Date enStartTime, Date enEndTime, Date enWorkDate, String enStationId, String passId, String enGid, Integer enPassType, Integer enShift, String enVehPlate, String enCardId, Date exStartTime, Date exEndTime, Date exWorkDate, String exStationId, String exGid, Integer exPassType, Integer exShift, String exVehPlate, String exCardId, Integer payWay) {
        return tblTransSummaryMapper.selectTransSummary(enStartTime,  enEndTime,  enWorkDate,  enStationId,  passId,  enGid,  enPassType,  enShift,  enVehPlate,  enCardId,  exStartTime,  exEndTime,  exWorkDate,  exStationId,  exGid,  exPassType,  exShift,  exVehPlate,  exCardId,  payWay);
    }

    @Override
    public List<Map> selectEnTransSummary(Date enStartTime, Date enEndTime, Date enWorkDate, String enStationId, String passId, String enGid, Integer enPassType, Integer enShift, String enVehPlate, String enCardId) {
        return tblTransSummaryMapper.selectEnTransSummary(enStartTime,  enEndTime,  enWorkDate,  enStationId,  passId,  enGid,  enPassType,  enShift,  enVehPlate,  enCardId);
    }

    @Override
    public List<Map> selectExTransSummary(String passId, Date exStartTime, Date exEndTime, Date exWorkDate, String exStationId, String exGid, Integer exPassType, Integer exShift, String exVehPlate, String exCardId, Integer payWay) {
        return tblTransSummaryMapper.selectExTransSummary(passId, exStartTime,  exEndTime,  exWorkDate,  exStationId,  exGid,  exPassType,  exShift,  exVehPlate,  exCardId,  payWay);
    }

    @Override
    public List<TransSummaryVo> selectTransSummaryList(SummaryVo summaryVo) {
        return tblTransSummaryMapper.selectTransSummaryList(summaryVo);
    }
}
