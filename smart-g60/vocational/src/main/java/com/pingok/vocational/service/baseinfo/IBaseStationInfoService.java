package com.pingok.vocational.service.baseinfo;


import com.pingok.vocational.domain.baseinfo.TblBaseStationInfo;
import com.pingok.vocational.domain.baseinfo.vo.StationInfo;

import java.util.List;
import java.util.Map;

/**
 * 收费站基础信息 业务层
 *
 * @author ruoyi
 */

public interface IBaseStationInfoService {


    /**
     * 根据网络号和收费站ID查询
     * @param netWork
     * @param stationId
     * @return
     */
    TblBaseStationInfo findByNetWorkAndStationId(String netWork,String stationId);

    /**
     * 根据Id查询收费站基础信息
     *
     * @param Id Id
     * @return 收费站基础信息
     */
    public TblBaseStationInfo selectBaseStationById(Long Id);

    /**
     * 根据收费站名模糊查询收费站基础信息
     *
     * @param stationName 收费站名
     * @return 收费站基础信息
     */
    public List<Map> selectBaseStation(String stationName);

    /**
     * 新增收费站基础信息
     *
     * @param tblBaseStationInfo 收费站基础信息
     * @return 结果
     */
    public int insertBaseStation(TblBaseStationInfo tblBaseStationInfo);

    /**
     * 修改收费站基础信息
     *
     * @param tblBaseStationInfo 收费站基础信息
     * @return 结果
     */
    public int updateBaseStation(TblBaseStationInfo tblBaseStationInfo);

    /**
     * 返回收费站名和收费站ID
     *
     * @return 收费站基础信息
     */
    public List<Map> selectStationInfo();

    /**
     * 返回收费站名和收费站ID(带路段中心)
     *
     * @return 收费站基础信息
     */
    public List<Map> selectStationCenterInfo();

    /**
     * 返回收费站名和收费站ID
     *
     * @return 收费站基础信息
     */
    public List<StationInfo> selectStationLaneInfo();
}
