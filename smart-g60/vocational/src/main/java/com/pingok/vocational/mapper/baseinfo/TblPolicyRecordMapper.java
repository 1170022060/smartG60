package com.pingok.vocational.mapper.baseinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pingok.vocational.domain.baseinfo.TblPolicyRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_POLICY_RECORD 数据层
 *
 * @author xia
 */
public interface TblPolicyRecordMapper extends CommonRepository<TblPolicyRecord> {

    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "a.TITLE as \"title\" ," +
            "a.CONTENT as \"content\" ," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else b.NICK_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else c.NICK_NAME end as \"updateUserName\" from TBL_POLICY_RECORD a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on a.UPDATE_USER_ID=c.USER_ID " +
            "where 1=1 " +
            "<when test='title != null'> " +
            "and a.TITLE like CONCAT(CONCAT('%',#{title}),'%')  " +
            "</when>" +
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            " order by a.CREATE_TIME "+
            "</script>"})
    List<Map> selectPolicyRecord(@Param("title") String title, @Param("status") Integer status);

    @Select("select * from TBL_POLICY_RECORD where TITLE= #{title} and rownum = 1")
    TblPolicyRecord checkTitleUnique(@Param("title") String title);
}
