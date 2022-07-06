package com.pingok.monitor.service.common;

import java.net.Socket;

/**
 * @author
 * @time 2022/5/2 8:53
 */
public interface ISocketService {
    Socket clientSocket(String host, Integer port);
    void send(byte[] bytes,Socket socket);
    byte[] receive(Socket socket);
    int writeAndResult(byte[] bytes,Socket socket);
}
