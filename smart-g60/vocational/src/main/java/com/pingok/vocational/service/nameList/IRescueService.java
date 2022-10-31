package com.pingok.vocational.service.nameList;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IRescueService {

    List<Map> getEmgAppendList(String stationName,String version);

    List<Map> findById(Long id);
}
