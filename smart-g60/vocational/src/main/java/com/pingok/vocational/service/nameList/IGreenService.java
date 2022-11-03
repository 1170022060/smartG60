package com.pingok.vocational.service.nameList;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IGreenService {

    /**
     * 查询綠通名单
     * @param stationName
     * @param version
     * @return
     */
    List<Map> getGreenList(String stationName, String version);

    /**
     * 根据id查询綠通名单明细
     * @param id
     * @return
     */
    List<Map> findById(Long id);
}
