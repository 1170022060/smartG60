package com.pingok.vocational.mapper.field;

import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.domain.field.vo.FieldVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_FIELD_INFO 数据层
 *
 * @author xia
 */
public interface TblFieldInfoMapper extends CommonRepository<TblFieldInfo> {

    @Select({"<script>" +
            "select a.ID as \"id\"," +
            "a.FIELD_NAME as \"fieldName\"," +
            "tfi.FIELD_NAME as \"parentField\"," +
            "e.DICT_LABEL as \"type\"," +
            "f.DICT_LABEL as \"roadBelong\" ," +
            "b.STATION_NAME as \"stationBelong\" ," +
            "a.REMARK as \"remark\" ," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else c.NICK_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else d.NICK_NAME end as \"updateUserName\" from TBL_FIELD_INFO a " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=a.PARENT_ID " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_BELONG)) " +
            "left join  SYS_USER c on a.CREATE_USER_ID=c.USER_ID " +
            "left join  SYS_USER d on a.UPDATE_USER_ID=d.USER_ID " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.TYPE) and e.DICT_TYPE='field_type' " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=a.ROAD_BELONG and f.DICT_TYPE='road_belong' " +
            "where 1=1" +
            "<when test='stationBelong != null'> " +
            "and a.STATION_BELONG= #{stationBelong} " +
            "</when>"+
            "<when test='roadBelong != null'> " +
            "and a.ROAD_BELONG= #{roadBelong} " +
            "</when>"+
            "<when test='fieldName != null'> " +
            "and a.FIELD_NAME like CONCAT(CONCAT('%',#{fieldName}),'%') " +
            "</when>"+
            "<when test='type != null'> " +
            "and a.TYPE= #{type} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "ORDER BY a.UPDATE_TIME DESC"+
            "</script>"})
    List<Map> selectFieldInfo(@Param("stationBelong") String stationBelong, @Param("roadBelong") String roadBelong, @Param("fieldName") String fieldName, @Param("type") Integer type, @Param("status") Integer status);

    @Select("select * from TBL_FIELD_INFO where FIELD_NAME= #{fieldName} and TYPE= #{type} and rownum = 1")
    TblFieldInfo checkFieldNameUnique(@Param("fieldName") String fieldName,@Param("type") Integer type);

    @Select("select TO_NUMBER(DICT_VALUE) as \"type\",DICT_LABEL as \"typeName\" from  SYS_DICT_DATA where DICT_TYPE='field_type' order by DICT_SORT ")
    List<FieldVo> selectFieldTypeName();

    @Select("select ID as \"id\",FIELD_NAME as \"fieldName\" from TBL_FIELD_INFO where TYPE= #{type} and STATUS=1 order by FIELD_NAME")
    List<Map> selectFieldName(@Param("type") Integer type);

    @Select("select ID as \"id\",FIELD_NAME as \"fieldName\" from TBL_FIELD_INFO where ID in (3940,3941) and STATUS=1 order by FIELD_NAME")
    List<Map> selectServiceField();

    @Select("select ID as \"id\",FIELD_NAME as \"fieldName\" from TBL_FIELD_INFO where PARENT_ID=#{id} and STATUS=1 order by FIELD_NAME")
    List<Map> getChildrenField(@Param("id")Long id);
}
