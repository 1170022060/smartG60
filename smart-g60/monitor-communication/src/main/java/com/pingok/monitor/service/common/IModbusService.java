package com.pingok.monitor.service.common;

import com.pingok.monitor.domain.common.MbsAttribute;

/**
 * @author
 * @time 2022/5/2 8:52
 */
public interface IModbusService {
    byte[] readHoldingRegister(MbsAttribute mbs);
    int writeRegister(MbsAttribute mbs, short value);
    int writeRegister(String portName, Integer slaveId, Integer address, Short value);
    int writeMultiRegister(MbsAttribute mbs, short[] values);
    int writeMultiRegister(String portName, Integer slaveId, Integer address, short[] value);
}
