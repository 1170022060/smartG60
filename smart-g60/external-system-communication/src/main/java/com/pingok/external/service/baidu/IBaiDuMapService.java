package com.pingok.external.service.baidu;

import com.pingok.external.domain.baidu.TblBaiDuMapRecord;

public interface IBaiDuMapService {
    /**
     * 事件推送
     *
     * @param baiDuMapRecord
     */
    void eventPublish(TblBaiDuMapRecord baiDuMapRecord);

    /**
     * 事件解除
     *
     * @param id
     */
    void eventRelieve(Long id);
}
