package com.pingok.external.service.artemis;

public interface IArtemisService {
    String httpRequest(String api,String body);

    void updateStatus();
}
