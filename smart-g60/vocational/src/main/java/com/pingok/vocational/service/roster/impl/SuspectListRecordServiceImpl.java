package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.domain.roster.TblSuspectListRecord;
import com.pingok.vocational.mapper.roster.TblSuspectListRecordMapper;
import com.pingok.vocational.service.roster.ISuspectListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
/**
 * 疑似违法车辆名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SuspectListRecordServiceImpl implements ISuspectListRecordService {

    @Autowired
    private TblSuspectListRecordMapper tblSuspectListRecordMapper;

    @Override
    public List<TblSuspectListRecord> selectSuspectList(String vehicleId, Date startTime, Date endTime) {

        Example example = new Example(TblSuspectListRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (vehicleId!=null) {
            criteria.andLike("vehicleId","%"+ vehicleId +"%");
        }
        if (startTime!=null) {
            criteria.andGreaterThanOrEqualTo("startTime", startTime);
        }
        if (endTime!=null) {
            criteria.andLessThanOrEqualTo("endTime", endTime);
        }
        return tblSuspectListRecordMapper.selectByExample(example);
    }
}
