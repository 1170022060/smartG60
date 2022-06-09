package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.domain.report.TblRoadTripTime;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.RoadTripTimeVo;
import com.pingok.vocational.mapper.report.TblRoadTripTimeMapper;
import com.pingok.vocational.service.report.IRoadTripTimeService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段行程时间 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RoadTripTimeServiceImpl implements IRoadTripTimeService {

    @Autowired
    private TblRoadTripTimeMapper tblRoadTripTimeMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<Map> selectRoadTripTime(Integer hour, Date startDate, Date endDate) {
        return tblRoadTripTimeMapper.selectRoadTripTime(hour, startDate, endDate);
    }

    @Override
    public List<RoadTripTimeVo> selectRoadTripTimeList(ReportVo reportVo) {
        return tblRoadTripTimeMapper.selectRoadTripTimeList(reportVo);
    }

    @Override
    public List<TblRoadTripTime> selectTripTimeHour() {
        return tblRoadTripTimeMapper.selectTripTimeHour();
    }

    @Override
    public int insertTripTimeHour() {
        List<TblRoadTripTime> list = tblRoadTripTimeMapper.selectTripTimeHour();
        int row=0;
        for(int i=0;i<list.size();i++)
        {
            TblRoadTripTime tblRoadTripTime = list.get(i);
            tblRoadTripTime.setId(remoteIdProducerService.nextId());
            tblRoadTripTimeMapper.insert(tblRoadTripTime);
            row++;
        }
        return row;
   }
}
