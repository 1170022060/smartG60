package com.pingok.vocational.service.emergency;

import com.alibaba.fastjson.JSONArray;
import com.pingok.vocational.domain.emergency.TblEventPaln;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;

import java.util.List;
import java.util.Map;

/**
 * 应急预案 业务层
 *
 * @author ruoyi
 */
public interface IEventPalnService {

    /**
     * 根据应急预案ID查询设备列表
     * @return
     */
    JSONArray deviceList(Long eventId,Long planId, Integer type);

    /**
     * 根据事件id获取应急预案列表
     * @param id
     * @return
     */
    List<TblEventPaln> findByEventType(Long id);

    /**
     * 停用或启用应急预案
     * @param id
     * @param status
     */
    void disableOrEnable (Long id,Integer status);

    /**
     * 根据条件分页查询预案列表
     *
     * @param planTitle 标题
     * @return
     */
    List<Map> selectEventPalnList(String planTitle);

    /**
     * 校验预案名称
     *
     * @param tblEventPaln
     * @return
     */
    String checkEventPalnNameUnique(TblEventPaln tblEventPaln);

    /**
     * 根据id查询详情
     *
     * @param id 主键id
     * @return
     */
    TblEventPaln findById(Long id);

    /**
     * 新增
     *
     * @param tblEventPaln 应急预案信息
     * @return 结果
     */
    int insert(TblEventPaln tblEventPaln);

    /**
     * 修改
     *
     * @param tblEventPaln 应急预案信息
     * @return 结果
     */
    int update(TblEventPaln tblEventPaln);

    /**
     * 选择应急小组时查询应急小组信息
     * @param suppliesType
     * @return
     */
    List<Map> selectPlanGroup(Integer suppliesType);

}
