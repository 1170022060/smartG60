package com.pingok.monitor.service.infoboard;

public interface IVmsService {
    int publish(String pubInfo);
    void collect(String devList);
}
