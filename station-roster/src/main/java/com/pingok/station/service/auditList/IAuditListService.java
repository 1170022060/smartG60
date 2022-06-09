package com.pingok.station.service.auditList;


import com.pingok.station.domain.auditList.vo.AuditVo;

import java.util.List;

public interface IAuditListService {
    /**
     * 下载稽核黑名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载稽核黑名单全量
     * @param version 版本号
     */
    void all(String version);

    /**
     * 稽核黑名单全量解压入库
     * @param version 版本号
     */
    void unzipAll(String version);

    void insert(List<AuditVo> list);

    void file();
}
