package com.pingok.monitor.service.infoboard;

import com.pingok.monitor.domain.infoboard.MbsAttribute;

/**
 * @author
 * @time 2022/5/2 8:52
 */
public interface IModbusService {
    byte[] readHoldingRegister(MbsAttribute mbs);
    boolean writeRegister(MbsAttribute mbs, short value);
    void writeMultiRegister(MbsAttribute mbs, short[] values);
}
