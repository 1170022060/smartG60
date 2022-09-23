package com.pingok.vocational.service.roster;

import com.pingok.vocational.domain.roster.TblEpidemicListRecord;

import java.util.Date;
import java.util.List;
/**
 * 中高风险地区名单 业务层
 *
 * @author ruoyi
 */
public interface IEpidemicListRecordService {
    /**
     * 通过生效时间查询中高风险地区名单
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 抢险救灾名单
     */
    List<TblEpidemicListRecord> selectEpidemicList(Date startTime, Date endTime);
}
