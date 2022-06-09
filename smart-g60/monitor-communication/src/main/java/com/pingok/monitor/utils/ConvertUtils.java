package com.pingok.monitor.utils;

import cn.hutool.core.convert.Convert;
import com.ruoyi.common.core.constant.Constants;

import java.lang.reflect.Type;

/**
 * @author
 * @time 2022/5/2 9:04
 */
public class ConvertUtils {

    public static <T> T value(String type, String value) {
        return Convert.convert(getTypeClassName(type), value);
    }

    /**
     * 获取基本类型 Class Name
     *
     * @param type String Type, short/int/long/float/double/boolean/string
     * @return Class Type
     */
    public static Type getTypeClassName(String type) {
        Type classType = String.class;
        switch (type.toLowerCase()) {
            case Constants.ValueType.BYTE:
                classType = Byte.TYPE;
                break;
            case Constants.ValueType.SHORT:
                classType = Short.TYPE;
                break;
            case Constants.ValueType.INT:
                classType = Integer.TYPE;
                break;
            case Constants.ValueType.LONG:
                classType = Long.TYPE;
                break;
            case Constants.ValueType.FLOAT:
                classType = Float.TYPE;
                break;
            case Constants.ValueType.DOUBLE:
                classType = Double.TYPE;
                break;
            case Constants.ValueType.BOOLEAN:
                classType = Boolean.TYPE;
                break;
            case Constants.ValueType.ENUM:
                classType = Enum.class;
                break;
            default:
                break;
        }
        return classType;
    }
}
