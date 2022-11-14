package com.pingok.monitor.service.gantry;

import com.pingok.monitor.domain.gantry.TblGantryStatus;
import com.pingok.monitor.domain.gantry.vo.GantryEnum;
import com.pingok.monitor.domain.gantry.vo.GantryV2X;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架 业务层
 *
 * @author qiumin
 */
public interface IGantryService {
    /**
     * 查询门架状态列表
     * @return
     */
    GantryEnum gantryStatus();

    /**
     * 根据门架主键id查询门架状态详情
     * @param id
     * @return
     */
    TblGantryStatus findById(Long id);

    /**
     * 车路协同接口：向门架系统发送
     * @param data 事件内容
     * @return true-成功失败
     */
    boolean gantryV2X(GantryV2X data);

    /**
     * 获取车路协同发布记录
     * @param gantryId
     * @param eventType
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> getRecord(String gantryId, String eventType, Integer status, Date startTime, Date endTime);
}
