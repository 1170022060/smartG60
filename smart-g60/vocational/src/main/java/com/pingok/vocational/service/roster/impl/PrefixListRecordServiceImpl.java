package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.domain.roster.TblPrefixListRecord;
import com.pingok.vocational.mapper.roster.TblPrefixListRecordMapper;
import com.pingok.vocational.service.roster.IPrefixListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
/**
 * 中高风险地区车牌前缀名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PrefixListRecordServiceImpl implements IPrefixListRecordService {

    @Autowired
    private TblPrefixListRecordMapper tblPrefixListRecordMapper;

    @Override
    public List<TblPrefixListRecord> selectPrefixList(String prefix, Date startTime, Date endTime) {

        Example example = new Example(TblPrefixListRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (prefix!=null) {
            criteria.andLike("prefix","%"+ prefix+ "%");
        }
        if (startTime!=null) {
            criteria.andGreaterThanOrEqualTo("startTime", startTime);
        }
        if (endTime!=null) {
            criteria.andLessThanOrEqualTo("startTime", endTime);
        }
        return tblPrefixListRecordMapper.selectByExample(example);
    }
}
