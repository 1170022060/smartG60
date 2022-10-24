package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.domain.nameList.TblAuditData;
import com.pingok.vocational.mapper.nameList.TblAubitDataMapper;
import com.pingok.vocational.mapper.nameList.TblRecoveryMapper;
import com.pingok.vocational.service.nameList.IRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class RecoveryServiceImpl implements IRecoveryService {
    @Autowired
    private TblRecoveryMapper tblRecoveryMapper;
    @Autowired
    private TblAubitDataMapper tblAubitDataMapper;

    @Override
    public List<Map> getRecoveryList(String stationName,String version) {
        return tblRecoveryMapper.getRecoveryList(stationName,version);
    }

    @Override
    public TblAuditData findById(Long id) {
        Example example;
        example = new Example(TblAuditData.class);
        example.createCriteria().andEqualTo("versionId",id);
        return tblAubitDataMapper.selectOneByExample(example);
    }
}
