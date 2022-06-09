package com.ruoyi.common.core.mapper;


import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface CommonRepository<T> extends Mapper<T>, InsertListMapper<T>, AggregationMapper<T> {
}
