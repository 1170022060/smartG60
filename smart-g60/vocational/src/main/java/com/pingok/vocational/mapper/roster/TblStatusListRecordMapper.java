package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblStatusListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_STATUS_LIST_RECORD 数据层
 *
 * @author xia
 */
public interface TblStatusListRecordMapper extends CommonRepository<TblStatusListRecord> {

    @Select({"<script>" +
            "select a.VERSION as \"version\" ," +
            "a.CARD_ID as \"cardId\" ," +
            "b.DICT_LABEL as \"provId\" ," +
            "a.ISSUER_ID as \"issuerId\" ," +
            "c.DICT_LABEL as \"statusType\" ," +
            "d.DICT_LABEL as \"statusSign\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.INSERT_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"insertTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\" from  TBL_STATUS_LIST_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.PROV_ID and b.DICT_TYPE='province_id' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.STATUS_TYPE and c.DICT_TYPE='status_type' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.STATUS_SIGN and d.DICT_TYPE='list_sign' " +
            "where 1=1 " +
            "<when test='cardId != null'> " +
            " and CARD_ID = #{cardId} " +
            "</when>"+
            "order by a.CREATE_TIME " +
            "</script>"})
    List<Map> selectStatusList(@Param("cardId") String cardId);
}
