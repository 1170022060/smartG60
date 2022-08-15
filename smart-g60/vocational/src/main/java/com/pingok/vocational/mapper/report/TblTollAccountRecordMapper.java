package com.pingok.vocational.mapper.report;


import com.pingok.vocational.domain.report.vo.TollAccountRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_TOLL_ACCOUNT_RECORD 数据层
 *
 * @author xia
 */
public interface TblTollAccountRecordMapper {

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "c.DICT_LABEL as \"vehStatus\"," +
            "a.TRANS_NO as \"transNo\" ," +
            "a.CALCULATE_FEE as \"calculateFee\" ," +
            "a.TOLL_FEE as \"tollFee\" ," +
            "a.AMOUNT as \"amount\" from TBL_TOLL_ACCOUNT_RECORD a " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_STATUS and c.DICT_TYPE='veh_status' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<Map> selectAccountRecord(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "c.DICT_LABEL as \"vehStatus\"," +
            "a.TRANS_NO as \"transNo\" ," +
            "a.CALCULATE_FEE as \"calculateFee\" ," +
            "a.TOLL_FEE as \"tollFee\" ," +
            "a.AMOUNT as \"amount\" from TBL_TOLL_ACCOUNT_RECORD a " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_STATUS and c.DICT_TYPE='veh_status' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<TollAccountRecordVo> selectAccountRecorList(ReportVo reportVo);
}
