package com.pingok.station.mapper.auditList;

import com.pingok.station.domain.auditList.AuditPreTrans;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditPreTransMapper {
    /**
     * 预追缴稽核黑名单交易记录子表入库
     *
     * @param auditPreTrans
     * @return 结果
     */
    public int insertAuditPreTrans(AuditPreTrans auditPreTrans);

    /**
     * 清除稽核黑名单交易记录子表
     *
     * @return 结果
     */
    public void deleteAll();
}
