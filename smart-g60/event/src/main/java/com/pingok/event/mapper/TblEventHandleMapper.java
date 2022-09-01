package com.pingok.event.mapper;

import com.pingok.event.domain.TblEventHandle;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TBL_EVENT_HANDLE 数据层
 *
 * @author qiumin
 */
public interface TblEventHandleMapper extends CommonRepository<TblEventHandle> {
    @Select("SELECT " +
            "teh.ID as \"id\", " +
            "teh.HANDLE_TIME as \"handleTime\", " +
            "teh.HANDLE_CONTENT as \"handleContent\", " +
            "usr.NICK_NAME as \"user\" " +
            "FROM " +
            "TBL_EVENT_HANDLE teh " +
            "LEFT JOIN  SYS_USER usr on usr.USER_ID = teh.USER_ID " +
            "WHERE 1=1 " +
            "AND teh.EVENT_ID = #{eventId} " +
            "ORDER BY teh.HANDLE_TIME")
    List<TblEventHandle> findByEventId(@Param("eventId") Long eventId);
}
