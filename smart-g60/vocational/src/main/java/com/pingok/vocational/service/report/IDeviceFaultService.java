package com.pingok.vocational.service.report;

import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.domain.report.vo.DeviceFaultSearch;
import com.pingok.vocational.domain.report.vo.DeviceFaultTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备故障记录表 服务层
 *
 * @author ruoyi
 */
public interface IDeviceFaultService {


    /**
     * 设备故障统计
     * @return
     */
    List<Map> faultStatistics();
    /**
     * 新增
     * @param tblDeviceFault
     */
    void add(TblDeviceFault tblDeviceFault);
    /**
     * 根据id查询详情
     * @param id 故障id
     * @return
     */
    TblDeviceFault findById(Long id);
    /**
     * 故障解除
     * @param id 故障id
     * @param remark 备注
     */
    void relieve(Long id, String remark);
    /**
     * 故障确认
     * @param id 故障id
     * @param remark 备注
     */
    void confirm(Long id, String remark);

    /**
     * 查询设备故障列表
     * @param faultType 故障类型
     * @param deviceId 设备id
     * @param faultId 故障代码
     * @param faultDescription 故障描述
     * @param status 状态
     * @return
     */
    List<DeviceFaultSearch> search(String faultType, Long deviceId, String faultId, String faultDescription, Integer status);

    /**
     * 通过故障类型、起止时间统计设备故障记录
     *
     * @param faultType 故障类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备故障记录统计
     */
    List<Map> selectDeviceFaultByType(String faultType, Date startTime, Date endTime);

    /**
     * 通过故障类型、起止时间统计设备故障记录
     *
     * @param reportVo
     * @return 设备故障记录统计
     */
    List<DeviceFaultTypeVo> selectDeviceFaultByTypeList(ReportVo reportVo);

    /**
     * 通过设备名称统计故障
     * @param deviceName
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> selectFaultByDeviceName(String deviceName,Date startTime, Date endTime);

    /**
     * 通过设备类目统计故障
     * @param deviceTypeId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> selectFaultByDeviceType(Long deviceTypeId,Date startTime, Date endTime);

    /**
     * 通过故障类型统计故障
     * @param faultType
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> selectFaultByFaultType(Integer faultType,Date startTime, Date endTime);
}
