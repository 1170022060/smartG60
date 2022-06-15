package com.pingok.monitor.mapper.device;

import com.pingok.monitor.domain.device.TblDeviceStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 数据层
 *
 * @author qiumin
 */
@Mapper
public interface TblDeviceStatusMapper extends CommonRepository<TblDeviceStatus> {

    @Select("<script>" +
            "SELECT \n" +
            "tdi.ID as \"id\", \n" +
            "tdi.DEVICE_CATEGORY as \"deviceCategory\", \n" +
            "tdc.CATEGORY_NAME as \"categoryName\", \n" +
            "tdi.DEVICE_NAME as \"deviceName\", \n" +
            "tdi.DEVICE_ID as \"deviceId\", \n" +
            "tdi.DEVICE_IP as \"ip\", \n" +
            "tds.TIME as \"time\", \n" +
            "nvl(tds.STATUS,0) as \"status\", \n" +
            "nvl(tds.STATUS_DESC,'未知') as \"statusDesc\", \n" +
            "tds.STATUS_DETAILS as \"statusDetails\"\n" +
            "FROM \n" +
            "TBL_DEVICE_INFO tdi \n" +
            "JOIN TBL_DEVICE_CATEGORY tdc on tdc.ID = tdi.DEVICE_CATEGORY\n" +
            "JOIN  SYS_DEPT d ON d.DEPT_ID = tdi.MANAGEMENT_SIDE\n" +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID \n" +
            "where 1=1 " +
            "<when test='deviceCategory != null'> " +
            "and tdi.DEVICE_CATEGORY = #{deviceCategory} " +
            "</when>" +
            "<when test='deviceName != null'> " +
            "and tdi.DEVICE_NAME like CONCAT(CONCAT('%',#{deviceName}),'%') " +
            "</when>" +
            "<when test='deviceId != null'> " +
            "and tdi.DEVICE_ID like CONCAT(CONCAT('%',#{deviceId}),'%') " +
            "</when>"+
            "and (d.DEPT_ID = (select u.DEPT_ID FROM  SYS_USER u where u.USER_ID=#{userId}) OR d.DEPT_ID IN (SELECT t.DEPT_ID FROM  SYS_DEPT t WHERE instr(','||ancestors||',' , ','||(select u.DEPT_ID FROM  SYS_USER u where u.USER_ID=#{userId})||',')&lt;&gt;0 )) " +
            "order by tds.STATUS desc " +
            "</script>")
    List<Map> list(@Param("deviceCategory") Long deviceCategory,@Param("deviceName") String deviceName,@Param("deviceId") String deviceId,@Param("userId") Long userId);

}
