package com.pingok.station.mapper.auditList;

import com.pingok.station.domain.auditList.AuditPre;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditPreMapper {

    /**
     * 稽核黑名单入库
     *
     * @param auditPre
     * @return 结果
     */
    public int insertAuditPre(AuditPre auditPre);

    /**
     * 清除稽核黑名单
     *
     * @return 结果
     */
    public void deleteAll();
}
