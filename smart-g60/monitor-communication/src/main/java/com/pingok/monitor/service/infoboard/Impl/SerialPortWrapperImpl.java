package com.pingok.monitor.service.infoboard.Impl;

import com.pingok.monitor.utils.SerialInputStream;
import com.pingok.monitor.utils.SerialOutputStream;
import com.serotonin.modbus4j.serial.SerialPortWrapper;
import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class SerialPortWrapperImpl implements SerialPortWrapper {

    private SerialPort port;
    private String commPortId;
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int parity;
    private int flowControlIn;
    private int flowControlOut;

    public SerialPortWrapperImpl(String commPortId, int baudRate, int dataBits, int stopBits, int parity,
                                 int flowControlIn, int flowControlOut) {

            this.commPortId = commPortId;
            this.baudRate = baudRate;
            this.dataBits = dataBits;
            this.stopBits = stopBits;
            this.parity = parity;
            this.flowControlIn = flowControlIn;
            this.flowControlOut = flowControlOut;

            port = new SerialPort(this.commPortId);
        }

    @Override
    public void close() throws Exception {
        port.closePort();
    }

    @Override
    public void open() {
        try {
            port.openPort();
            port.setParams(this.getBaudRate(), this.getDataBits(), this.getStopBits(), this.getParity());
            port.setFlowControlMode(0 | 0);
        } catch (SerialPortException ex) {
            log.error("打开串口异常 : {} for {} ", port.getPortName(), ex);
        }
    }

    @Override
    public InputStream getInputStream() {
        return new SerialInputStream(port);
    }

    @Override
    public OutputStream getOutputStream() {
        return new SerialOutputStream(port);
    }

    @Override
    public int getBaudRate() {
        return baudRate;
    }

    @Override
    public int getDataBits() {
        return dataBits;
    }

    @Override
    public int getStopBits() {
        return stopBits;
    }

    @Override
    public int getParity() {
        return parity;
    }
}
