package com.pingok.vocational.mapper.release;

import com.pingok.vocational.domain.release.TblReleaseRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_RELEASE_RECORD 数据层
 *
 * @author xia
 */
public interface TblReleaseRecordMapper extends CommonRepository<TblReleaseRecord> {

    @Select({"<script>" +
            "SELECT " +
            "trr.ID AS id, " +
            "trr.DEVICE_ID AS deviceId, " +
            "tdi.DEVICE_NAME AS deviceName, " +
            "tdi.PILE_NO AS pileNo, " +
            "TO_CHAR(trr.PRESET_TIME,'yyyy-mm-dd hh24:mi:ss') AS presetTime, " +
            "usr.NICK_NAME as presetUserName, " +
            "trr.STATUS AS status, " +
            "trr.PUBLISH_CONTENT as publishContent " +
            "FROM " +
            "TBL_RELEASE_RECORD trr " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.DEVICE_ID = trr.DEVICE_ID " +
            "LEFT JOIN SYS_USER usr ON usr.USER_ID = trr.PRESET_USER_ID " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and trr.PRESET_TIME <![CDATA[>=]]> to_date(#{startTime},'yyyy-mm-dd') " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and trr.PRESET_TIME <![CDATA[>=]]> to_date(#{endTime},'yyyy-mm-dd') " +
            "</when>"+
            "<when test='deviceId != null'> " +
            "and trr.DEVICE_ID like CONCAT(CONCAT('%',#{deviceId}),'%')" +
            "</when>"+
            "<when test='deviceName != null'> " +
            "and tdi.DEVICE_NAME like CONCAT(CONCAT('%',#{deviceName}),'%') " +
            "</when>"+
            "<when test='pileNo != null'> " +
            "and tdi.PILE_NO like CONCAT(CONCAT('%',#{pileNo}),'%') " +
            "</when>"+
            "order by trr.PRESET_TIME desc " +
            "</script>"})
    List<Map> selectReleaseRecord(@Param("deviceId") String deviceId,@Param("deviceName") String deviceName,@Param("pileNo") String pileNo, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
