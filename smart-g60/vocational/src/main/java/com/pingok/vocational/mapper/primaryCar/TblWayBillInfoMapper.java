package com.pingok.vocational.mapper.primaryCar;

import com.pingok.vocational.domain.primaryCar.TblWaybillInfo;
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
public interface TblWayBillInfoMapper extends CommonRepository<TblWaybillInfo> {
    @Select({"<script>" +
            "SELECT  " +
            "a.ID AS \"id\", " +
            "a.ACCIDENT_CONCLUSION AS \" accidentConclusion\", " +
            "a.ACCIDENT_CORP_NAME AS \"accidentConsequence\", " +
            "a.ACCIDENT_CONSEQUENCE AS \"accidentCorpName\", " +
            "a.ACCIDENT_DRIVER_NAME AS \"accidentDriverName\", " +
            "a.ACCIDENT_DRIVER_SERIAL_NUMBER AS \"accidentDriverSerialNumber\", " +
            "a.ACCIDENT_ESCORT_NAME AS \"accidentEscortName\", " +
            "a.ACCIDENT_ESCORT_SERIAL_NUMBER AS \"accidentEscortSerialNumber\", " +
            "a.ACCIDENT_GO_THROUGH AS \"accidentGoThrough\", " +
            "a.ACCIDENT_LOCATION AS \"accidentLocation\", " +
            "a.ACCIDENT_TIME AS \"accidentTime\", " +
            "a.ACCIDENT_VEHICLE_LICENSE AS \"accidentVehicleLicense\", " +
            "to_char(a.ANNUAL_REVIEW_DATE,'yyyy-mm-dd hh24:mi:ss') AS \"annualReviewDate\", " +
            "to_char(a.BEGIN_VALID_DATE,'yyyy-mm-dd hh24:mi:ss') AS \"beginValidDate\", " +
            "a.CN_NAME AS \"cnName\", " +
            "a.DRIVER_IDENTIFICATION AS \"driverIdentification\", " +
            "a.DRIVER_NAME AS \"driverName\", " +
            "a.DRIVER_PHONE AS \"driverPhone\", " +
            "a.DRIVER_SERIAL_NUMBER AS \"driverSerialNumber\", " +
            "a.DRIVER_TYPE AS \"driverType\", " +
            "a.END_POINT AS \"endPoint\", " +
            "a.ESCORT_IDENTIFICATION AS \"escortIdentification\", " +
            "a.ESCORT_NAME AS \"escortName\", " +
            "a.ESCORT_PHONE AS \"escortPhone\", " +
            "a.ESCORT_SERIAL_NUMBER AS \"escortSerialNumber\", " +
            "a.ESCORT_TYPE AS \"escortType\", " +
            "a.GOODS_LEVEL AS \"goodsLevel\", " +
            "a.GOODS_NUMBER AS \"goodsNumber\", " +
            "a.GOODS_START_ADDRESS AS \"goodsStartAddress\", " +
            "a.GOODS_TYPE AS \"goodsType\", " +
            "a.GOODS_WEIGHT AS \"goodsWeight\", " +
            "a.LEGAL_PERSON_NAME AS \"legalPersonName\", " +
            "a.LEGAL_PERSON_PHONE AS \"legalPersonPhone\", " +
            "a.LIABILITY_INSURANCE_DATE AS \"liabilityInsuranceDate\", " +
            "a.LICENSE_NUMBER AS \"licenseNumber\", " +
            "a.OPERATE_ADDRESS AS \"operateAddress\", " +
            "a.RATING_DATE AS \"ratingDate\", " +
            "a.SAFE_PERSON_NAME AS \"safePersonName\", " +
            "a.SAFE_PERSON_PHONE AS \"safePersonPhone\", " +
            "a.TECHNICAL_GRADE AS \"technicalGrade\", " +
            "to_char(a.TECHNICAL_GRADE_DATE,'yyyy-mm-dd hh24:mi:ss') AS \" technicalGradeDate\", " +
            "a.VEHICLE_GROSS_MASS AS \"vehicleGrossMass\", " +
            "a.VEHICLE_LICENSE AS \"vehicleLicense\", " +
            "a.VEHICLE_LICENSE_NUMBER AS \"vehicleLicenseNumber\", " +
            "a.VEHICLE_MANAGER_AGENCE AS \"vehicleManagerAgence\", " +
            "a.VEHICLE_SCOPE AS \"vehicleScope\", " +
            "a.VEHICLE_STATUS AS \"vehicleStatus\", " +
            "b.DICT_LABEL AS \"vehicleType\" " +
            "FROM TBL_WAYBILL_INFO a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=a.VEHICLE_TYPE and b.DICT_TYPE='veh_class' " +
            "where 1=1 " +
            "<when test='vehPlate != null'>" +
            "and a.VEHICLE_LICENSE like '%' || #{vehPlate} || '%' " +
            "</when> " +
            "</script>"})
    List<Map> getWayBillInfo(@Param("vehPlate") String vehPlate);
    
}
