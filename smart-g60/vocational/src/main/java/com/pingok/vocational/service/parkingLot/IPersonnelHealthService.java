package com.pingok.vocational.service.parkingLot;

import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.parkingLot.TblPersonnelHealth;
import com.ruoyi.common.core.web.domain.AjaxResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员健康信息 业务层
 *
 * @author xia
 */
public interface IPersonnelHealthService {

    /**
     * 根据Id查询人员健康信息
     *
     * @param Id Id
     * @return 人员健康信息
     */
    public TblPersonnelHealth selectPersonnelHealthById(Long Id);

    /**
     * 根据姓名、区域Id、查询人员健康信息
     *
     * @param serviceId 服务区id
     * @param fieldId 区域Id
     * @param date 日期
     * @return 人员健康信息
     */
    public List<Map> selectPersonnelHealth(Long serviceId, Long fieldId, Date date);

    /**
     * 根据类型、区域Id、查询人员健康信息统计
     *
     * @param type 类型
     * @param fieldId 区域Id
     * @param date 日期
     * @return 人员健康信息
     */
    public List<Map> selectHealthStatistics(Integer type, Long fieldId, Date date);

    public List<Map> selectHealthMonitor(Date date);

    /**
     * 新增人员健康信息
     *
     * @param personnelHealth 人员健康信息
     * @return 结果
     */
    public AjaxResult insertPersonnelHealth(JSONObject personnelHealth);

    /**
     * 修改人员健康信息
     *
     * @param tblPersonnelHealth 人员健康信息
     * @return 结果
     */
    public int updatePersonnelHealth(TblPersonnelHealth tblPersonnelHealth);
}
