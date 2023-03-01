package com.pingok.monitor.service.common.Impl;

import com.pingok.monitor.service.common.ISocketService;
import com.pingok.monitor.utils.ByteUtils;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author
 * @time 2022/5/2 8:58
 */
@Service
public class SocketServiceImpl implements ISocketService {
    @Override
    public Socket clientSocket(String host, Integer port) {
        Socket socket = new Socket();
        SocketAddress endpoint = new InetSocketAddress(host, port);
        try {
            socket.connect(endpoint, 3000);
            socket.setSoTimeout(3000);
        } catch (IOException e) {
            System.out.println("初始化socket失败，ip端口：" + host + ":" + port);
            return null;
        }
        return socket;
    }

    @Override
    public void send(byte[] bytes, Socket socket) {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.write(bytes);
        } catch (Exception e) {
            System.out.println("发送失败，socket：" + socket.toString());
        }
    }

    @Override
    public byte[] receive(Socket socket) {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            return bytes;
        } catch (Exception e) {
            System.out.println("接收异常，socket：" + socket.toString());
        }
        return null;
    }

    @Override
    public int writeAndResult(byte[] bytes, Socket socket) {
        int retCode = 200;

        try {
            send(bytes, socket);
            byte[] receive = receive(socket);
            System.out.println("返回报文：" + ByteUtils.bytes2hex(receive));
            if(receive == null || receive.length == 0){
                retCode = -1;
            }
        } catch (Exception e) {
            System.out.println("接收异常，socket：" + socket.toString());
            retCode = -1;
        }

        return retCode;
    }
}
