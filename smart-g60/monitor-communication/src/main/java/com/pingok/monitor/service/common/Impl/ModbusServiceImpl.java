package com.pingok.monitor.service.common.Impl;

import com.pingok.monitor.domain.common.MbsAttribute;
import com.pingok.monitor.service.common.IModbusService;

import com.pingok.monitor.service.infoboard.Impl.SerialPortWrapperImpl;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.*;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;

import gnu.io.SerialPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author
 * @time 2022/5/2 8:54
 */
@Slf4j
@Service
public class ModbusServiceImpl implements IModbusService {

    static ModbusFactory modbusFactory = new ModbusFactory();

    public ModbusMaster getMaster(String host, int port) {
        ModbusMaster modbusMaster = null;
        try {
            IpParameters params = new IpParameters();
            params.setHost(host);
            params.setPort(port);
            modbusMaster = modbusFactory.createTcpMaster(params, false);
            modbusMaster.init();
        } catch (Exception e) {
            modbusMaster = null;
            log.error("情报板modbus设备连接失败,host："+host + "port："+port);
        }
        return modbusMaster;
    }

    @Override
    public byte[] readHoldingRegister(MbsAttribute mbs) {
        try {
            // 03 Holding Register类型数据读取
            ReadHoldingRegistersRequest req = new ReadHoldingRegistersRequest(mbs.getSlaveId(), mbs.getOffset(), mbs.getCount());
            ModbusMaster master = getMaster(mbs.getHost(), mbs.getPort());
            ModbusResponse resp = master.send(req);
            ByteQueue byteQueue = new ByteQueue(1024);
            resp.write(byteQueue);
            return byteQueue.popAll();
        } catch (Exception e) {
            log.error("情报板读寄存器失败：" + e.getMessage());
        }
        return null;
    }

    @Override
    public int writeRegister(MbsAttribute mbs, short value) {
        int ret = 200;
        try {
            WriteRegisterRequest req = new WriteRegisterRequest(mbs.getSlaveId(), mbs.getOffset(), value);
            ModbusMaster master = getMaster(mbs.getHost(), mbs.getPort());
            ModbusResponse resp = master.send(req);
            if(resp.isException()) {
                log.error("情报板寄存器失败：" + resp.getExceptionMessage());
                ret = -1;
            }
        } catch (Exception e) {
            log.error("情报板写寄存器异常：" + e.getMessage());
            ret = -1;
        }
        return ret;
    }

    @Override
    public int writeRegister(String portName, Integer slaveId, Integer address, Short value) {

        SerialPortWrapperImpl wrapper = new SerialPortWrapperImpl(portName, 9600,
                SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, 0, 0);

//        https://blog.csdn.net/zhangFG77/article/details/126423708
        ModbusFactory modbusFactory = new ModbusFactory();
        ModbusMaster master = modbusFactory.createRtuMaster(wrapper);
        try {
            master.init();
            WriteRegisterRequest request = new WriteRegisterRequest(slaveId, address, value);
            WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);
            if (response.isException()){
                log.error("情报板串口，写保持寄存器错误：" + response.getExceptionMessage());
                return -1;
            }
        } catch (Exception ex){
            log.error("情报板串口，写寄存器异常：" + ex.getMessage());
            return -1;
        }
        return 200;
    }

    @Override
    public int writeMultiRegister(MbsAttribute mbs, short[] values) {
        try {
            WriteRegistersRequest req = new WriteRegistersRequest(mbs.getSlaveId(), mbs.getOffset(), values);
            ModbusMaster master = getMaster(mbs.getHost(), mbs.getPort());
            ModbusResponse resp = master.send(req);
            if(resp.isException()) {
                log.error("情报板写多寄存器失败：" + resp.getExceptionMessage());
            }
        } catch (Exception e) {
            log.error("情报板写多寄存器失败：" + e.getMessage());
            return -1;
        }
        return 200;
    }

    @Override
    public int writeMultiRegister(String portName, Integer slaveId, Integer address, short[] value) {

        SerialPortWrapperImpl wrapper = new SerialPortWrapperImpl(portName, 9600,
                SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, 0, 0);
        ModbusFactory modbusFactory = new ModbusFactory();
        ModbusMaster master = modbusFactory.createRtuMaster(wrapper);
        try {
            master.init();
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, address, value);
            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);
            if (response.isException()){
                log.error("情报板串口，写多保持寄存器错误：" + response.getExceptionMessage());
                return -1;
            }
        } catch (Exception ex){
            log.error("情报板串口，写多寄存器异常：" + ex.getMessage());
            return -1;
        }
        return 200;
    }
}