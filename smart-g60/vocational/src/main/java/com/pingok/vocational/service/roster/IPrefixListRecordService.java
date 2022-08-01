package com.pingok.vocational.service.roster;

import com.pingok.vocational.domain.roster.TblPrefixListRecord;

import java.util.Date;
import java.util.List;
/**
 * 中高风险地区车牌前缀名单 业务层
 *
 * @author ruoyi
 */
public interface IPrefixListRecordService {
    /**
     * 通过车牌前缀、生效时间查询中高风险地区车牌前缀名单
     *
     * @param prefix 车牌前缀
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 抢险救灾名单
     */
    List<TblPrefixListRecord> selectPrefixList(String prefix,Date startTime, Date endTime);
}
