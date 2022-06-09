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
            "select " +
            "a.DEVICE_ID as \"deviceId\" ," +
            "a.DEVICE_NAME as \"deviceName\" ," +
            "a.PILE_NO as \"pileNo\" ," +
            "b.DICT_LABEL as \"infoType\" , " +
            "a.PRESET_INFO as \"presetInfo\" , " +
            "c.DICT_LABEL as \"typeface\" , " +
            "d.DICT_LABEL as \"typefaceSize\" , " +
            "e.DICT_LABEL as \"color\" , " +
            "f.DICT_LABEL as \"pictureType\", " +
            "to_char(a.PRESET_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"presetTime\", " +
            "to_char(a.REVOKE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"revokeTime\", " +
            "case when a.PRESET_USER_ID is null then null else a.PRESET_USER_ID || ':' || g.USER_NAME end as \"presetUserName\" " +
            "from TBL_RELEASE_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.INFO_TYPE and b.DICT_TYPE='release_info_type' " +
            "left join  SYS_DICT_DATA c on b.DICT_VALUE=a.TYPEFACE and c.DICT_TYPE='release_typeface' " +
            "left join  SYS_DICT_DATA d on b.DICT_VALUE=a.TYPEFACE_SIZE and d.DICT_TYPE='release_size' " +
            "left join  SYS_DICT_DATA e on b.DICT_VALUE=a.COLOR and e.DICT_TYPE='release_color' " +
            "left join  SYS_DICT_DATA f on b.DICT_VALUE=a.PICTURE_TYPE and f.DICT_TYPE='release_picture_type' " +
            "left join  SYS_USER g on a.PRESET_USER_ID=g.USER_ID " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.PRESET_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.PRESET_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='infoType != null'> " +
            "and a.INFO_TYPE= #{infoType} " +
            "</when>"+
            "<when test='deviceId != null'> " +
            "and a.DEVICE_ID like CONCAT(CONCAT('%',#{deviceId}),'%')" +
            "</when>"+
            "<when test='deviceName != null'> " +
            "and a.DEVICE_NAME like CONCAT(CONCAT('%',#{deviceName}),'%') " +
            "</when>"+
            "<when test='pileNo != null'> " +
            "and a.PILE_NO like CONCAT(CONCAT('%',#{pileNo}),'%') " +
            "</when>"+
            "order by a.INFO_TYPE, a.PRESET_TIME" +
            "</script>"})
    List<Map> selectReleaseRecord(@Param("infoType") Integer infoType,@Param("deviceId") String deviceId,@Param("deviceName") String deviceName,@Param("pileNo") String pileNo, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
