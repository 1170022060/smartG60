package com.pingok.gantry.utils;

import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @time 2022/7/7 16:20
 */
public class BeanUtil {
    /**
     * 获取null的字段
     *
     * @param source    源对象
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        Class clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Set<String> nullNames = new HashSet();
        for(Field field : fields) {
            Object value;
            Method method;
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(field.getName(), clazz);
            } catch (IntrospectionException e) {
                continue;
            }
            method = descriptor.getReadMethod();
            try {
                value = method.invoke(source);
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            }
            if (null == value) {
                nullNames.add(field.getName());
            }
        }
        String[] result = new String[nullNames.size()];
        return nullNames.toArray(result);
    }

    /**
     * 复制非NULL属性
     *
     * @param src   源对象
     * @param target    目标对象
     */
    public static void copyNotNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
