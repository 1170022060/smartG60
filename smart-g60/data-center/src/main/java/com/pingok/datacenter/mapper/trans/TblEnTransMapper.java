package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnTrans;
import com.pingok.datacenter.domain.trans.vo.EnTranFlow;
import com.pingok.datacenter.domain.trans.vo.UpdatePassIdVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_EN_TRANS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblEnTransMapper {

    /**
     * 入口流水表入库
     *
     * @param tblEnTrans 入口流水表
     * @return 结果
     */
    public int insertEnTrans(TblEnTrans tblEnTrans);

    /**
     * 添加passId
     *
     * @return 结果
     */
    public int updatePassId(UpdatePassIdVo updatePassIdVo);

    /**
     * 查询指定的站入口流量之和
     *
     * @return 结果
     */
    public int selectEnFlow(EnTranFlow enTranFlow);

    @Select({"<script>" +
            "SELECT COUNT(*) FROM TBL_SHAR_ENPD_RES_SENDER_${year} a  " +
            "WHERE a.EN_TIME <![CDATA[ >= ]]> #{startTime} AND a.EN_TIME <![CDATA[ <= ]]> #{endTime}  " +
            "AND SUBSTR(EN_TOLL_LANE_HEX,0, 8) in ('31010801','31010804','31010805','31010806','31010807','31010808','31010809','3101080a') " +
            "</script>"})
    public int selectEnFlow(@Param("year") String year,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
