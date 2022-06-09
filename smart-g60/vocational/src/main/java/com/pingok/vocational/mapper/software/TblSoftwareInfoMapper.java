package com.pingok.vocational.mapper.software;

import com.pingok.vocational.domain.software.TblSoftwareInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_SOFTWARE_INFO 数据层
 *
 * @author xia
 */
public interface TblSoftwareInfoMapper extends CommonRepository<TblSoftwareInfo> {

    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "a.NUM as \"num\" ," +
            "a.NAME as \"name\" ," +
            "a.VERSION as \"version\" ," +
            "a.IP as \"ip\" ," +
            "to_char(a.TIME, 'yyyy-mm-dd hh24:mi:ss')  as \"time\" ," +
            "case a.STATUS when '1' then '正常' when '0' then '异常' else '错误状态' end as \"status\" ," +
            "a.STATUS_CODE as \"statusCode\" ," +
            "case a.UPLOAD_FLAG when '1' then '有上传业务' when '0' then '无上传业务' else '错误状态' end as \"uploadFlag\" ," +
            "case a.DOWNLOAD_FLAG when '1' then '有下载业务' when '0' then '无下载业务' else '错误状态' end as \"downloadFlag\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || b.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || c.USER_NAME end as \"updateUserName\" from TBL_SOFTWARE_INFO a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on a.UPDATE_USER_ID=c.USER_ID " +
            "where 1=1" +
            "<when test='stationId != null and stationId.length()==2'> " +
            "and UPPER(a.NUM) like CONCAT(CONCAT('%',CONCAT('3101',#{stationId})),'%') and LENGTH(a.NUM)=8  " +
            "</when>"+
            "<when test='stationId != null and stationId.length()==4'> " +
            "and UPPER(a.NUM) like CONCAT(CONCAT('%',CONCAT('3101',#{stationId})),'%') and LENGTH(a.NUM)=10  " +
            "</when>"+
            "<when test='name != null'> " +
            "and a.NAME like CONCAT(CONCAT('%',#{name}),'%')  " +
            "</when>"+
            "</script>"})
    List<Map> selectSoftwareInfo(@Param("stationId") String stationId,@Param("name") String name);

    @Select("select * from TBL_SOFTWARE_INFO where NUM= #{num} and rownum = 1")
    TblSoftwareInfo checkSoftNumUnique(@Param("num") String num);
}
