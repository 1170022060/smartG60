package com.pingok.vocational.mapper.device;

import com.pingok.vocational.domain.device.TblDeviceRepair;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_DEVICE_REPAIR 数据层
 *
 * @author xia
 */
public interface TblDeviceRepairMapper extends CommonRepository<TblDeviceRepair> {

    @Select({"<script>" +
            "select to_char(a.REPAIR_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"repairTime\" ," +
            "c.DICT_LABEL as \"repairType\" ," +
            "a.REASON as \"reason\" ," +
            "a.CONTENT as \"content\" ," +
            "case when a.RESPONSIBLE_USER_ID is null then null else a.RESPONSIBLE_USER_ID || ':' || b.USER_NAME end as \"responsibleUserNum\" from TBL_DEVICE_REPAIR a " +
            "left join  SYS_USER b on a.RESPONSIBLE_USER_ID=b.USER_ID " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.REPAIR_TYPE and c.DICT_TYPE='repair_type' " +
            "where 1=1 " +
            "<when test='deviceId != null'> " +
            "and a.DEVICE_ID= #{deviceId} " +
            "</when>" +
            "order by a.REPAIR_TIME DESC "+
            "</script>"})
    public List<Map> selectDeviceRepair(@Param("deviceId")Long deviceId);

}
