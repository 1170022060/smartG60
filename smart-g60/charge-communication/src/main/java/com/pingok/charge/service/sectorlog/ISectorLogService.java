package com.pingok.charge.service.sectorlog;


import com.pingok.charge.domain.sectorlog.vo.SectorLogVo;

/**
 * 牵引车名单 业务层
 *
 * @author ruoyi
 */
public interface ISectorLogService {
    /**
     * 获取车道日志
     * @param laneHex
     * @param gid
     * @return
     */
    SectorLogVo getSectorLog(String laneHex, String gid);

    /**
     * 上传车道日志
     * @param sectorLogVo
     */
    void updateSectorLog(SectorLogVo sectorLogVo);
}
