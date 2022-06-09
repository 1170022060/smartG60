package com.pingok.monitor.service.infoboard;

import com.pingok.monitor.domain.infoboard.MbsAttribute;

/**
 * @author
 * @time 2022/5/2 8:52
 */
public interface IModbusService {
    String read(MbsAttribute mbsAttribute) throws Exception;

    Boolean write(MbsAttribute mbsAttribute, String value) throws Exception;
}
