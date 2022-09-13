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
            "case when a.STATUS = 1 then '进入状态名单' when a.STATUS = 2 then '解除状态名单' end as \"status\"," +
            "a.CREATION_TIME as \"creationTime\" , a.VERSION as \"version\"," +
            "to_char(a.UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"updateTime\" , a.MEDIA_TYPE as \"mediaType\", " +
            "a.APPLY_TIME as \"applyTime\" " +
            "from TBL_BLACK_CARD a " +
            "<when test='mediaType == 1'> " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE = to_char(a.TYPE) and c.DICT_TYPE = 'blackcard_obu_type' " +
            "</when>" +
            "<when test='mediaType == 2'> " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE = to_char(a.TYPE) and c.DICT_TYPE = 'blackcard_cpc_type' " +
            "</when>" +
            "where 1=1 and a.MEDIA_TYPE = #{mediaType}" +
            "<when test='mediaId != null'> " +
            " and a.MEDIA_ID like CONCAT(#{mediaId}, '%') " +
            "</when>" +
            "</script>"})
    List<Map> selectByMedia(@Param("mediaId") String mediaId, @Param("mediaType") Integer mediaType);
}
