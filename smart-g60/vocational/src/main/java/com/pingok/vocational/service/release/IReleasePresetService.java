package com.pingok.vocational.service.release;

import com.pingok.vocational.domain.release.TblReleasePreset;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 信息发布预设 业务层
 *
 * @author ruoyi
 */
public interface IReleasePresetService {

    /**
     * 根据Id查询信息发布预设信息
     *
     * @param Id Id
     * @return 信息发布预设信息
     */
    public TblReleasePreset selectReleasePresetById(Long Id);

    /**
     * 根据信息类型、使用状态查询信息发布预设信息
     *
     * @param infoType 信息类型
     * @param status 使用状态
     * @return 信息发布预设信息
     */
    public List<Map> selectReleasePreset(Integer infoType,Integer status);

    /**
     * 根据预设信息id查询适合发送的设备列表
     *
     * @param id 信息类型
     * @return 信息发布预设信息
     */
    public List<Map> selectDevice(Long id);

    /**
     * 根据情报板、限速板查询设备信息
     *
     * @param type 类型
     * @return 设备信息
     */
    public List<Map> selectDeviceBoard(@Param("type") Integer type);

    /**
     * 新增信息发布预设信息
     *
     * @param tblReleasePreset 信息发布预设信息
     * @return 结果
     */
    public int insertReleasePreset(TblReleasePreset tblReleasePreset);

    /**
     * 修改信息发布预设信息
     *
     * @param tblReleasePreset 信息发布预设信息
     * @return 结果
     */
    public int updateReleasePreset(TblReleasePreset tblReleasePreset);

    /**
     * 根据id,更改信息发布预设状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验预设内容
     *
     * @param tblReleasePreset 信息发布预设信息
     * @return 结果
     */
    public String checkPresetInfoUnique(TblReleasePreset tblReleasePreset);

    /**
     * 根据信息类型查询部分信息发布预设信息，用于下拉列表
     *
     * @param infoType 信息发布预设信息
     * @return 结果
     */
    public List<Map> selectReleaseInfo(Integer infoType);
}
