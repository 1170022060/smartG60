package com.pingok.idproducer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 数据层
 *
 * @author qiumin
 */
@Mapper
public interface IdProducerMapper {
    @Select("SELECT ID_SEQ.nextval FROM dual")
    Object nextId();
}