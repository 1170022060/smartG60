package com.pingok.algorithm.event.service;

import com.pingok.algorithm.event.entity.TblAlgEvent;

import java.util.List;

/**
 * 事件业务接口
 */
public interface TblAlgEventService {

    /**
     * 查询事件列表
     * @param tblAlgEvent
     * @return
     */
    List<TblAlgEvent> listByBean(TblAlgEvent tblAlgEvent);

    /**
     * 查询事未结束件列表
     * @return
     */
    List<TblAlgEvent> selectNoFinishEventList();

    /**
     * 查询事件详情
     * @param tblAlgEvent
     * @return
     */
    TblAlgEvent selectByBean(TblAlgEvent tblAlgEvent);

    /**
     * 保存事件记录
     *
     * @param tblAlgEvent
     * @return Boolean
     */
    Boolean saveTblAlgEvent(TblAlgEvent tblAlgEvent);

    /**
     * 修改事件记录
     *
     * @param tblAlgEvent
     * @return Boolean
     */
    Boolean modifyTblAlgEvent(TblAlgEvent tblAlgEvent);

    /**
     * 删除事件记录
     *
     * @param id
     * @return Boolean
     */
    Boolean deleteTblAlgEvent(Long id);
}
