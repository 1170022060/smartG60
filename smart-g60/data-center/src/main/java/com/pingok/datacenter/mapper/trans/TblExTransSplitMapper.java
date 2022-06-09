package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExTransSplit;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EX_TRANS_SPLIT_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExTransSplitMapper{

    /**
     * 出口通行分省信息表入库
     *
     * @param tblExTransSplit 接收出口通行分省信息表信息
     */
    public int insertExTransSplit(TblExTransSplit tblExTransSplit);
}
