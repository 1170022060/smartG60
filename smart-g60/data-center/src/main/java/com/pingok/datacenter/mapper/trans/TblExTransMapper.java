package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExTrans;
import com.pingok.datacenter.domain.trans.vo.UpdatePassIdVo;
import org.apache.ibatis.annotations.Mapper;

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
}
