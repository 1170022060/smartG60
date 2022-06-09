package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnTrans;
import com.pingok.datacenter.domain.trans.TblExTrans;
import com.ruoyi.common.core.mapper.CommonRepository;
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
}
