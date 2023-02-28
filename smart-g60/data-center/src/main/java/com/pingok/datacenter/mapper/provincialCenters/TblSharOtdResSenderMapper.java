package com.pingok.datacenter.mapper.provincialCenters;

import com.pingok.datacenter.domain.provincialCenters.TblSharGvidResSender;
import com.pingok.datacenter.domain.provincialCenters.TblSharOtdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author xia
 */
@Mapper
public interface TblSharOtdResSenderMapper {

    TblSharOtdResSender findById(TblSharOtdResSender tblSharOtdResSender);

    int update(TblSharOtdResSender tblSharOtdResSender);

    int add(TblSharOtdResSender tblSharOtdResSender);

    int addList(ProvincialCentersVo provincialCentersVo);

    List<TblSharOtdResSender> findByIds(ProvincialCentersVo provincialCentersVo);
}
