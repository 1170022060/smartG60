package com.pingok.charge.service.roster;


import com.pingok.charge.domain.roster.vo.TblTractorListRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 牵引车名单 业务层
 *
 * @author ruoyi
 */
public interface ITractorRecordService {

    /**
     * 根据起止时间查询牵引车辆变动信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<TblTractorListRecord> selectByTime(String startTime, String endTime);

}
