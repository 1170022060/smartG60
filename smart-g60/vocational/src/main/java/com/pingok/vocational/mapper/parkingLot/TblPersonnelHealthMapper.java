package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblPersonnelHealth;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_PERSONNEL_HEALTH 数据层
 *
 * @author xia
 */
public interface TblPersonnelHealthMapper extends CommonRepository<TblPersonnelHealth> {

    @Select({"<script>" +
            "select tph.ID as \"id\", to_char(tph.TRANS_DATE, 'yyyy-mm-dd') as \"transDate\"," +
            "tfi.FIELD_NAME as \"serviceName\", " +
            "t.FIELD_NAME as \"fieldName\", " +
            "NVL(tph.NORMAL_NUM,0) as \"normalNum\"," +
            "NVL(tph.ABNORMAL_NUM,0) as \"abnormalNum\"," +
            "NVL(round(100*tph.NORMAL_NUM/SUM(tph.NORMAL_NUM+tph.ABNORMAL_NUM)),0) as normalRateA," +
            "NVL(100-round(100*tph.NORMAL_NUM/SUM(tph.NORMAL_NUM+tph.ABNORMAL_NUM)),0) as abnormalRateA,"+
            "to_char(tph.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(tph.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when tph.CREATE_USER_ID is null then null else b.NICK_NAME end as \"createUserName\"," +
            "case when tph.UPDATE_USER_ID is null then null else c.NICK_NAME end as \"updateUserName\"," +
            "from TBL_PERSONNEL_HEALTH tph " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tph.FIELD_ID " +
            "left join TBL_FIELD_INFO t on t.ID=tph.SERVICE_ID " +
            "left join  SYS_USER b on tph.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on tph.UPDATE_USER_ID=c.USER_ID " +
            "where 1=1 " +
            "<when test='name != null'> " +
            "and tph.NAME like CONCAT(CONCAT('%',#{name}),'%') " +
            "</when>"+
            "<when test='fieldId != null'> " +
            "and tph.FIELD_ID = #{fieldId}  " +
            "</when>"+
            "<when test='date != null'> " +
            "and tph.TRANS_DATE= #{date} " +
            "</when>"+
            "</script>"})
    List<Map> selectPersonnelHealth(@Param("name") String name, @Param("fieldId") Long fieldId, @Param("date") Date date);

    @Select({"<script>" +
            "select to_char(tph.TRANS_DATE, 'yyyy-mm-dd') as \"date\", " +
            "tfi.FIELD_NAME as \"fieldName\", " +
            "'核酸' as \"type\", " +
            "sum(case when tph.NUCLEIC_ACID &lt;=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.nucleicAcid') then 1 else 0 end) as \"normal\", " +
            "round(100*sum(case when tph.NUCLEIC_ACID &lt;=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.nucleicAcid') then 1 else 0 end)/count(1)) || '%' as \"normalRate\", " +
            "(100-round(100*sum(case when tph.NUCLEIC_ACID &lt;=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.nucleicAcid') then 1 else 0 end)/count(1))) || '%' as \"abnormalRate\" " +
            "from TBL_PERSONNEL_HEALTH tph  " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tph.FIELD_ID  " +
            "where 1=1  " +
            "<when test='type == 2'> " +
            "and 1=0 " +
            "</when>"+
            "<when test='fieldId != null'> " +
            "and tph.FIELD_ID = #{fieldId}  " +
            "</when>"+
            "<when test='date != null'> " +
            "and tph.TRANS_DATE= #{date} " +
            "</when>" +
            "group by TRANS_DATE,tfi.FIELD_NAME " +
            "union all " +
            "select to_char(tph.TRANS_DATE, 'yyyy-mm-dd') as \"date\", " +
            "tfi.FIELD_NAME as \"fieldName\",  " +
            "'体温' as \"type\", " +
            "sum(case when tph.TEMPERATURE &lt;=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.temperature') then 1 else 0 end) as \"normal\"," +
            "round(100*sum(case when tph.TEMPERATURE &lt;=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.temperature') then 1 else 0 end)/count(1)) || '%' \"normalRate\", " +
            "(100-round(100*sum(case when tph.TEMPERATURE &lt;=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.temperature') then 1 else 0 end)/count(1))) || '%' \"abnormalRate\" " +
            "from TBL_PERSONNEL_HEALTH tph " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tph.FIELD_ID " +
            "where 1=1 " +
            "<when test='type == 1'> " +
            "and 1=0 " +
            "</when>"+
            "<when test='fieldId != null'> " +
            "and tph.FIELD_ID = #{fieldId}  " +
            "</when>"+
            "<when test='date != null'> " +
            "and tph.TRANS_DATE= #{date} " +
            "</when>" +
            "group by TRANS_DATE,tfi.FIELD_NAME " +
            "</script>"})
    List<Map> selectHealthStatistics(@Param("type") Integer type, @Param("fieldId") Long fieldId, @Param("date") Date date);

    @Select("SELECT " +
            "sum(NORMAL_NUM+ABNORMAL_NUM) as \"count\"," +
            "NVL(NORMAL_NUM,0)as \"normalNum\",NVL(ABNORMAL_NUM,0)as \"abnormalNum\"," +
            "NVL(round(100*NORMAL_NUM/SUM(NORMAL_NUM+ABNORMAL_NUM)),0) as \"normalRateA\"," +
            "NVL(100-round(100*NORMAL_NUM/SUM(NORMAL_NUM+ABNORMAL_NUM)),0) as \"abnormalRateA\" " +
            "FROM TBL_PERSONNEL_HEALTH " +
            "where TRANS_DATE= #{date}" +
            "GROUP BY NORMAL_NUM,ABNORMAL_NUM ")
    List<Map> selectHealthMonitor(@Param("date") Date date);

}
