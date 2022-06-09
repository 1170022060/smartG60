package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExTransPass;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EX_TRANS_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExTransPassMapper {

    /**
     * 出口通行信息表入库
     *
     * @param tblExTransPass 接收出口通行信息表信息
     */
    public int insertExTransPass(TblExTransPass tblExTransPass);
}
