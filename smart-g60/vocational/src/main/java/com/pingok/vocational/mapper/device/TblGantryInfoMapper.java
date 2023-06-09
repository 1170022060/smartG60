package com.pingok.vocational.mapper.device;

import com.pingok.vocational.domain.device.TblGantryInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_GANTRY_INFO 数据层
 *
 * @author xia
 */

public interface TblGantryInfoMapper extends CommonRepository<TblGantryInfo> {

    /**
     * 通过设备类目、设备状态、安装地点、设备ID、使用方、管理方、使用寿命查询门架信息
     * @param deviceCategory 设备类目
     * @param status 设备状态
     * @param fieldBelong 安装地点
     * @param deviceId 设备ID
     * @param userSide 使用方
     * @param managementSide 管理方
     * @param serviceLife 使用寿命
     * @return 设备对应信息
     */
    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "f.PROJECT_NAME as \"itemName\" ," +
            "b.CATEGORY_NAME as \"deviceCategory\" ," +
            "a.DEVICE_ID as \"deviceId\" ," +
            "a.DEVICE_NAME as \"deviceName\" ," +
            "a.DEVICE_BRAND as \"deviceBrand\" ," +
            "a.DEVICE_MODEL as \"deviceModel\" ," +
            "e.DICT_LABEL as \"status\" ," +
            "a.DEVICE_PHOTO as \"devicePhoto\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else c.NICK_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else d.NICK_NAME end as \"updateUserName\" from TBL_GANTRY_INFO a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "left join  SYS_USER c on a.CREATE_USER_ID=c.USER_ID " +
            "left join  SYS_USER d on a.UPDATE_USER_ID=d.USER_ID " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.STATUS) and e.DICT_TYPE='device_status' " +
            "left join TBL_PROJECT_INFO f on a.ITEM_NAME=f.ID " +
            "where 1=1 " +
            "<when test='deviceCategory != null'> " +
            "and (a.DEVICE_CATEGORY= #{deviceCategory} or a.DEVICE_CATEGORY in (SELECT ID FROM TBL_DEVICE_CATEGORY CONNECT BY PRIOR ID = PARENT_CATEGORY START WITH PARENT_CATEGORY = #{deviceCategory})) " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "<when test='fieldBelong != null'> " +
            "and a.FIELD_BELONG= #{fieldBelong} " +
            "</when>"+
            "<when test='deviceId != null'> " +
            "and a.DEVICE_ID like CONCAT(CONCAT('%',#{deviceId}),'%') " +
            "</when>"+
            "<when test='userSide != null'> " +
            "and a.USER_SIDE= #{userSide} " +
            "</when>"+
            "<when test='managementSide != null'> " +
            "and a.MANAGEMENT_SIDE= #{managementSide} " +
            "</when>"+
            "<when test='serviceLife != null'> " +
            "and a.SERVICE_LIFE= #{serviceLife} " +
            "</when>"+
            "<when test='deviceName != null'> " +
            "and a.DEVICE_NAME like CONCAT(CONCAT('%',#{deviceName}),'%') " +
            "</when>"+
            "</script>"})
    public List<Map> selectDeviceInfoGantry(@Param("deviceCategory")Long deviceCategory, @Param("status") Integer status, @Param("fieldBelong") Long fieldBelong, @Param("deviceId")String deviceId, @Param("userSide") Long userSide, @Param("managementSide") Long managementSide, @Param("serviceLife") Integer serviceLife,@Param("deviceName") String deviceName);

    @Select("select ID as \"id\",DEVICE_NAME as \"deviceName\" from TBL_GANTRY_INFO where STATUS = 1 order by DEVICE_ID")
    List<Map> selectGantryName();

    @Select("select * from TBL_GANTRY_INFO where DEVICE_ID= #{deviceId} and rownum = 1")
    TblGantryInfo checkDeviceIdUnique(@Param("deviceId") String deviceId);

    @Select("select * from TBL_GANTRY_INFO where DEVICE_NAME= #{deviceName} and rownum = 1")
    TblGantryInfo checkDeviceNameUnique(@Param("deviceName") String deviceName);
}
