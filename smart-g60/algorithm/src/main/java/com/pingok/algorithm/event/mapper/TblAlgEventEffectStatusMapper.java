package com.pingok.algorithm.event.mapper;

import com.pingok.algorithm.event.entity.TblAlgEventEffectStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblAlgEventEffectStatusMapper extends CommonRepository<TblAlgEventEffectStatus> {

    @Select("<script>" +
            " SELECT " +
            " ID as id, " +
            " EVENT_ID as eventId, " +
            " LINE_SPEED as lineSpeed, " +
            " SAMPLE_TIME as sampleTime, " +
            " EFFECT_FLG as effectFlg, " +
            " MILD_CONG_TIME as mildCongTime, " +
            " MED_CONG_TIME as medCongTime, " +
            " SEV_CONG_TIME as sevCongTime, " +
            " CREATE_TIME as createTime, " +
            " UPDATE_TIME as updateTime " +
            " FROM " +
            " TBL_ALG_EVENT_EFFECT_STATUS " +
            " WHERE " +
            " EVENT_ID = #{eventId} " +
            " AND " +
            " CREATE_TIME = ( " +
            " SELECT " +
            " MAX(CREATE_TIME) " +
            " FROM " +
            " TBL_ALG_EVENT_EFFECT_STATUS " +
            " WHERE " +
            " EVENT_ID = #{eventId} " +
            " ) " +
            "</script>")
    TblAlgEventEffectStatus getLatestEventEffectStatusByEventId(@Param("eventId") Long eventId);

    @Select("<script>" +
            " SELECT " +
            " ID as id, " +
            " EVENT_ID as eventId, " +
            " LINE_SPEED as lineSpeed, " +
            " SAMPLE_TIME as sampleTime, " +
            " EFFECT_FLG as effectFlg, " +
            " MILD_CONG_TIME as mildCongTime, " +
            " MED_CONG_TIME as medCongTime, " +
            " SEV_CONG_TIME as sevCongTime, " +
            " CREATE_TIME as createTime, " +
            " UPDATE_TIME as updateTime " +
            " FROM " +
            " TBL_ALG_EVENT_EFFECT_STATUS " +
            " WHERE " +
            " EVENT_ID = #{eventId} " +
            " AND " +
            " CREATE_TIME = ( " +
            " SELECT " +
            " MIN(CREATE_TIME) " +
            " FROM " +
            " TBL_ALG_EVENT_EFFECT_STATUS " +
            " WHERE " +
            " EVENT_ID = #{eventId} " +
            " ) " +
            "</script>")
    TblAlgEventEffectStatus getFirstEventEffectStatusByEventId(@Param("eventId") Long eventId);

    @Select("<script>" +
            " SELECT " +
            " efs.ID as id, " +
            " efs.EVENT_ID as eventId, " +
            " efs.LINE_SPEED as lineSpeed, " +
            " efs.SAMPLE_TIME as sampleTime, " +
            " efs.EFFECT_FLG as effectFlg, " +
            " efs.MILD_CONG_TIME as mildCongTime, " +
            " efs.MED_CONG_TIME as medCongTime, " +
            " efs.SEV_CONG_TIME as sevCongTime, " +
            " efs.CREATE_TIME as createTime, " +
            " efs.UPDATE_TIME as updateTime " +
            " FROM ( " +
            " SELECT " +
            " EVENT_ID, " +
            " MAX(CREATE_TIME) as LAST_TIME " +
            " FROM " +
            " TBL_ALG_EVENT_EFFECT_STATUS " +
            " WHERE " +
            " CREATE_TIME is not null " +
            " GROUP BY " +
            " EVENT_ID " +
            " ) temp " +
            " LEFT JOIN " +
            " TBL_ALG_EVENT_EFFECT_STATUS efs on efs.EVENT_ID = temp.EVENT_ID AND efs.CREATE_TIME = temp.LAST_TIME " +
            " where 1 = 1 " +
            " <if test = 'eventId != null' > " +
            " and temp.EVENT_ID = #{eventId} " +
            " </if> " +
            " ORDER BY " +
            " temp.EVENT_ID " +
            "</script>")
    List<TblAlgEventEffectStatus> selectEventEffectStatusListByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus);
}
