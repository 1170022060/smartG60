package com.pingok.vocational.mapper.assist;

import com.pingok.vocational.domain.assist.TblSpatiotemporalInfluence;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_SPATIOTEMPORAL_INFLUENCE 数据层
 *
 * @author xia
 */
public interface TblSpatiotemporalInfluenceMapper extends CommonRepository<TblSpatiotemporalInfluence> {

    @Select({"<script>" +
            "select e.DICT_LABEL as \"eventType\" ," +
            "b.LOCATION_INTERVAL as \"locationInterval\" ," +
            "to_char(b.EVENT_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"eventTime\" ," +
            "a.INCIDENCE as \"incidence\" ," +
            "c.DICT_LABEL as \"influenceMode\" ," +
            "a.DURATION as \"duration\"," +
            "d.DICT_LABEL as \"harmDegree\" ," +
            "to_char(a.RELEASE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"releaseTime\"  from TBL_SPATIOTEMPORAL_INFLUENCE a " +
            "left join TBL_EVENT_RECORD b on b.ID=a.EVENT_ID " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.INFLUENCE_MODE) and c.DICT_TYPE='influence_mode' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=to_char(a.HARM_DEGREE) and d.DICT_TYPE='harm_degree' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=b.EVENT_TYPE and e.DICT_TYPE='event_type' " +
            "where a.EVENT_ID in " +
            "<foreach collection = \"ids\" index=\"index\" item=\"param1\" open=\"(\" separator=\",\" close=\")\">" +
            " #{param1}" +
            "</foreach>" +
            "</script>"})
    List<Map> selectSpatiotemporal(@Param("ids") Long[] ids);

    @Select({"<script>" +
            "select ID  from TBL_EVENT_RECORD " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='eventType != null'> " +
            " and EVENT_TYPE= #{eventType} " +
            "</when>"+
            "</script>"})
    List<Long> selectEvent(@Param("eventType") String eventType,@Param("startTime") Date startTime, @Param("endTime")  Date endTime);

}
