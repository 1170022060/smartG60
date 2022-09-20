package com.pingok.datacenter.service.rush;

import com.pingok.datacenter.domain.rush.TblRushRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 闯关确认 业务层
 *
 * @author ruoyi
 */
public interface IRushService {

    /**
     * 闯关确认
     * @param id
     * @param status
     */
    void rushConfirm(Long id,Integer status);

    /**
     * 闯关识别
     *
     * @param year
     * @param startTime
     * @param endTime
     * @param twoHours
     * @return
     */
    void rushRecord(String year, String startTime, String endTime, String twoHours);

    /**
     * 查询闯关车列表
     * @param stationName
     * @param startTime
     * @param endTime
     * @return
     */
    List<TblRushRecord> list(String stationName, String vehPlate,String startTime, String endTime);
}
