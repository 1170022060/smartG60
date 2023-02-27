package com.pingok.datacenter.mapper.provincialCenters;

import com.pingok.datacenter.domain.provincialCenters.TblSharSvidResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author xia
 */
@Mapper
public interface TblSharSvidResSenderMapper {

    int add(TblSharSvidResSender tblSharSvidResSender);

    int addList(ProvincialCentersVo provincialCentersVo);

    List<TblSharSvidResSender> findByIds(ProvincialCentersVo provincialCentersVo);
}
