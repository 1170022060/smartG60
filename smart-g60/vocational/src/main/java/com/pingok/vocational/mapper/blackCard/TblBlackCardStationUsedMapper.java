package com.pingok.vocational.mapper.blackCard;

import com.pingok.vocational.domain.blackCard.TblBlackCardStationUsed;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblBlackCardStationUsedMapper extends CommonRepository<TblBlackCardStationUsed> {
    @Select({"<script>" +
            "select a.ID as \"id\" , a.STATION_HEX as \"stationHex\", " +
            "c.STATION_NAME as \"stationName\", " +
            "a.OBU_VERSION as \"obuVersion\", " +
            "a.APPLY_TIME as \"applyTime\", a.CREATE_TIME as \"createTime\", " +
            "a.CPC_VERSION as \"cpcVersion\" " +
            "from TBL_BLACK_CARD_STATION_USED a " +
            "left join TBL_BASE_STATION_INFO c on a.STATION_HEX = c.STATION_HEX " +
            "where 1=1 " +
            "order by a.STATION_HEX" +
            "</script>"})
    List<Map> selectList();

}
