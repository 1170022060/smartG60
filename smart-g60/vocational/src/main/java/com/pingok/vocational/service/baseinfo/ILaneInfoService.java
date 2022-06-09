package com.pingok.vocational.service.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblLaneInfo;

import java.util.List;
import java.util.Map;

/**
 * 车道基础信息 业务层
 *
 * @author ruoyi
 */
public interface ILaneInfoService {

    /**
     * 根据车道hex查询
     * @param laneHex
     * @return
     */
    TblLaneInfo findByLaneHex(String laneHex);

    /**
     * 根据Id查询车道基础信息
     *
     * @param Id Id
     * @return 车道基础信息
     */
    public TblLaneInfo selectLaneById(Long Id);

    /**
     * 根据站编码、使用状态模糊查询车道基础信息
     *
     * @param stationId 站编码
     * @param status 使用状态
     * @return 车道基础信息
     */
    public List<Map> selectLane(String stationId,Integer status);

    /**
     * 新增车道基础信息
     *
     * @param tblLaneInfo 车道基础信息
     * @return 结果
     */
    public int insertLane(TblLaneInfo tblLaneInfo);

    /**
     * 修改车道基础信息
     *
     * @param tblLaneInfo 车道基础信息
     * @return 结果
     */
    public int updateLane(TblLaneInfo tblLaneInfo);

    /**
     * 根据id,更改车道基础信息状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验车道名
     *
     * @param tblLaneInfo 车道基础信息
     * @return 结果
     */
    public String checkLaneNameUnique(TblLaneInfo tblLaneInfo);

    /**
     * 返回车道名和车道ID
     *
     * @return 车道基础信息
     */
    public List<Map> selectLaneInfo();
}
