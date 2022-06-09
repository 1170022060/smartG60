package com.pingok.datacenter.service.lprtrans;

import com.pingok.datacenter.domain.lprtrans.TblEnLprTrans;
import com.pingok.datacenter.domain.lprtrans.TblExLprTrans;

/**
 * 出入口车牌识别流水数据入库 业务层
 *
 * @author ruoyi
 */
public interface ILprTransService {

    /**
     * 入口车牌识别流水表入库
     *
     * @param tblEnLprTrans 入口车牌识别流水表
     * @return 结果
     */
    public int insertEnLprTrans(TblEnLprTrans tblEnLprTrans);

    /**
     * 出口车牌识别流水表入库
     *
     * @param tblExLprTrans 出口车牌识别流水表
     * @return 结果
     */
    public int insertExLprTrans(TblExLprTrans tblExLprTrans);

    /**
     * 出口车牌识别流水表入汇总表
     *
     * @param tblEnLprTrans 出口车牌识别流水表
     * @return 结果
     */
    public int insertEnLprSummary(TblEnLprTrans tblEnLprTrans);

    /**
     * 出口车牌识别流水表入汇总表
     *
     * @param tblExLprTrans 出口车牌识别流水表
     * @return 结果
     */
    public int insertExLprSummary(TblExLprTrans tblExLprTrans);
}
