package com.pingok.datacenter.mapper.simulatedSorting;


import com.pingok.datacenter.domain.simulatedSorting.TblSimulatedSorting;
import com.pingok.datacenter.domain.simulatedSorting.vo.SimulatedSortingVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_SIMULATED_SORTING 数据层
 *
 * @author xia
 */
public interface TblSimulatedSortingMapper extends CommonRepository<TblSimulatedSorting> {

    @Select({"SELECT " +
            "TO_CHAR( tgt.TRANS_TIME, 'yyyy-mm-dd' ) AS \"sortingDate\", " +
            "TO_CHAR( tgt.TRANS_TIME, 'hh24' ) AS \"sortingHour\", " +
            "tgt.MEDIA_TYPE AS \"mediaType\", " +
            "DECODE( " +
            "tnt.PASS_ID, " +
            "NULL, " +
            "DECODE( txt.PASS_ID, NULL, 4, 3 ), " +
            "DECODE( txt.PASS_ID, NULL, 2, 1 )  " +
            ") AS \"routeType\", " +
            "nvl( tgt.CPU_CARD_TYPE, 0 ) AS \"cardType\", " +
            "DECODE( " +
            "DECODE( " +
            "tgt.MEDIA_TYPE, " +
            "1, " +
            "SUBSTR( tgt.CPU_ISSUE_ID, 0, 8 ), " +
            "SUBSTR( tgt.OBU_ISSUE_ID, 0, 8 )  " +
            "), " +
            "'C9CFBAA3', " +
            "1, " +
            "0  " +
            ") AS \"issueId\", " +
            "COUNT( 0 ) AS \"flow\", " +
            "sum( tgt.FEE ) AS \"fee\"  " +
            "FROM " +
            "TBL_SHAR_GTD_RES_SENDER_${year} tgt " +
            "LEFT JOIN TBL_SHAR_ENPD_RES_SENDER_${year} tnt ON tnt.PASS_ID = tgt.PASS_ID " +
            "LEFT JOIN ( " +
            "SELECT " +
            "PASS_ID  " +
            "FROM " +
            "TBL_SHAR_ETCTD_RES_SENDER_${year}  " +
            "WHERE " +
            "EN_TIME >= to_date( #{startTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "AND EN_TIME <= to_date( #{endTime}, 'yyyy-mm-dd hh24:mi:ss' ) UNION " +
            "SELECT " +
            "PASS_ID  " +
            "FROM " +
            "TBL_SHAR_OTD_RES_SENDER_${year}  " +
            "WHERE " +
            "EN_TIME >= to_date( #{startTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "AND EN_TIME <= to_date( #{endTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            ") txt ON txt.PASS_ID = tgt.PASS_ID  " +
            "WHERE " +
            "tgt.TRADE_RESULT = 0  " +
            "AND tgt.TRANS_TIME >= to_date( #{startTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "AND tgt.TRANS_TIME <= to_date( #{endTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "GROUP BY " +
            "TO_CHAR( tgt.TRANS_TIME, 'yyyy-mm-dd' ), " +
            "TO_CHAR( tgt.TRANS_TIME, 'hh24' ), " +
            "tgt.MEDIA_TYPE, " +
            "DECODE( " +
            "tnt.PASS_ID, " +
            "NULL, " +
            "DECODE( txt.PASS_ID, NULL, 4, 3 ), " +
            "DECODE( txt.PASS_ID, NULL, 2, 1 )  " +
            "), " +
            "nvl( tgt.CPU_CARD_TYPE, 0 ), " +
            "DECODE( " +
            "DECODE( " +
            "tgt.MEDIA_TYPE, " +
            "1, " +
            "SUBSTR( tgt.CPU_ISSUE_ID, 0, 8 ), " +
            "SUBSTR( tgt.OBU_ISSUE_ID, 0, 8 )  " +
            "), " +
            "'C9CFBAA3', " +
            "1, " +
            "0  " +
            ")  " +
            "ORDER BY " +
            "TO_CHAR( tgt.TRANS_TIME, 'yyyy-mm-dd' ), " +
            "TO_CHAR( tgt.TRANS_TIME, 'hh24' ) "})
    List<TblSimulatedSorting> simulatedSortingNew(@Param("year") String year, @Param("startTime") String startTime, @Param("endTime") String endTime);


    @Select({"SELECT  " +
            "sum(FLOW) as \"flow\", " +
            "sum(FEE)/100 as \"fee\" " +
            "FROM " +
            "TBL_SIMULATED_SORTING " +
            "where SORTING_DATE = #{date}"})
    Map statistics(@Param("date") String date);

    @Select({"SELECT " +
            "TO_CHAR( tgt.TRANS_TIME, 'yyyy-mm-dd' ) AS \"sortingDate\", " +
            "TO_CHAR( tgt.TRANS_TIME, 'hh24' ) AS \"sortingHour\", " +
            "tgt.MEDIA_TYPE AS \"mediaType\", " +
            "DECODE( " +
            "tnt.PASS_ID, " +
            "NULL, " +
            "DECODE( txt.PASS_ID, NULL, 4, 3 ), " +
            "DECODE( txt.PASS_ID, NULL, 2, 1 )  " +
            ") AS \"routeType\", " +
            "nvl( tgt.CPU_CARD_TYPE, 0 ) AS \"cardType\", " +
            "DECODE( " +
            "DECODE( " +
            "tgt.MEDIA_TYPE, " +
            "1, " +
            "SUBSTR( tgt.CPU_ISSUE_ID, 0, 8 ), " +
            "SUBSTR( tgt.OBU_ISSUE_ID, 0, 8 )  " +
            "), " +
            "'C9CFBAA3', " +
            "1, " +
            "0  " +
            ") AS \"issueId\", " +
            "COUNT( 0 ) AS \"flow\", " +
            "sum( tgt.FEE ) AS \"fee\"  " +
            "FROM " +
            "TBL_GANTRY_TRANSACTION_${year} tgt " +
            "LEFT JOIN TBL_EN_TRANS_${year} tnt ON tnt.PASS_ID = tgt.PASS_ID " +
            "LEFT JOIN TBL_EX_TRANS_${year} txt ON txt.PASS_ID = tgt.PASS_ID  " +
            "WHERE " +
            "tgt.TRADE_RESULT = 0  " +
            "AND tgt.TRANS_TIME >= to_date(#{startTime},'yyyy-mm-dd hh24:mi:ss')   " +
            "AND tgt.TRANS_TIME <= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss')   " +
            "GROUP BY " +
            "TO_CHAR( tgt.TRANS_TIME, 'yyyy-mm-dd' ), " +
            "TO_CHAR( tgt.TRANS_TIME, 'hh24' ), " +
            "tgt.MEDIA_TYPE, " +
            "DECODE( " +
            "tnt.PASS_ID, " +
            "NULL, " +
            "DECODE( txt.PASS_ID, NULL, 4, 3 ), " +
            "DECODE( txt.PASS_ID, NULL, 2, 1 )  " +
            "), " +
            "nvl( tgt.CPU_CARD_TYPE, 0 ), " +
            "DECODE( " +
            "DECODE( " +
            "tgt.MEDIA_TYPE, " +
            "1, " +
            "SUBSTR( tgt.CPU_ISSUE_ID, 0, 8 ), " +
            "SUBSTR( tgt.OBU_ISSUE_ID, 0, 8 )  " +
            "), " +
            "'C9CFBAA3', " +
            "1, " +
            "0  " +
            ")  " +
            "ORDER BY " +
            "TO_CHAR( tgt.TRANS_TIME, 'yyyy-mm-dd' ), " +
            "TO_CHAR( tgt.TRANS_TIME, 'hh24' ) "})
    List<TblSimulatedSorting> simulatedSorting(@Param("year") String year, @Param("startTime") String startTime, @Param("endTime") String endTime);


    @Select({"<script>" +
            "SELECT " +
            "SORTING_DATE as \"sortingDate\", " +
            "TRUNC(SUM( DECODE( ROUTE_TYPE, 1, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) )/100, 2) AS \"inAndOutCpcFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 1, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"inAndOutEtcSvFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 1, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"inAndOutEtcCcFee\", " +
            "TRUNC(SUM( DECODE( ROUTE_TYPE, 2, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) )/100, 2) AS \"inCpcFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 2, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"inEtcSvFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 2, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"inEtcCcFee\", " +
            "TRUNC(SUM( DECODE( ROUTE_TYPE, 3, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) )/100, 2) AS \"outCpcFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 3, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"outEtcSvFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 3, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"outEtcCcFee\", " +
            "TRUNC(SUM( DECODE( ROUTE_TYPE, 4, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) )/100, 2) AS \"passCpcFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 4, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"passEtcSvFee\", " +
            "TRUNC(SUM( " +
            "DECODE( ROUTE_TYPE, 4, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ")/100, 2) AS \"passEtcCcFee\", " +
            "TRUNC((SUM( DECODE( ROUTE_TYPE, 1, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) ) + SUM( " +
            "DECODE( ROUTE_TYPE, 1, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( " +
            "DECODE( ROUTE_TYPE, 1, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( DECODE( ROUTE_TYPE, 2, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) ) + SUM( " +
            "DECODE( ROUTE_TYPE, 2, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( " +
            "DECODE( ROUTE_TYPE, 2, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( DECODE( ROUTE_TYPE, 3, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) ) + SUM( " +
            "DECODE( ROUTE_TYPE, 3, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( " +
            "DECODE( ROUTE_TYPE, 3, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( DECODE( ROUTE_TYPE, 4, DECODE( MEDIA_TYPE, 2, FEE, 0 ), 0 ) ) + SUM( " +
            "DECODE( ROUTE_TYPE, 4, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 1, FEE, 0 ), 0 ), 0 )  " +
            ") + SUM( " +
            "DECODE( ROUTE_TYPE, 4, DECODE( MEDIA_TYPE, 1, DECODE( CARD_TYPE, 2, FEE, 0 ), 0 ), 0 )  " +
            "))/100, 2) AS \"allFee\"  " +
            "FROM " +
            "TBL_SIMULATED_SORTING  " +
            "where 1=1" +
            "<when test='startTime != null'> " +
            "and to_date(SORTING_DATE,'yyyy-mm-dd') <![CDATA[>=]]> to_date(#{startTime},'yyyy-mm-dd') " +
            "</when>" +
            "<when test='endTime != null'> " +
            "and to_date(SORTING_DATE,'yyyy-mm-dd') <![CDATA[<=]]> to_date(#{endTime},'yyyy-mm-dd') " +
            "</when>" +
            "GROUP BY " +
            "SORTING_DATE " +
            "</script>"})
    List<SimulatedSortingVo> dayStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
