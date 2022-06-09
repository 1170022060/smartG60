package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblStatusListRecordMapper;
import com.pingok.vocational.service.roster.IStatusListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 状态名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class StatusListRecordServiceImpl implements IStatusListRecordService {

    @Autowired
    private TblStatusListRecordMapper tblStatusListRecordMapper;

    @Override
    public List<Map> selectStatusList(String cardId) {
        return  tblStatusListRecordMapper.selectStatusList(cardId);
    }
}
