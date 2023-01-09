package com.pingok.vocational.service.business.impl;

import com.pingok.vocational.domain.business.vo.LprSummaryVo;
import com.pingok.vocational.domain.business.vo.SummaryVo;
import com.pingok.vocational.mapper.business.TblLprSummaryMapper;
import com.pingok.vocational.service.business.ILprSummaryService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车道牌识 服务层处理
 *
 * @author ruoyi
 */
@Service
public class LprSummaryServiceImpl implements ILprSummaryService {

    @Autowired
    private TblLprSummaryMapper tblLprSummaryMapper;

    @Override
    public List<Map> selectLprTrans(Date enStartTime, Date enEndTime, String enStationId, Integer enLaneType, String enVehPlate, Date exStartTime, Date exEndTime, String exStationId, Integer exLaneType, String exVehPlate) {
        return tblLprSummaryMapper.selectLprTrans(enStartTime, enEndTime, enStationId, enLaneType, enVehPlate, exStartTime, exEndTime, exStationId, exLaneType, exVehPlate);
    }

    @Override
    public List<Map> selectEnLprTrans(Date enStartTime, Date enEndTime, String enStationId, Integer enLaneType, String enVehPlate) {
        return tblLprSummaryMapper.selectEnLprTrans(DateUtils.getTimeDay(enStartTime).substring(0,4),enStartTime, enEndTime, enStationId, enLaneType, enVehPlate);
    }

    @Override
    public List<Map> selectExLprTrans(Date exStartTime, Date exEndTime, String exStationId, Integer exLaneType, String exVehPlate) {
        return tblLprSummaryMapper.selectExLprTrans(DateUtils.getTimeDay(exStartTime).substring(0,4),exStartTime, exEndTime, exStationId, exLaneType, exVehPlate);
    }

    @Override
    public List<LprSummaryVo> selectLprTransList(SummaryVo summaryVo) {
        return tblLprSummaryMapper.selectLprTransList(summaryVo);
    }
}
