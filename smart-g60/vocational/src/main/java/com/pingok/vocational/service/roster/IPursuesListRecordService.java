package com.pingok.vocational.service.roster;

import com.pingok.vocational.domain.roster.TblPursuesListRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 追讨名单 业务层
 *
 * @author ruoyi
 */
public interface IPursuesListRecordService {
    /**
     * 通过起止时间查询追讨名单
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 追讨名单
     */
    List<Map> selectPursuesList(Date startTime, Date endTime);

    /**
     * 导入追讨名单
     *
     * @param pursuesList 追讨名单列表
     * @return 结果
     */
    public String importPursues(List<TblPursuesListRecord> pursuesList);
}
