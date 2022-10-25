package com.pingok.vocational.mapper.nameList;

import com.pingok.vocational.domain.nameList.TblBlackCardLogN;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblBlackCardLogNMapper extends CommonRepository<TblBlackCardLogN> {
    @Select(
            "select a.ID as \"id\", a.CARD_ID as \"cardId\" ," +
            "a.ISSUER_ID as \"issuerId\" , a.INSERT_TIME as \"insertTime\"," +
            "c.TYPE as \"type\"," +
            "case when a.STATUS = 1 then '进入状态名单' when a.STATUS = 2 then '解除状态名单' end as \"status\"," +
            "a.CREATION_TIME as \"creationTime\" , a.VERSION as \"version\"," +
            "to_char(a.UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "a.VERSION_ID as \"versionId\" " +
            "from TBL_BLACK_CARD_LOG a " +
            "where a.VERSION_ID = #{id}")
    List<Map> findById(@Param("id") Long id);

}
