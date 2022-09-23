package com.pingok.vocational.service.business;

import com.pingok.vocational.domain.business.TblOptInfo;
import com.pingok.vocational.domain.business.TblOptWorkInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作员工班信息 服务层
 *
 * @author ruoyi
 */
public interface IOptWorkInfoService {

    /**
     * 通过起止日期、收费站编码、员工姓名、工班号查询排班当岗信息
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param stationId 收费站编码
     * @param optName 员工姓名
     * @param shift 工班号
     * @param optId 员工工号
     * @return 排班当岗信息
     */
    List<Map> selectOptWorkInfo( Date startDate, Date endDate, String stationId, String optName, Integer shift ,Integer optId);

    /**
     * 新增工班信息
     *
     * @param tblOptWorkInfo 工班信息
     * @return 结果
     */
    public int insertOptWorkInfo(TblOptWorkInfo tblOptWorkInfo);

    /**
     * 修改工班信息
     *
     * @param id 工班信息id
     * @return 结果
     */
    public int updateOptWorkInfo(Long id);

    /**
     * 下发工班信息
     *
     * @return 结果
     */
    public void issueOptWorkInfo();
}
