package com.pingok.vocational.service.field.impl;

import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.domain.field.vo.FieldVo;
import com.pingok.vocational.mapper.field.TblFieldInfoMapper;
import com.pingok.vocational.service.field.TblFieldInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 场地信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblFieldInfoServiceImpl implements TblFieldInfoService {
    @Autowired
    private TblFieldInfoMapper tblFieldInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblFieldInfo selectFieldInfoById(Long Id) {
        return tblFieldInfoMapper.selectByPrimaryKey(Id);
    }


    @Override
    public List<Map> selectFieldInfo(String stationBelong, String roadBelong, String fieldName, Integer type,Integer status) {
        return tblFieldInfoMapper.selectFieldInfo(stationBelong, roadBelong, fieldName, type, status);
    }


    @Override
    public int insertFieldInfo(TblFieldInfo tblFieldInfo) {

        tblFieldInfo.setId(remoteIdProducerService.nextId());
        tblFieldInfo.setStatus(1);
        tblFieldInfo.setCreateTime(new Date());
        tblFieldInfo.setFieldNum(PinYinUtil.getPinYinHeadChar(tblFieldInfo.getFieldName()));
        tblFieldInfo.setCreateUserId(SecurityUtils.getUserId());
        return tblFieldInfoMapper.insert(tblFieldInfo);
    }

    @Override
    public int updateFieldInfo(TblFieldInfo tblFieldInfo) {
        tblFieldInfo.setUpdateTime(new Date());
        tblFieldInfo.setUpdateUserId(SecurityUtils.getUserId());
        if(StringUtils.isNotNull(tblFieldInfo.getFieldName()))
        {
            tblFieldInfo.setFieldNum(PinYinUtil.getPinYinHeadChar(tblFieldInfo.getFieldName()));
        }
        return tblFieldInfoMapper.updateByPrimaryKeySelective(tblFieldInfo);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblFieldInfo tblFieldInfo= tblFieldInfoMapper.selectByPrimaryKey(id);
        tblFieldInfo.setStatus(status);
        return tblFieldInfoMapper.updateByPrimaryKeySelective(tblFieldInfo);
    }

    @Override
    public String checkFieldNameUnique(TblFieldInfo tblFieldInfo) {
        Long id = StringUtils.isNull(tblFieldInfo.getId()) ? -1L : tblFieldInfo.getId();
        Integer type = tblFieldInfo.getType();
        TblFieldInfo info = tblFieldInfoMapper.checkFieldNameUnique(tblFieldInfo.getFieldName(),type);
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<FieldVo> selectFieldTypeName() {

        List<FieldVo> lists =tblFieldInfoMapper.selectFieldTypeName();
        for (FieldVo list : lists) {
            list.setFieldInfo(tblFieldInfoMapper.selectFieldName(list.getType()));
        }
        return lists;
    }

}
