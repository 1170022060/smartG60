package com.pingok.algorithmBeiJing.service;

public interface IRocketMqService {

    /**
     * 发送门架交易记录
     */
    void trajectoryData(String plate,String instanceId,String startTime,String endTime);

    /**
     * 发送门架交易记录
     */
    void gantryTransactionLog(String startTime,String endTime);

    /**
     * 发送路网结构
     */
    void sendRoads();
}
