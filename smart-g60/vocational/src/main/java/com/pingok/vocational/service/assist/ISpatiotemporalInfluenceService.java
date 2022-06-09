package com.pingok.vocational.service.assist;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段时空影响预估表 服务层
 *
 * @author ruoyi
 */

public interface ISpatiotemporalInfluenceService {

    /**
     * 通过起止日期查询对应事件ID
     *
     * @param eventType 事件类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件ID
     */
    List<Map> selectSpatiotemporal(String eventType,Date startTime, Date endTime);

}
