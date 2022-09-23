package com.pingok.station.mapper.auditList;

import com.pingok.station.domain.auditList.AuditAppend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditAppendMapper {

    /**
     * 稽核黑名单入库
     *
     * @param auditAppend
     * @return 结果
     */
    public int insertAuditAppend(AuditAppend auditAppend);
}
