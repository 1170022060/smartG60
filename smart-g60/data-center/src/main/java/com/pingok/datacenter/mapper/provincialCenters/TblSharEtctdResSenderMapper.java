package com.pingok.datacenter.mapper.provincialCenters;

import com.pingok.datacenter.domain.provincialCenters.TblSharEtctdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author xia
 */
@Mapper
public interface TblSharEtctdResSenderMapper {

    int add(TblSharEtctdResSender tblSharEtctdResSender);
    int addList(ProvincialCentersVo provincialCentersVo);

    List<TblSharEtctdResSender> findByIds(ProvincialCentersVo provincialCentersVo);
}
