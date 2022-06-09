package com.pingok.datacenter.mapper.lprtrans;

import com.pingok.datacenter.domain.lprtrans.TblExLprTrans;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EX_LPR_TRANS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExLprTransMapper {

    /**
     * 出口车牌识别流水表入库
     *
     * @param tblExLprTrans 出口车牌识别流水表
     * @return 结果
     */
    public int insertExLprTrans(TblExLprTrans tblExLprTrans);
}
