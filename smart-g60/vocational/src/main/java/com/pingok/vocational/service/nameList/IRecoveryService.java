package com.pingok.vocational.service.nameList;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IRecoveryService {
    /**
     * 查询追缴名单
     * @param stationName
     * @param version
     * @return
     */
    List<Map> getRecoveryList(String stationName,String version);

    /**
     * 根据id查询追缴名单明细
     * @param id
     * @return
     */
    List<Map> findById(Long id);
}
