package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.domain.nameList.TblEpidemicListRecordN;
import com.pingok.vocational.mapper.nameList.TblEpidemicListRecordNMapper;
import com.pingok.vocational.mapper.nameList.TblEpidemicStationUsedMapper;
import com.pingok.vocational.service.nameList.IEpidemicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class EpidemicServiceImpl implements IEpidemicService {
    @Autowired
    private TblEpidemicStationUsedMapper tblEpidemicStationUsedMapper;
    @Autowired
    private TblEpidemicListRecordNMapper tblEpidemicListRecordNMapper;

    @Override
    public List<Map> getEpidemicList(String stationName,String version) {
        return tblEpidemicStationUsedMapper.getEpidemicList(stationName,version);
    }

    @Override
    public List<TblEpidemicListRecordN> findById(Long id) {
        Example example;
        example = new Example(TblEpidemicListRecordN.class);
        example.createCriteria().andEqualTo("versionId",id);
        return tblEpidemicListRecordNMapper.selectByExample(example);
    }
}
