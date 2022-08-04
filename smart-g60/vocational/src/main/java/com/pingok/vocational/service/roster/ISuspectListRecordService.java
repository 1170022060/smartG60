package com.pingok.vocational.service.roster;

import com.pingok.vocational.domain.roster.TblSuspectListRecord;

import java.util.Date;
import java.util.List;

/**
 * 疑似违法车辆名单 业务层
 *
 * @author ruoyi
 */
public interface ISuspectListRecordService {
    /**
     * 通过车牌、生效时间、结束时间查询疑似违法车辆名单
     *
     * @param vehicleId 车牌
     * @param startTime 生效时间
     * @param endTime 结束时间
     * @return 抢险救灾名单
     */
    List<TblSuspectListRecord> selectSuspectList(String vehicleId, Date startTime, Date endTime);
}
