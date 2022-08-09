package com.pingok.station.mapper.suspectList;
import com.pingok.station.domain.suspectList.SuspectArea;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SuspectAreaMapper {
    /**
     * 疑似违法车辆名单全量入库
     *
     * @param suspectArea
     * @return 结果
     */
    public int insertSuspect(SuspectArea suspectArea);

    /**
     * 清除疑似违法车辆名单
     *
     * @return 结果
     */
    public void deleteAll();
}
