package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnTrans;
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
}
