package com.pingok.vocational.mapper.nameList;

import com.pingok.vocational.domain.nameList.TblRescueListRecordLog;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblRescueListRecordNMapper extends CommonRepository<TblRescueListRecordLog> {
    @Select("select  " +
            "VERSION_ID as \"versionId\", " +
            "case when DATA_TYPE=1 then '预约类' when DATA_TYPE=2 then '名单类' end as \"dataType\"," +
            "case when APPOINT_TYPE=010000 then '疫苗车辆' when APPOINT_TYPE=020000 then '应急车辆' end as \"appointType\", " +
            "DISCOUNT_INFO as \"discountInfo\"," +
            "case when HANDLE_TYPE=1 then '收费站' when HANDLE_TYPE=2 then '出口省中心' when HANDLE_TYPE=3 then '通行省中心' end as \"handleType\"," +
            "case when CHECK_TYPE=1 then '无需核验' when CHECK_TYPE=2 then '收费站' when CHECK_TYPE=3 then '出口省中心' " +
            "when CHECK_TYPE=4 then '通行省中心' end as \"checkType\"," +
            "to_char(APPOINT_TIME,'yyyy-mm-dd hh24:mi:ss') as \"appointTime\"," +
            "VEHICLE_SIGN as \"vehicleSign\"," +
            "LANE_HANDLE as \"laneHandle\"," +
            "case when VEHICLE_IDENTIFY_TYPE=1 then '通过ETC卡编号确定车辆' when VEHICLE_IDENTIFY_TYPE=2 then '通过车牌编号确定车辆' " +
            "when VEHICLE_IDENTIFY_TYPE=3 then '通过ETC卡编号或车牌号确定车辆' when VEHICLE_IDENTIFY_TYPE=4 then '通过ETC卡编号且车牌号确定车辆' " +
            "end as \"vehicleIdentifyType\"," +
            "CARD_ID as \"cardId\"," +
            "VEHICLE_PLATE_ID as \"vehiclePlateId\"," +
            "VEHICLE_PLATE_COLOR as \"vehiclePlateColor\"," +
            "case when OPERATION=1 then '新增' when OPERATION=2 then '变更' when OPERATION=3 then '删除' end as \"operation\"," +
            "case when DISCOUNT_TYPE=1 then '按优惠折扣' when DISCOUNT_TYPE=2 then '按优惠金额' " +
            "when DISCOUNT_TYPE=3 then '按优惠定额' when DISCOUNT_TYPE=4 then '其他' end as \"discountType\"," +
            "DISCOUNT as \"discount\"," +
            "DISCOUNT_QUOTA as \"discountQuota\"," +
            "to_char(START_TIME,'yyyy-mm-dd hh24:mi:ss') as \"startTime\"," +
            "to_char(END_TIME,'yyyy-mm-dd hh24:mi:ss') as \"endTime\"," +
            "PROVINCE_IDS as \"provinceIds\"," +
            "EN_STATION as \"enStation\"," +
            "EX_STATION as \"exStation\"  " +
            "from TBL_RESCUE_LIST_RECORD_LOG " +
            "where VERSION_ID = #{id}  ")
    List<Map> findById(@Param("id") Long id);
}
