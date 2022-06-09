package com.pingok.vocational.service.software;


import com.pingok.vocational.domain.software.TblSoftwareUpdate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 软件更新 业务层
 *
 * @author ruoyi
 */
public interface ISoftwareUpdateService {

    /**
     * 根据Id查询软件更新信息
     *
     * @param Id Id
     * @return 软件更新信息
     */
    public TblSoftwareUpdate selectSoftwareUpdateById(Long Id);

    /**
     * 通过软件名称、软件类型、更新状态查询软件更新信息
     *
     * @param name 软件名称
     * @param softwareType 软件名称
     * @param status 软件名称
     * @return 软件更新信息
     */
    List<Map> selectSoftwareUpdate(String name,Integer softwareType,Integer status);

    /**
     * 新增软件更新信息
     *
     * @param tblSoftwareUpdate 软件更新信息
     * @return 结果
     */
    public int insertSoftwareUpdate(TblSoftwareUpdate tblSoftwareUpdate);

    /**
     * 修改软件更新信息
     *
     * @param tblSoftwareUpdate 软件更新信息
     * @return 结果
     */
    public int updateSoftwareUpdate(TblSoftwareUpdate tblSoftwareUpdate);

    /**
     * 根据id,更改更新状态为已发布
     *
     * @param id id
     * @return 结果
     */
    public int updatePublished(Long id);


    /**
     * 根据id,更改更新状态为下架
     *
     * @param id id
     * @return 结果
     */
    public int updateDiscard(Long id);
}
