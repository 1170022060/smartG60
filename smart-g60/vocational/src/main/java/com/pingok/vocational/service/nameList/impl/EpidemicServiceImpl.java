package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.domain.roster.TblEpidemicListRecord;
import com.pingok.vocational.mapper.nameList.TblEpidemicListMapper;
import com.pingok.vocational.service.nameList.IEpidemicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class EpidemicServiceImpl implements IEpidemicService {
    @Autowired
    private TblEpidemicListMapper tblEpidemicListMapper;

    @Override
    public List<Map> getEpidemicList(String stationName,String version) {
        return tblEpidemicListMapper.getEpidemicList(stationName,version);
    }
}
