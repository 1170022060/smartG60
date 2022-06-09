package com.pingok.vocational.service.assist.impl;

import com.pingok.vocational.mapper.assist.TblSpatiotemporalInfluenceMapper;
import com.pingok.vocational.service.assist.ISpatiotemporalInfluenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段时空影响预估表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SpatiotemporalInfluenceServiceImpl implements ISpatiotemporalInfluenceService {

    @Autowired
    private TblSpatiotemporalInfluenceMapper tblSpatiotemporalInfluenceMapper;

    @Override
    public List<Map> selectSpatiotemporal(String eventType, Date startTime, Date endTime) {

        List<Long> list = tblSpatiotemporalInfluenceMapper.selectEvent(eventType, startTime, endTime);
        Long[] ids = list.stream().toArray(Long[]::new);
        return tblSpatiotemporalInfluenceMapper.selectSpatiotemporal(ids);
    }
}
