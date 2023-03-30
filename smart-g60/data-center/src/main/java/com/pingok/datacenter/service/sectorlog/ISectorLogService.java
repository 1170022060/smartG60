package com.pingok.datacenter.service.sectorlog;

import com.pingok.datacenter.domain.sectorlog.vo.SectorLogVo;

/**
 * 扇区日志入库 业务层
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
    void getSectorLog(String laneHex, String gid);

    /**
     * 扇区日志主表入库
     *
     * @param sectorLogVo 接收扇区日志信息
     */
    public long insertSectorLog(SectorLogVo sectorLogVo);

    /**
     * CPC EF02日志表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param ef02 CPC EF02信息
     */
    public int insertCpcEf02Log(Long logId ,String ef02);

    /**
     * CPC EF04日志表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param ef04 CPC EF04信息
     */
    public int insertCpcEf04Log(Long logId ,String ef04);

    /**
     * CPU 0015日志表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param file0015 CPU 0015信息
     */
    public int insertCpu0015Log(Long logId ,String file0015);

    /**
     * CPU 0019日志表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param file0019 CPU 0019信息
     */
    public int insertCpu0019Log(Long logId ,String file0019);

    /**
     * OBU EF01日志表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param obuVehicleInfo OBU EF01信息
     */
    public int insertObuEf01Log(Long logId ,String obuVehicleInfo);

    /**
     * OBU EF04日志表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param ef04 OBU EF04信息
     */
    public long insertObuEf04Log(Long logId ,String ef04);

    /**
     * OBU EF04省份子表入库
     *
     * @param logId 对应扇区日志主表ID
     * @param province 省份信息
     */
    public int insertObuEf04LogProv(Long logId ,String province);

    /**
     * 查询抢险救灾名单
     *
     * @return 结果
     * @param gid
     */
    public int checkGidUnique(String gid);
}
