package com.pingok.vocational.service.project.impl;

import com.pingok.vocational.domain.project.TblProjectInfo;
import com.pingok.vocational.mapper.project.TblProjectInfoMapper;
import com.pingok.vocational.service.project.IProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class ProjectInfoServiceImpl implements IProjectInfoService {

    @Autowired
    private TblProjectInfoMapper tblProjectInfoMapper;

    @Override
    public List<Map> selectProjectName() {
        return tblProjectInfoMapper.selectProjectName();
    }

    @Override
    public TblProjectInfo selectProjectInfoById(Long Id) {
        return tblProjectInfoMapper.selectByPrimaryKey(Id);
    }

}
