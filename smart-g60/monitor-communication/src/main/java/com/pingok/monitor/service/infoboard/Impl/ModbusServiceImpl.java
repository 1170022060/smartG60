package com.pingok.monitor.service.infoboard.Impl;

import com.pingok.monitor.domain.infoboard.MbsAttribute;
import com.pingok.monitor.service.infoboard.IModbusService;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.WriteCoilRequest;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import org.springframework.stereotype.Service;

import static com.pingok.monitor.utils.ConvertUtils.value;

/**
 * @author
 * @time 2022/5/2 8:54
 */
@Service
public class ModbusServiceImpl implements IModbusService {

    static ModbusFactory modbusFactory = new ModbusFactory();

    static final class ValueType {
        public static final String BYTE = "byte";
        public static final String SHORT = "short";
        public static final String INT = "int";
        public static final String LONG = "long";
        public static final String FLOAT = "float";
        public static final String DOUBLE = "double";
        public static final String BOOLEAN = "boolean";
        public static final String STRING = "string";
        public static final String ENUM = "enum";
    }

    @Override
    public String read(MbsAttribute mbsAttribute) throws Exception {
        ModbusMaster modbusMaster = getMaster(mbsAttribute.getHost(), mbsAttribute.getPort());
        if (modbusMaster == null) {
            return null;
        }
        String result = null;
        try {
            result = readValue(modbusMaster, mbsAttribute);
        } catch (Exception e) {
            System.out.println("设备连接失败,host："+mbsAttribute.getHost() + "port："+mbsAttribute.getPort());
            return null;
        }
        return result;
    }

    @Override
    public Boolean write(MbsAttribute mbsAttribute, String value) throws Exception {
        ModbusMaster modbusMaster = getMaster(mbsAttribute.getHost(), mbsAttribute.getPort());
        if (modbusMaster == null) {
            return false;
        }
        Boolean flag = null;
        try {
            flag = writeValue(modbusMaster, mbsAttribute, value);
        } catch (Exception e) {
            System.out.println("设备连接失败,host："+mbsAttribute.getHost() + "port："+mbsAttribute.getPort());
            return false;
        }
        return flag;
    }

    public ModbusMaster getMaster(String host, int port) {
        System.out.println("Modbus 设备信息：" + host);
        ModbusMaster modbusMaster = null;
        try {
            IpParameters params = new IpParameters();
            params.setHost(host);
            params.setPort(port);
            modbusMaster = modbusFactory.createTcpMaster(params, true);
            modbusMaster.init();
        } catch (Exception e) {
            modbusMaster = null;
            System.out.println("设备连接失败,host："+host + "port："+port);
        }
        return modbusMaster;
    }

    public String readValue(ModbusMaster modbusMaster, MbsAttribute attribute) throws ModbusTransportException, ErrorResponseException {
        int slaveId = attribute.getSlaveId();
        int functionCode = attribute.getFuncCode();
        int offset = attribute.getOffset();
        switch (functionCode) {
            case 1:
                BaseLocator<Boolean> coilLocator = BaseLocator.coilStatus(slaveId, offset);
                Boolean coilValue = modbusMaster.getValue(coilLocator);
                return String.valueOf(coilValue);
            case 2:
                BaseLocator<Boolean> inputLocator = BaseLocator.inputStatus(slaveId, offset);
                Boolean inputStatusValue = modbusMaster.getValue(inputLocator);
                return String.valueOf(inputStatusValue);
            case 3:
                BaseLocator<Number> holdingLocator = BaseLocator.holdingRegister(slaveId, offset, getValueType(attribute.getAttributeType()));
                Number holdingValue = modbusMaster.getValue(holdingLocator);
                return String.valueOf(holdingValue);
            case 4:
                BaseLocator<Number> inputRegister = BaseLocator.inputRegister(slaveId, offset, getValueType(attribute.getAttributeType()));
                Number inputRegisterValue = modbusMaster.getValue(inputRegister);
                return String.valueOf(inputRegisterValue);
            default:
                return "0";
        }
    }

    public boolean writeValue(ModbusMaster modbusMaster, MbsAttribute mbsAttribute, String value) throws ModbusTransportException, ErrorResponseException {
        int slaveId = mbsAttribute.getSlaveId();
        int functionCode = mbsAttribute.getFuncCode();
        int offset = mbsAttribute.getOffset();
        switch (functionCode) {
            case 1:
                boolean coilValue = value(mbsAttribute.getAttributeType(), value);
                WriteCoilRequest coilRequest = new WriteCoilRequest(slaveId, offset, coilValue);
                WriteCoilResponse coilResponse = (WriteCoilResponse) modbusMaster.send(coilRequest);
                return !coilResponse.isException();
            case 3:
                BaseLocator<Number> locator = BaseLocator.holdingRegister(slaveId, offset, getValueType(mbsAttribute.getAttributeType()));
                modbusMaster.setValue(locator, value(mbsAttribute.getAttributeType(), value));
                return true;
            default:
                return false;
        }
    }

    public int getValueType(String type) {
        switch (type.toLowerCase()) {
            case ValueType.LONG:
                return DataType.FOUR_BYTE_INT_SIGNED;
            case ValueType.FLOAT:
                return DataType.FOUR_BYTE_FLOAT;
            case ValueType.DOUBLE:
                return DataType.EIGHT_BYTE_FLOAT;
            default:
                return DataType.TWO_BYTE_INT_SIGNED;
        }
    }
}