package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnTrans;
import com.pingok.datacenter.domain.trans.vo.EnTranFlow;
import com.pingok.datacenter.domain.trans.vo.UpdatePassIdVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

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
}
