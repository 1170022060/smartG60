package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.domain.nameList.TblBlackCardLogN;
import com.pingok.vocational.mapper.nameList.TblBlackCardLogNMapper;
import com.pingok.vocational.mapper.nameList.TblBlackCardStationNMapper;
import com.pingok.vocational.service.nameList.IBlackCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class BlackCardStationServiceImpl implements IBlackCardService {

    @Autowired
    private TblBlackCardStationNMapper tblBlackCardStationNMapper;
    @Autowired
    private TblBlackCardLogNMapper tblBlackCardLogNMapper;

    @Override
    public List<Map> getBlackCardList(String stationName, String version) {
        return tblBlackCardStationNMapper.getBlackCardList(stationName,version);
    }

    @Override
    public List<Map> findById(Long id) {
        return tblBlackCardLogNMapper.findById(id);
    }
}
