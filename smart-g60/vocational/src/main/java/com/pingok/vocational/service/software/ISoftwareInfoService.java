package com.pingok.vocational.service.software;

import com.pingok.vocational.domain.software.TblSoftwareInfo;

import java.util.List;
import java.util.Map;

/**
 * 软件信息 业务层
 *
 * @author ruoyi
 */
public interface ISoftwareInfoService {

    /**
     * 根据Id查询软件信息
     *
     * @param Id Id
     * @return 软件信息
     */
    public TblSoftwareInfo selectSoftwareInfoById(Long Id);

    /**
     * 通过站(中心)编码、软件名称查询软件信息
     *
     * @param stationId 站(中心)编码
     * @param name 软件名称
     * @return 软件信息
     */
    List<Map> selectSoftwareInfo(String stationId,String name);

    /**
     * 新增软件信息
     *
     * @param tblSoftwareInfo 软件信息
     * @return 结果
     */
    public int insertSoftwareInfo(TblSoftwareInfo tblSoftwareInfo);

    /**
     * 修改软件信息
     *
     * @param tblSoftwareInfo 软件信息
     * @return 结果
     */
    public int updateSoftwareInfo(TblSoftwareInfo tblSoftwareInfo);

    /**
     * 校验软件编码
     *
     * @param tblSoftwareInfo 软件信息
     * @return 结果
     */
    public String checkSoftNumUnique(TblSoftwareInfo tblSoftwareInfo);
}
