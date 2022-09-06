package com.pingok.vocational.mapper.blackCard;

import com.pingok.vocational.domain.blackCard.TblBlackCardLog;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblBlackCardLogMapper extends CommonRepository<TblBlackCardLog> {
    @Select({"<script>" +
            "select a.ID as \"id\", a.MEDIA_ID as \"mediaId\" ," +
            "a.ISSUER_ID as \"issuerId\" , a.INSERT_TIME as \"insertTime\"," +
            "c.DICT_LABEL as \"type\"," +
            "case when a.STATUS = 1 then '进入状态名单' when a.STATUS = 2 then '解除状态名单' as \"status\"," +
            "a.CREATION_TIME as \"creationTime\" , a.VERSION as \"version\"," +
            "a.UPDATE_TIME as \"updateTime\" , a.MEDIA_TYPE as \"mediaType\" " +
            "from TBL_BLACK_CARD a " +
            "<when test='a.MEDIA_TYPE == 1'> " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE = to_char(a.TYPE) and c.DICT_TYPE = 'blackcard_obu_type' " +
            "</when>" +
            "<when test='a.MEDIA_TYPE == 2'> " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE = to_char(a.TYPE) and c.DICT_TYPE = 'blackcard_cpc_type' " +
            "</when>" +
            "where 1=1 " +
            "<when test='mediaId != null'> " +
            " and a.MEDIA_ID like CONCAT(#{mediaId}, '%') " +
            "</when>" +
            "</script>"})
    List<Map> selectByMediaId(@Param("mediaId") String mediaId);
}
