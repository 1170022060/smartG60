package com.pingok.monitor.service.videoEvent;

public interface IVideoService {

    Boolean eventUpdate(Long ubiLogicId,Integer uiState,String szRemark,String szUser);

    String getImgById(Long imgId);

    String getEventVideoById(Long ubiLogicId, Integer uiEventType, Integer uiVideoType);
}
