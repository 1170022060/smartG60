package com.pingok.vocational.mapper.business;

import com.pingok.vocational.domain.business.TblOptInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblOptInfoMapper extends CommonRepository<TblOptInfo> {

    @Select({"<script>" +
            "select a.ID as \"id\"," +
            "a.OPT_ID as \"optId\"," +
            "a.OPT_NAME as \"optName\"," +
            "e.DICT_LABEL as \"belongCenter\" ," +
            "b.STATION_NAME as \"belongStation\" ," +
            "a.REMARK as \"remark\" ," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || c.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || d.USER_NAME end as \"updateUserName\" from TBL_OPT_INFO a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.BELONG_STATION)) " +
            "left join  SYS_USER c on a.CREATE_USER_ID=c.USER_ID " +
            "left join  SYS_USER d on a.UPDATE_USER_ID=d.USER_ID " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.BELONG_CENTER and e.DICT_TYPE='road_belong' " +
            "where 1=1" +
            "<when test='belongStation != null'> " +
            "and a.BELONG_STATION= #{belongStation} " +
            "</when>"+
            "<when test='belongCenter != null'> " +
            "and a.BELONG_CENTER= #{belongCenter} " +
            "</when>"+
            "<when test='optName != null'> " +
            "and a.OPT_NAME like CONCAT(CONCAT('%',#{optName}),'%') " +
            "</when>"+
            "<when test='optId != null'> " +
            "and a.OPT_ID= #{optId} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "</script>"})
    List<Map> selectOptInfo(@Param("belongStation") String belongStation,@Param("belongCenter") String belongCenter,  @Param("optName") String optName, @Param("optId") Integer optId,@Param("status") Integer status);

    @Select("select * from TBL_OPT_INFO where OPT_ID= #{optId} and rownum = 1")
    TblOptInfo checkOptIdUnique(@Param("optId") Integer optId);

}
