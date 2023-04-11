package com.pingok.event.mapper.buildManage;

import com.pingok.event.domain.buildManage.TblBuildManage;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Array;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblBuildManaMapper extends CommonRepository<TblBuildManage> {

    @Select({"<script>" +
            "SELECT  " +
            "tbm.ID as \"id\", " +
            "tbm.CONTENT as \"content\", " +
            "tbm.START_PILE_NUM as \"startPileNo\", " +
            "tbm.END_PILE_NUM as \"endPileNo\", " +
            "to_char(tbm.START_TIME, 'yyyy-mm-dd hh24:mi') as \"startTime\", " +
            "to_char(tbm.END_TIME, 'yyyy-mm-dd hh24:mi') as \"endTime\", " +
            "tbm.REMARK as \"remark\", " +
            "to_char(tbm.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\", " +
            "a.NICK_NAME as \"createUser\", " +
            "to_char(tbm.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\", " +
            "b.NICK_NAME as \"updateUser\"  " +
            "FROM TBL_BUILD_MANAGE tbm  " +
            "LEFT JOIN SYS_USER a on a.USER_ID = tbm.CREATE_USER_ID " +
            "LEFT JOIN SYS_USER b on b.USER_ID = tbm.UPDATE_USER_ID " +
            "where 1=1 " +
            "<when test='content != null'>" +
            "AND tbm.CONTENT like '%' || #{content} || '%' " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and tbm.START_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and tbm.END_TIME &lt;= #{endTime} " +
            "</when>"+
            "ORDER BY tbm.START_TIME DESC "+
            "</script>"})
    List<Map> getBuildManaInfo(@Param("content") String content, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select("select  " +
            "ID as \"id\"," +
            "START_TIME as  \"startTime\", " +
            "END_TIME as \"endTime\", " +
            "START_PILE_NUM as \"startPileNo\", " +
            "END_PILE_NUM as \"endPileNo\" " +
            "from TBL_BUILD_MANAGE " +
            "order by CREATE_TIME DESC ")
    List<Map> buildManaList();
}
