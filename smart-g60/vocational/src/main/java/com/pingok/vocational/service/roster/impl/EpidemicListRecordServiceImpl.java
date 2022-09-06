package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.domain.roster.TblEpidemicListRecord;
import com.pingok.vocational.mapper.roster.TblEpidemicListRecordMapper;
import com.pingok.vocational.service.roster.IEpidemicListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
/**
 * 中高风险地区名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class EpidemicListRecordServiceImpl implements IEpidemicListRecordService {

    @Autowired
    private TblEpidemicListRecordMapper tblEpidemicListRecordMapper;

    @Override
    public List<TblEpidemicListRecord> selectEpidemicList(Date startTime, Date endTime) {
        Example example = new Example(TblEpidemicListRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (startTime!=null) {
            criteria.andGreaterThanOrEqualTo("startTime", startTime);
        }
        if (endTime!=null) {
            criteria.andLessThanOrEqualTo("startTime", endTime);
        }
        example.setOrderByClause("START_TIME asc");
        return tblEpidemicListRecordMapper.selectByExample(example);
    }
}
