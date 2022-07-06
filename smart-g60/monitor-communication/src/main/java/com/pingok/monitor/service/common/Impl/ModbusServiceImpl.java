package com.pingok.monitor.service.common.Impl;

import com.pingok.monitor.domain.common.MbsAttribute;
import com.pingok.monitor.service.common.IModbusService;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.*;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;
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
    public boolean writeRegister(MbsAttribute mbs, short value) {
        boolean ret = true;
        try {
            WriteRegisterRequest req = new WriteRegisterRequest(mbs.getSlaveId(), mbs.getOffset(), value);
            ModbusMaster master = getMaster(mbs.getHost(), mbs.getPort());
            ModbusResponse resp = master.send(req);
            if(resp.isException()) {
                log.error("车检器写寄存器失败：" + resp.getExceptionMessage());
                ret = false;
            }
        } catch (Exception e) {
            log.error("车检器写寄存器失败：" + e.getMessage());
            ret = false;
        }
        return ret;
    }

    @Override
    public void writeMultiRegister(MbsAttribute mbs, short[] values) {
        try {
            WriteRegistersRequest req = new WriteRegistersRequest(mbs.getSlaveId(), mbs.getOffset(), values);
            ModbusMaster master = getMaster(mbs.getHost(), mbs.getPort());
            ModbusResponse resp = master.send(req);
            if(resp.isException()) {
                log.error("情报板写多寄存器失败：" + resp.getExceptionMessage());
            }
        } catch (Exception e) {
            log.error("情报板写多寄存器失败：" + e.getMessage());
        }
    }
}