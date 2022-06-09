package com.pingok.websocket.service;

/**
 *
 */
public interface MassageLinkApplicationService {
    /**
     *
     * <p>Title: sendMassage</p>
     * <p>Description: 指定组发送消息</p>
     * @param model
     * @param massage
     */
    boolean sendMassage(String model,String massage);

    /**
     *
     * <p>Title: sendMassage</p>
     * <p>Description: 向所有人发送消息</p>
     * @param massage
     */
    void sendMassage(String massage);

    /**
     *
     * <p>Title: cleanCacle</p>
     * <p>Description:清理缓存 </p>
     */
    void cleanCacle();
}
