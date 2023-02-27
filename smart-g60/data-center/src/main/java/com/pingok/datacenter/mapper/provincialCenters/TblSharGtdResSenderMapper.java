package com.pingok.datacenter.mapper.provincialCenters;

import com.pingok.datacenter.domain.provincialCenters.TblSharGtdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author xia
 */
@Mapper
public interface TblSharGtdResSenderMapper {

    int add(TblSharGtdResSender tblSharGtdResSender);

    int addList(ProvincialCentersVo provincialCentersVo);

    List<TblSharGtdResSender> findByIds(ProvincialCentersVo provincialCentersVo);
}
