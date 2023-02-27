package com.pingok.datacenter.mapper.provincialCenters;

import com.pingok.datacenter.domain.provincialCenters.TblSharEnpdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author xia
 */
@Mapper
public interface TblSharEnpdResSenderMapper {

    int add(TblSharEnpdResSender tblSharEnpdResSender);

    int addList(ProvincialCentersVo provincialCentersVo);

    List<TblSharEnpdResSender> findByIds(ProvincialCentersVo provincialCentersVo);
}
