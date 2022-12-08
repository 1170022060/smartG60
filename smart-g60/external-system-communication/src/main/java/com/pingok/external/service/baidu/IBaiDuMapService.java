package com.pingok.external.service.baidu;

public interface IBaiDuMapService {
    /**
     * 事件推送
     *
     * @param id
     */
    void eventPublish(Long id,Long eventType);

    /**
     * 事件解除
     *
     * @param id
     */
    void eventRelieve(Long id);
}
