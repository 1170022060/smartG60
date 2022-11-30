package com.pingok.vocational.mapper.device;

import com.pingok.vocational.domain.device.TblDeviceInfo;
import com.pingok.vocational.domain.infoboard.VmsInfoByType;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TblDeviceInfo 数据层
 *
 * @author xia
 */
public interface TblDeviceInfoMapper extends CommonRepository<TblDeviceInfo> {
    
    @Select("SELECT " +
            "tdi.ID as \"id\", " +
            "tdi.DEVICE_ID as \"deviceId\", " +
            "tdi.DEVICE_NAME as \"deviceName\", " +
            "tdi.PILE_NO as \"pileNo\", " +
            "tdi.DIRECTION as \"direction\", " +
            "tds.WIDTH as \"width\", " +
            "tds.HIGH as \"high\", " +
            "tds.STATUS as \"status\", " +
            "tds.STATUS_DESC as \"statusDesc\" " +
            "FROM " +
            "TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID " +
            "where 1=1 " +
            "and tdi.DEVICE_CATEGORY= #{deviceCategory} ")
    List<Map> findBydeviceCategory(@Param("deviceCategory")Long deviceCategory);
    /**
     * 通过设备名称、设备类目、设备状态、安装地点、设备ID、使用方、管理方、使用寿命查询门架信息
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
            "g.DICT_LABEL as \"deviceType\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else c.NICK_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else d.NICK_NAME end as \"updateUserName\" from TBL_DEVICE_INFO a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "left join  SYS_USER c on a.CREATE_USER_ID=c.USER_ID " +
            "left join  SYS_USER d on a.UPDATE_USER_ID=d.USER_ID " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.STATUS) and e.DICT_TYPE='device_status' " +
            "left join TBL_PROJECT_INFO f on a.ITEM_NAME=f.ID " +
            "left join  SYS_DICT_DATA g on g.DICT_VALUE=to_char(a.DEVICE_TYPE) and g.DICT_TYPE='device_type' " +
            "where 1=1 " +
            "<when test='deviceCategory != null'> " +
            "and (a.DEVICE_CATEGORY= #{deviceCategory} or a.DEVICE_CATEGORY in (SELECT ID FROM TBL_DEVICE_CATEGORY CONNECT BY PRIOR ID = PARENT_CATEGORY START WITH PARENT_CATEGORY = #{deviceCategory})) " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "<when test='deviceType != null'> " +
            "and a.DEVICE_TYPE= #{deviceType} " +
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
    public List<Map> selectDeviceInfo(@Param("deviceCategory")Long deviceCategory,@Param("status") Integer status,@Param("fieldBelong") Long fieldBelong, @Param("deviceId")String deviceId,@Param("userSide") Long userSide,@Param("managementSide") Long managementSide,@Param("serviceLife") Integer serviceLife,@Param("deviceName") String deviceName,@Param("deviceType") Integer deviceType);

    @Select("select * from TBL_DEVICE_INFO where DEVICE_ID= #{deviceId} and rownum = 1")
    TblDeviceInfo checkDeviceIdUnique(@Param("deviceId") String deviceId);

    @Select("select * from TBL_DEVICE_INFO where DEVICE_NAME= #{deviceName} and rownum = 1")
    TblDeviceInfo checkDeviceNameUnique(@Param("deviceName") String deviceName);

    @Select("select ID as \"id\",DEVICE_NAME || '_' || DEVICE_ID as \"deviceName\" from TBL_DEVICE_INFO order by DEVICE_ID")
    List<Map> selectDeviceName();

    @Select({"<script>" +
            "select " +
            "a.DEVICE_ID as \"deviceId\" ," +
            "a.DEVICE_NAME as \"deviceName\" ," +
            "b.DICT_LABEL as \"manufacturer\" ," +
            "a.DEVICE_MODEL as \"deviceModel\" ," +
            "a.PILE_NO as \"pileNo\" ," +
            "c.PRESET_INFO as \"text\" ," +
            "a.DEVICE_PHOTO as \"devicePhoto\" from TBL_DEVICE_INFO a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=a.MANUFACTURER and b.DICT_TYPE='manufacturer' " +
            "left join TBL_RELEASE_RECORD c on c.DEVICE_ID=a.DEVICE_ID and c.ID in (select ID " +
            "  from (select t.*,                " +
            "               row_number() over(partition by t.DEVICE_ID order by t.PRESET_TIME desc) rn " +
            "          from TBL_RELEASE_RECORD t) c " +
            " where rn = 1) " +
            "where 1 = 1 " +
            "<when test='deviceType != null'> " +
            "and a.DEVICE_TYPE= #{deviceType} " +
            "</when>"+
            "<when test='deviceName != null'> " +
            "and a.DEVICE_NAME like CONCAT(CONCAT('%',#{deviceName}),'%') " +
            "</when>"+
            "<when test='pileNo != null'> " +
            "and a.PILE_NO like CONCAT(CONCAT('%',#{pileNo}),'%') " +
            "</when>"+
            "<when test='manufacturer != null'> " +
            "and a.MANUFACTURER =#{manufacturer} " +
            "</when>"+
            "<when test='deviceModel != null'> " +
            "and a.DEVICE_MODEL like CONCAT(CONCAT('%',#{deviceModel}),'%') " +
            "</when>" +
            "</script>"})
    List<Map> selectInfoBoard(@Param("deviceType")Integer deviceType,@Param("deviceName") String deviceName,@Param("pileNo") String pileNo,@Param("manufacturer")String manufacturer,@Param("deviceModel")String deviceModel);

    @Select({"<script>" +
            "select a.ID as \"id\", a.DEVICE_NAME as \"deviceName\", a.DEVICE_BRAND as \"deviceBrand\", " +
            "a.DEVICE_MODEL as \"deviceModel\", a.TECH_PARA as \"techPara\", a.DEVICE_IP as \"deviceIp\", " +
            "a.PORT as \"port\", a.SLAVE_ID as \"slaveId\", a.PILE_NO as \"pileNo\", a.DIRECTION as \"direction\", " +
            "a.GPS as \"gps\", a.PROTOCOL as \"protocol\", a.WIDTH as \"width\", a.HIGH as \"high\", " +
            "b.STATUS as \"deviceStatus\", b.TIME as \"statusTime\", " +
            "b.STATUS_DESC as \"statusDesc\", b.STATUS_DETAILS as \"statusDetails\", " +
            "c.INFO_TYPE as \"infoType\", c.TYPEFACE as \"typeFace\", c.TYPEFACE_SIZE as \"typeFaceSize\", " +
            "c.COLOR as \"color\", c.PICTURE_TYPE as \"pictureType\", c.RECENT_5 as \"recent5\", " +
            "c.PUBLISH_CONTENT as \"publishContent\", " +
            "a.CAMERA_ID as \"cameraId\" " +
            " from TBL_DEVICE_INFO a " +
            " LEFT JOIN TBL_DEVICE_STATUS b on a.ID = b.DEVICE_ID " +
            " LEFT JOIN TBL_RELEASE_RECORD c on a.DEVICE_ID = c.DEVICE_ID and c.ID in (select ID " +
            "  from (select t.*, row_number() over(partition by t.DEVICE_ID order by t.PRESET_TIME desc) rn " +
                "from TBL_RELEASE_RECORD t) c " +
                " where rn = 1) " +
            "where DEVICE_TYPE = 9 " +
            "<when test='type != null'> " +
            "and DEVICE_MODEL = #{type}" +
            "</when>" +
            "<when test='protocol != null'> " +
            "and PROTOCOL = #{protocol}" +
            "</when>" +
            " order by a.ID" +
            "</script>"
    })
    List<VmsInfoByType> getVmsListByType(@Param("type") String type, @Param("protocol") String protocol);
}
