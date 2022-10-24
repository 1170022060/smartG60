package com.pingok.vocational.service.nameList;

import com.pingok.vocational.domain.roster.TblEpidemicListRecord;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IEpidemicService {
    /**
     * 查询中高风险地区
     * @param stationName
     * @param version
     * @return
     */
    List<Map> getEpidemicList(String stationName,String version);

    /**
     * 根据id查询中高风险地区明细
     * @param id
     * @return
     */
//    TblEpidemicListRecord findById(Long id);
}
