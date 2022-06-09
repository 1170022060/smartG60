package com.pingok.vocational.service.report;


import com.pingok.vocational.domain.report.vo.TollAccountRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITollAccountRecordService {
    /**
     * 通过起止日期查询通行费核算记录
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 通行费核算记录
     */
    List<Map> selectAccountRecord(Date startDate, Date endDate);

    /**
     * 通过起止日期查询通行费核算记录
     *
     * @param reportVo
     * @return 通行费核算记录
     */
    List<TollAccountRecordVo> selectAccountRecorList(ReportVo reportVo);
}
