package com.pingok.vocational.service.report;

import com.pingok.vocational.domain.report.vo.IntransitRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在途总流量记录 服务层
 *
 * @author ruoyi
 */
public interface IIntransitRecordService {
    /**
     * 通过起止日期查询在途总流量记录
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 在途总流量记录
     */
    List<Map> selectIntransitRecord(Date startDate, Date endDate);

    /**
     * 通过起止日期查询在途总流量记录
     *
     * @param reportVo
     * @return 在途总流量记录
     */
    List<IntransitRecordVo> selectIntransitRecordList(ReportVo reportVo);
}
