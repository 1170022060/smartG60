package com.pingok.vocational.service.roster;

import java.util.List;
import java.util.Map;
/**
 * 绿通车辆信息 业务层
 *
 * @author ruoyi
 */
public interface IGreenListRecordService {
    /**
     * 通过车牌查询绿通车辆名单
     *
     * @param vehPlate 车牌
     * @return 绿通车辆名单
     */
    List<Map> selectGreenList(String vehPlate);
}
