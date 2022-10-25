package com.pingok.vocational.service.nameList;

import com.pingok.vocational.domain.nameList.TblRate;
import com.pingok.vocational.domain.nameList.TblRateProv;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IRateService {
    /**
     * 查询最小费率表信息
     * @param stationName
     * @param version
     * @return
     */
    List<Map> getRateList(String stationName,String version);

    /**
     * 根据id查询最小费率表详情
     * @param id
     * @return
     */
    List<TblRate> findRateById(Long id);

    /**
     *根据最小费率详情表的rateId查询子表信息
     * @param id
     * @return
     */
    List<TblRateProv> findRateProvById(Long id);
}
