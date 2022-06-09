package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblTransSummary;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_TRANS_SUMMARY 数据层
 *
 * @author xia
 */
@Mapper
public interface TblTransSummaryMapper extends CommonRepository<TblTransSummary> {

    @Select("select ID as \"id\" from TBL_TRANS_SUMMARY where PASS_ID= #{passId} and rownum = 1")
    Long selectSamePassId(@Param("passId") String passId);

    @Select("select LANE_GB from TBL_LANE_INFO where LANE_HEX =#{laneHex} and rownum=1 ")
    String selectLaneGB(@Param("laneHex") String laneHex);
}
