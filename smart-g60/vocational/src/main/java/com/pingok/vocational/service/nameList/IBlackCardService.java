package com.pingok.vocational.service.nameList;

import com.pingok.vocational.domain.nameList.TblBlackCardLogN;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IBlackCardService {
    /**
     * 获取状态名单信息
     * @param stationName
     * @param version
     * @return
     */
    List<Map> getBlackCardList(String stationName, String version);

    /**
     * 根据id查询状态名单详情
     * @param id
     * @return
     */
    List<Map> findById(Long id);

    /**
     * 统计各版本更新情况（状态名单、中高风险、最小费率）
     */
    Object statisticsVersion();

}
