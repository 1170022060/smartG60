package com.pingok.datacenter.mapper.lprtrans;

import com.pingok.datacenter.domain.lprtrans.TblEnLprTrans;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EN_LPR_TRANS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblEnLprTransMapper {

    /**
     * 入口车牌识别流水表入库
     *
     * @param tblEnLprTrans 入口车牌识别流水表
     * @return 结果
     */
    public int insertEnLprTrans(TblEnLprTrans tblEnLprTrans);
}
