package com.pingok.datacenter.mapper.simulatedSorting;


import com.pingok.datacenter.domain.simulatedSorting.TblSimulatedSorting;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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
    List<TblSimulatedSorting> simulatedSorting(@Param("year") String year, @Param("startTime") String startTime, @Param("endTime")  String endTime);
}
