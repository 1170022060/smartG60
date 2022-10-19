package com.pingok.vocational.mapper.software;

import com.pingok.vocational.domain.software.TblSoftwareUpdate;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_SOFTWARE_UPDATE 数据层
 *
 * @author xia
 */
public interface TblSoftwareUpdateMapper extends CommonRepository<TblSoftwareUpdate> {

    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "d.DICT_LABEL as \"softwareType\" ," +
            "a.NAME as \"name\" ," +
            "a.VERSION as \"version\" ," +
            "e.DICT_LABEL as \"status\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else b.NICK_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else c.NICK_NAME end as \"updateUserName\" from TBL_SOFTWARE_UPDATE a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on a.UPDATE_USER_ID=c.USER_ID " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=to_char(a.SOFTWARE_TYPE) and d.DICT_TYPE='software_type' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.STATUS) and e.DICT_TYPE='software_status' " +
            "where 1=1" +
            "<when test='name != null'> " +
            "and a.NAME like CONCAT(CONCAT('%',#{name}),'%')  " +
            "</when>"+
            "<when test='softwareType != null'> " +
            "and a.SOFTWARE_TYPE = #{softwareType} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS = #{status} " +
            "</when>" +
            "order by a.CREATE_TIME "+
            "</script>"})
    List<Map> selectSoftwareUpdate(@Param("name") String name,@Param("softwareType") Integer softwareType,@Param("status") Integer status);

}
