package com.pingok.vocational.service.roster;

import java.util.List;
import java.util.Map;

/**
 * 追缴名单 业务层
 *
 * @author ruoyi
 */
public interface IRecoveryListRecordService {
    /**
     * 通过车牌查询追缴名单
     *
     * @param vehPlate 车牌
     * @return 追缴名单
     */
    List<Map> selectRecoveryList(String vehPlate);
}
