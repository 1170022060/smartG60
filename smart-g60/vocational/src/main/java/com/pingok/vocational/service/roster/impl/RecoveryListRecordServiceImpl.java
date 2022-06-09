package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblRecoveryListRecordMapper;
import com.pingok.vocational.service.roster.IRecoveryListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 追缴名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RecoveryListRecordServiceImpl implements IRecoveryListRecordService {

    @Autowired
    private TblRecoveryListRecordMapper tblRecoveryListRecordMapper;

    @Override
    public List<Map> selectRecoveryList(String vehPlate) {

        return tblRecoveryListRecordMapper.selectRecoveryList(vehPlate);
    }
}
