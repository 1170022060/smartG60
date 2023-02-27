package com.pingok.datacenter.mapper.provincialCenters;

import com.pingok.datacenter.domain.provincialCenters.TblSharGvidResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author xia
 */
@Mapper
public interface TblSharGvidResSenderMapper {

    int add(TblSharGvidResSender tblSharGvidResSender);

    int addList(ProvincialCentersVo provincialCentersVo);

    List<TblSharGvidResSender> findByIds(ProvincialCentersVo provincialCentersVo);
}
