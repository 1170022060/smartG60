package com.pingok.vocational.mapper.primaryCar;

import com.pingok.vocational.domain.primaryCar.TblPrimaryVehInfo;
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
public interface TblPrimaryVehInfoMapper extends CommonRepository<TblPrimaryVehInfo> {

    @Select({"<script>" +
            "SELECT  " +
            "a.ID AS \"id\", " +
            "a.TRADE_ID AS \"tradeId\", " +
            "a.LICENSE_NUMBER AS \"licenseNumber\", " +
            "a.SCOPE_ID AS \"scopeId\", " +
            "a.VEHICLE_LICENSE AS \"vehicleLicense\", " +
            "c.DICT_LABEL AS \"licenseSubject\", " +
            "a.STATUS AS \"status\", " +
            "a.FRAME_NUMBER AS \"frameNumber\", " +
            "to_char(a.MANUFACTURE_DATE,'yyyy-mm-dd') AS \"manufactureDate\", " +
            "to_char(a.OPERATE_DATE,'yyyy-mm-dd') AS \"operateDate\", " +
            "to_char(a.DRIVING_LICENSE_FIRST_DATE,'yyyy-mm-dd hh24:mi:ss') AS \"drivingLicenseFirstDate\", " +
            "b.DICT_LABEL AS \"vehicleClass\", " +
            "a.COLOR AS \"color\", " +
            "a.FUEL AS \"fuel\", " +
            "a.BRAND AS \"brand\", " +
            "a.MODEL AS \"model\", " +
            "a.BODY_LENGTH AS \"bodyLength\", " +
            "a.BODY_WIDTH AS \"bodyWidth\", " +
            "a.BODY_HEIGHT AS \"bodyHeight\", " +
            "a.APPROVED_QUALITY AS \"approvedQuality\", " +
            "a.SEAT AS \"seat\", " +
            "a.WHEEL_BASE AS \"wheelBase\", " +
            "a.SUSPENSION AS \"suspension\", " +
            "a.EMPTY_WEIGHT AS \"emptyWeight\", " +
            "a.GROSS_MASS AS \"grossMass\", " +
            "a.AXLE AS \"axle\", " +
            "a.ENGINE_NUMBER AS \"engineNumber\", " +
            "a.ENGINE_TYPE AS \"engineType\", " +
            "a.POWER AS \"power\", " +
            "a.DISPLACEMENT AS \"displacement\", " +
            "a.APPROVAL_AUTHORITY AS \"approvalAuthority\", " +
            "a.MANAGER_AGENCE AS \"managerAgence\", " +
            "a.MANAGER_DEPARTMANET AS \"managerDepartment\", " +
            "a.GPS AS \"gps\", " +
            "a.GPS_NUM AS \"gpsNum\", " +
            "a.GPS_OPERATOR AS \"gpsOperator\", " +
            "a.DVR AS \"dvr\", " +
            "a.TACHOGRAPH_NUM AS \"tachographNum\", " +
            "a.TACHOGRAPH_OPERATOR AS \"tachographOperator\", " +
            "to_char(a.BEGIN_VALID_DATE,'yyyy-mm-dd hh24:mi:ss') AS \"beginValidDate\", " +
            "a.END_VALID_DATE AS \"endValidDate\", " +
            "a.NMUG_YEAR AS \"nmugYear\", " +
            "a.NUMG_STATUS AS \"nmugStatus\", " +
            "to_char(a.INITIAL_DATE,'yyyy-mm-dd hh24:mi:ss') AS \"initialDate\", " +
            "a.CANCEL_LATION_DATE AS \"cancelLationDate\", " +
            "a.CANCEL_LATION_REASON AS \"cancelLationReason\", " +
            "a.IS_REMOVE AS \"isRemove\", " +
            "a.VIN AS \"vin\", " +
            "a.DATE_FOR_ADD AS \"dateforAdd\", " +
            "a.LICENSE_VALID AS \"licenseValid\", " +
            "a.LICENSE_COLOR AS \"licenseColor\", " +
            "a.FUEL_TYPE AS \"fuelType\", " +
            "a.VEHICLE_TYPE AS \"vehicleType\", " +
            "a.VEHICLE_STATUS AS \"vehicleStatus\", " +
            "a.VEHICLE_SCOPE AS \"vehicleScope\", " +
            "a.TRADE AS \"trade\", " +
            "a.ENTER_PRISE_ID AS \"enterpriseId\", " +
            "a.DISTRICT AS \"district\", " +
            "a.MANAGEMENT_UNIT AS \"managementUnit\" " +
            "FROM TBL_PRIMARY_VEH_INFO a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=a.VEHICLE_CLASS and b.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=a.LICENSE_SUBJECT and c.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='vehPlate != null'>" +
            "and a.VEHICLE_LICENSE like '%' || #{vehPlate} || '%' " +
            "</when> " +
            "</script>"})
    List<Map> getPrimaryVehInfo(@Param("vehPlate") String vehPlate);
}
