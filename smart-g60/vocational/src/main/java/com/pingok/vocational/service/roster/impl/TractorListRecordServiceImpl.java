package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.domain.roster.TblTractorListRecord;
import com.pingok.vocational.mapper.roster.TblTractorListRecordMapper;
import com.pingok.vocational.service.roster.ITractorListRecordService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 牵引车信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TractorListRecordServiceImpl implements ITractorListRecordService {

    @Autowired
    private TblTractorListRecordMapper tblTractorListRecordMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<TblTractorListRecord> selectByTime(Date startTime, Date endTime) {
        Example example = new Example(TblTractorListRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (startTime!=null) {
            criteria.andGreaterThanOrEqualTo("createTime", startTime).orGreaterThanOrEqualTo("updateTime", startTime);
        }
        if (endTime!=null) {
            criteria.andLessThanOrEqualTo("createTime", endTime).orLessThanOrEqualTo("updateTime", endTime);
        }
        return tblTractorListRecordMapper.selectByExample(example);
    }

    @Override
    public TblTractorListRecord selectTractorListById(Long Id) {
        return tblTractorListRecordMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectTractorList(String vehPlate, Integer status) {
        return tblTractorListRecordMapper.selectTractorList(vehPlate, status);
    }

    @Override
    public int insertTractorList(TblTractorListRecord tblTractorListRecord) {
        tblTractorListRecord.setId(remoteIdProducerService.nextId());
        tblTractorListRecord.setStatus(1);
        tblTractorListRecord.setCreateTime(new Date());
        tblTractorListRecord.setCreateUserId(SecurityUtils.getUserId());
        return tblTractorListRecordMapper.insert(tblTractorListRecord);
    }

    @Override
    public int updateTractorList(TblTractorListRecord tblTractorListRecord) {
        tblTractorListRecord.setUpdateTime(new Date());
        tblTractorListRecord.setUpdateUserId(SecurityUtils.getUserId());
        return tblTractorListRecordMapper.updateByPrimaryKeySelective(tblTractorListRecord);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblTractorListRecord tblTractorListRecord = tblTractorListRecordMapper.selectByPrimaryKey(id);
        tblTractorListRecord.setUpdateTime(new Date());
        tblTractorListRecord.setUpdateUserId(SecurityUtils.getUserId());
        tblTractorListRecord.setStatus(status);
        return tblTractorListRecordMapper.updateByPrimaryKeySelective(tblTractorListRecord);
    }

    @Override
    public String checkVehPlateUnique(TblTractorListRecord tblTractorListRecord) {
        Long id = StringUtils.isNull(tblTractorListRecord.getId()) ? -1L : tblTractorListRecord.getId();
        TblTractorListRecord info = tblTractorListRecordMapper.checkVehPlateUnique(tblTractorListRecord.getVehPlate(), tblTractorListRecord.getVehColor());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
