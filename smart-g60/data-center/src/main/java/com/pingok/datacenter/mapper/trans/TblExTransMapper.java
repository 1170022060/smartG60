package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExTrans;
import com.pingok.datacenter.domain.trans.vo.EnTranFlow;
import com.pingok.datacenter.domain.trans.vo.ExTranFlow;
import com.pingok.datacenter.domain.trans.vo.UpdatePassIdVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * TBL_EX_TRANS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExTransMapper{
    /**
     * 出口流水表入库
     *
     * @param tblExTrans 接收出口流水表信息
     */
    public int insertExTrans(TblExTrans tblExTrans);
    /**
     * 添加passId
     *
     * @return 结果
     */
    public int updatePassId(UpdatePassIdVo updatePassIdVo);

    /**
     * 查询指定的站出口流量之和
     *
     * @return 结果
     */
    public int selectExFlow(ExTranFlow exTranFlow);

    /**
     * MTC单省
     * @param year
     * @param startTime
     * @param endTime
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) as \"count\" FROM " +
            "(SELECT EX_TOLL_STATION_NAME,EX_TIME FROM TBL_SHAR_OTD_RES_SENDER_${year})a " +
            "LEFT JOIN TBL_BASE_STATION_INFO b on a.EX_TOLL_STATION_NAME = b.STATION_NAME " +
            "WHERE a.EX_TIME <![CDATA[ >= ]]> #{startTime} AND a.EX_TIME <![CDATA[ <= ]]> #{endTime}  " +
            "AND CONCAT('3101', LOWER(b.STATION_ID)) in ('31010801','31010804','31010805','31010806','31010807','31010808','31010809','3101080a') " +
            "and PROVINCE_COUNT =1 " +
            "</script>"})
    public int selectExFlowSingle(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime);

    /**
     * MTC跨省
     * @param year
     * @param startTime
     * @param endTime
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) as \"count\" FROM " +
            "(SELECT EX_TOLL_STATION_NAME,EX_TIME FROM TBL_SHAR_OTD_RES_SENDER_${year})a " +
            "LEFT JOIN TBL_BASE_STATION_INFO b on a.EX_TOLL_STATION_NAME = b.STATION_NAME " +
            "WHERE a.EX_TIME <![CDATA[ >= ]]> #{startTime} AND a.EX_TIME <![CDATA[ <= ]]> #{endTime}  " +
            "AND CONCAT('3101', LOWER(b.STATION_ID)) in ('31010801','31010804','31010805','31010806','31010807','31010808','31010809','3101080a') " +
            "and PROVINCE_COUNT >1 " +
            "</script>"})
    public int selectExFlow(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
