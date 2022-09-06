package com.pingok.station.mapper.tracer;

import com.pingok.station.domain.tracer.ListTracer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListTracerMapper {
    /**
     * 查询版本
     *
     * @param listType
     * @return 结果
     */
    public String selectVersion(String listType);

    /**
     * 查询是否存在该listType
     *
     * @param listType
     * @return 结果
     */
    public int selectListType(String listType);

    /**
     * 插入
     *
     * @param listType
     * @return 结果
     */
    public int insertTracer(String listType);
    /**
     * 更新版本追踪表
     *
     * @param listTracer
     * @return 结果
     */
    public int updateTracer(ListTracer listTracer);
}
