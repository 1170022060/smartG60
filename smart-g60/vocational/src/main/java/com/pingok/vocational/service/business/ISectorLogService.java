package com.pingok.vocational.service.business;

import com.pingok.vocational.domain.business.vo.SectorLogEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 扇区日志 服务层
 *
 * @author ruoyi
 */
public interface ISectorLogService {
    /**
     * 通过起止时间、GID、车道编码、通行介质查询扇区日志信息
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param gid GID
     * @param laneId 车道编码
     * @param passType 通行介质
     * @return 扇区日志信息
     */
    List<Map> selectSectorLog(Date startTime, Date endTime, String gid, String laneId, Integer passType);

    /**
     * 通过对应扇区日志表ID查询文件详情
     *
     * @param logId 对应扇区日志表ID
     */
    SectorLogEnum selectDetails(Long logId);

    /**
     * 通过gid查询文件详情
     *
     * @param gid
     */
    SectorLogEnum findByGidAndLaneHex(String gid,String laneHex);
}
