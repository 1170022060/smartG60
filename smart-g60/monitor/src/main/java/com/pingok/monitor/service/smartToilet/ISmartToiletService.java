package com.pingok.monitor.service.smartToilet;

import java.util.List;
import java.util.Map;

/**
 * 智慧厕所 业务层
 *
 * @author qiumin
 */
public interface ISmartToiletService {
    /**
     * 根据场地编号查询智慧厕所信息
     * @param fieldNum 场地编号
     * @return
     */
    List<Map> findByFieldNum(String fieldNum);
}
