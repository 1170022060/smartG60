package com.pingok.charge.service.lprtrans;

import com.pingok.charge.domain.lprtrans.TblEnLprTrans;
import com.pingok.charge.domain.lprtrans.TblExLprTrans;

/**
 * 出入口牌识 业务层
 *
 * @author ruoyi
 */
public interface ILprTransService {

    /**
     * 临时存储入口牌识信息
     * @param tblEnLprTrans
     */
    void addEn(TblEnLprTrans tblEnLprTrans);

    /**
     * 上传入口牌识信息
     * @param tblEnLprTrans
     */
    void updateEn(TblEnLprTrans tblEnLprTrans);

    /**
     * 临时存储出口牌识信息
     * @param tblExLprTrans
     */
    void addEx(TblExLprTrans tblExLprTrans);

    /**
     * 上传出口牌识信息
     * @param tblExLprTrans
     */
    void updateEx(TblExLprTrans tblExLprTrans);
}
