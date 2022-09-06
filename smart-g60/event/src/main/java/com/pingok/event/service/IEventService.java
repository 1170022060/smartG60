package com.pingok.event.service;

import com.alibaba.fastjson.JSONArray;
import com.pingok.event.domain.TblEventHandle;
import com.pingok.event.domain.TblEventRecord;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 事件服务 业务层
 *
 * @author ruoyi
 */
public interface IEventService {


    /**
     * 保存事件视频
     * @param ubiLogicId
     * @param url
     */
    void updateEventVideo(Long ubiLogicId, String url);

    /**
     * 事件报告
     * @param id
     * @return
     */
    TblEventRecord report(Long id);

    /**
     * 事件解除
     * @param id
     */
    void relieve(Long id);

    /**
     * 填报处置内容
     * @param tblEventHandles
     */
    void handleContent(List<TblEventHandle> tblEventHandles);

    /**
     * 应急处置
     * @param id 事件id
     * @param eventPlan 应急预案数组
     */
    void handle(Long id, JSONArray eventPlan);

    /**
     * 事件误报
     * @param id
     */
    void fault(Long id,String remark);

    /**
     * 事件确认
     * @param id
     */
    void confirm(Long id,String eventType,String remark,String direction);

    /**
     * 分页查询
     * @param status
     * @return
     */
    List<Map> search(Integer status);

    /**
     * 根据id查询事件
     * @param id
     * @return
     */
    TblEventRecord findById(Long id);

    /**
     * 新增
     * @param tblEventRecord
     * @return
     */
    int insert(TblEventRecord tblEventRecord);

    /**
     * 编辑
     * @param tblEventRecord
     * @return
     */
    int update(TblEventRecord tblEventRecord);

    List<TblEventRecord> event();
}
