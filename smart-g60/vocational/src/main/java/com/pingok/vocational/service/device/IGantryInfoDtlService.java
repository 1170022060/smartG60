package com.pingok.vocational.service.device;

import com.pingok.vocational.domain.device.TblGantryInfoDtl;
import com.pingok.vocational.domain.device.vo.DtlEnum;

import java.util.List;

/**
 * 门架设备详情 服务层
 *
 * @author ruoyi
 */
public interface IGantryInfoDtlService {

    /**
     * 通过门架设备主键ID查询门架设备详情
     *
     * @param infoId 门架设备主键ID
     * @return 门架设备详情
     */
    List<TblGantryInfoDtl> selectGantryInfoDtl(Long infoId);

    /**
     * 新增设备详情
     *
     * @param dtlEnum 门架设备详情集合
     * @return 结果
     */
    public int insertGantryInfoDtl(DtlEnum dtlEnum);

    /**
     * 批量删除设备详情
     *
     * @param infoIds 需要删除的设备详情主键ID
     * @return 结果
     */
    public int deleteGantryInfoDtlByIds(Long[] infoIds);

    /**
     * 通过门架设备主键ID查询门架设备详情主键ID
     *
     * @param infoId 门架设备主键ID
     * @return 门架设备详情
     */
    Long[] selectDtlId(Long infoId);
}
