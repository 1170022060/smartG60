package com.pingok.event.mapper.videoEvent;

import com.pingok.event.domain.videoEvent.TblFaceInfoDetail;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblFaceInfoDetailMapper extends CommonRepository<TblFaceInfoDetail> {

    @Select({"<script>" +
            "SELECT  " +
            "TO_CHAR(UBI_TIME / (1000 * 60 * 60 * 24) + TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') as \"time\", " +
            "SZ_SOURCE_CODE as \"deviceId\", " +
            "b.DEVICE_NAME as \"deviceName\", " +
            "case UI_AGE when 0 then '儿童' when 1 then '青年' when 2 then '中年' when 3 then '老年' end as \"age\", " +
            "case UI_SEX when 0 then '男' when 1 then '女' end as \"sex\", " +
            "case when UI_MASK = 1 then '未戴口罩' end as \"noMask\", " +
            "SZ_IMG as \"image\" " +
            "FROM TBL_FACE_INFO_DETAIL a " +
            "LEFT JOIN TBL_DEVICE_INFO b on b.DEVICE_ID = a.SZ_SOURCE_CODE " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and UBI_TIME / (1000 * 60 * 60 * 24) + TO_DATE('1970-01-01 08:00:00', 'yyyy-MM-dd hh24:mi:ss') &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and UBI_TIME / (1000 * 60 * 60 * 24) + TO_DATE('1970-01-01 08:00:00', 'yyyy-MM-dd hh24:mi:ss') &lt;= #{endTime} " +
            "</when>"+
            "</script>"})
    List<Map> getNoMask(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
