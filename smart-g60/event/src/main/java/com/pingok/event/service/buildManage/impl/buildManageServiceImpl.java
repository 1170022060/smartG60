package com.pingok.event.service.buildManage.impl;

import com.pingok.event.domain.buildManage.TblBuildManage;
import com.pingok.event.mapper.buildManage.TblBuildManaMapper;
import com.pingok.event.service.buildManage.IBuildManageService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class buildManageServiceImpl implements IBuildManageService {
    @Autowired
    TblBuildManaMapper tblBuildManaMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<Map> getBuilManaInfo(String content, Date startTime, Date endTime) {
        return tblBuildManaMapper.getBuildManaInfo(content,startTime,endTime);
    }

    @Override
    public int add(TblBuildManage tblBuildManage) {
        tblBuildManage.setId(remoteIdProducerService.nextId());
        tblBuildManage.setCreateTime(DateUtils.getNowDate());
        tblBuildManage.setCreateUserId(SecurityUtils.getUserId());
        return tblBuildManaMapper.insert(tblBuildManage);
    }

    @Override
    public int edit(TblBuildManage tblBuildManage) {
//        Example example = new Example(TblBuildManage.class);
//        example.createCriteria().andEqualTo("ID",tblBuildManage.getId());

        tblBuildManage.setUpdateTime(DateUtils.getNowDate());
        tblBuildManage.setUpdateUserId(SecurityUtils.getUserId());
        return tblBuildManaMapper.updateByPrimaryKey(tblBuildManage);
    }
}
