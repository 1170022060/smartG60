package com.pingok.vocational.service.roster;

import com.pingok.vocational.domain.roster.TblTractorListRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 牵引车名单 业务层
 *
 * @author ruoyi
 */
public interface ITractorListRecordService {

    /**
     * 根据起止时间查询牵引车辆变动信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<TblTractorListRecord> selectByTime(Date startTime,Date endTime);

    /**
     * 根据Id查询牵引车信息
     *
     * @param Id Id
     * @return 牵引车信息
     */
    public TblTractorListRecord selectTractorListById(Long Id);

    /**
     * 根据车牌、使用状态查询牵引车信息
     *
     * @param vehPlate 车牌
     * @param status 使用状态
     * @return 牵引车信息
     */
    public List<Map> selectTractorList(String vehPlate,Integer status);


    /**
     * 新增牵引车信息
     *
     * @param tblTractorListRecord 牵引车信息
     * @return 结果
     */
    public int insertTractorList(TblTractorListRecord tblTractorListRecord);

    /**
     * 修改牵引车信息
     *
     * @param tblTractorListRecord 牵引车信息
     * @return 结果
     */
    public int updateTractorList(TblTractorListRecord tblTractorListRecord);

    /**
     * 根据id,更改牵引车状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验车牌
     *
     * @param tblTractorListRecord 牵引车信息
     * @return 结果
     */
    public String checkVehPlateUnique(TblTractorListRecord tblTractorListRecord);
}
