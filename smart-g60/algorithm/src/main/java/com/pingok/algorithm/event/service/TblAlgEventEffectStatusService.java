package com.pingok.algorithm.event.service;

import com.pingok.algorithm.event.entity.TblAlgEventEffectStatus;

import java.util.List;

/**
 * 事件影响状态业务接口
 */
public interface TblAlgEventEffectStatusService {

    /**
     * 查询事件影响状态列表
     * @param tblAlgEventEffectStatus
     * @return
     */
    List<TblAlgEventEffectStatus> listByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus);

    /**
     * 查询最新事件影响状态列表
     * @param tblAlgEventEffectStatus
     * @return
     */
    List<TblAlgEventEffectStatus> selectEventEffectStatusListByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus);

    /**
     * 查询事件影响状态详情
     * @param tblAlgEventEffectStatus
     * @return
     */
    TblAlgEventEffectStatus selectByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus);

    /**
     * 保存事件影响状态记录
     *
     * @param tblAlgEventEffectStatus
     * @return Boolean
     */
    Boolean saveTblAlgEventEffectStatus(TblAlgEventEffectStatus tblAlgEventEffectStatus);

    /**
     * 自动保存事件影响状态记录
     *
     * @return Boolean
     */
    Boolean autoSaveEventEffectStatus();

    /**
     * 修改事件影响状态记录
     *
     * @param tblAlgEventEffectStatus
     * @return Boolean
     */
    Boolean modifyTblAlgEventEffectStatus(TblAlgEventEffectStatus tblAlgEventEffectStatus);

    /**
     * 删除事件影响状态记录
     *
     * @param id
     * @return Boolean
     */
    Boolean deleteTblAlgEventEffectStatus(Long id);
}
