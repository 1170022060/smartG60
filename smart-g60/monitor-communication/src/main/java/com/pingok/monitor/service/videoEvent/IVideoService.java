package com.pingok.monitor.service.videoEvent;

public interface IVideoService {

    /**
     * 枪云联动接口
     * @param ubiLogicId
     */
    void linkage(Long ubiLogicId);
    Boolean eventUpdate(Long ubiLogicId,Integer uiState,String szRemark,String szUser);

    String getImgById(Long imgId);

    String getEventVideoById(Long ubiLogicId, Integer uiEventType, Integer uiVideoType);
}
