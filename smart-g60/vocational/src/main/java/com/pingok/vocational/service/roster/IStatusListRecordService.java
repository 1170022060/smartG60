package com.pingok.vocational.service.roster;

import java.util.List;
import java.util.Map;

/**
 * 状态名单 业务层
 *
 * @author ruoyi
 */
public interface IStatusListRecordService {
    /**
     * 通过卡号查询状态名单
     *
     * @param cardId ETC卡号
     * @return 状态名单记录
     */
    List<Map> selectStatusList(String cardId);
}
