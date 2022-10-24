package com.pingok.vocational.service.nameList;

import com.pingok.vocational.domain.nameList.TblEmgAppend;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IRescueService {

    List<Map> getEmgAppendList(String stationName,String version);

    List<TblEmgAppend> findById(Long id);
}
