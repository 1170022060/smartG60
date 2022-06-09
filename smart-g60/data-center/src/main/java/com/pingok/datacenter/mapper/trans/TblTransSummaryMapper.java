package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblTransSummary;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * TBL_TRANS_SUMMARY 数据层
 *
 * @author xia
 */
public interface TblTransSummaryMapper extends CommonRepository<TblTransSummary> {

    @Select("select ID as \"id\" from TBL_TRANS_SUMMARY where PASS_ID= #{passId} and rownum = 1")
    Long selectSamePassId(@Param("passId") String passId);
}
