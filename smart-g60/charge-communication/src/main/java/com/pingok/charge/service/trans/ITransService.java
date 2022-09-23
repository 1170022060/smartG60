package com.pingok.charge.service.trans;

import com.pingok.charge.domain.trans.vo.EnTransEnum;
import com.pingok.charge.domain.trans.vo.ExTransEnum;

/**
 * 出入口流水 业务层
 *
 * @author ruoyi
 */
public interface ITransService {

    /**
     * 临时存储入口流水信息
     * @param enTransEnum
     */
    void addEn(EnTransEnum enTransEnum);

    /**
     * 上传入口流水信息
     * @param enTransEnum
     */
    void updateEn(EnTransEnum enTransEnum);

    /**
     * 临时存储出口流水信息
     * @param exTransEnum
     */
    void addEx(ExTransEnum exTransEnum);

    /**
     * 上传出口流水信息
     * @param exTransEnum
     */
    void updateEx(ExTransEnum exTransEnum);
}
