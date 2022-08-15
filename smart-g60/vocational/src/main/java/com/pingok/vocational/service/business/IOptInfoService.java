package com.pingok.vocational.service.business;

import com.pingok.vocational.domain.business.TblOptInfo;

import java.util.List;
import java.util.Map;

/**
 * 员工信息 业务层
 *
 * @author ruoyi
 */
public interface IOptInfoService {

    /**
     * 根据Id查询员工信息
     *
     * @param Id Id
     * @return 场地信息
     */
    public TblOptInfo selectOptInfoById(Long Id);

    /**
     * 根据所属站、所属中心、员工姓名、员工工号、使用状态查询员工信息
     *
     * @param belongStation 所属站
     * @param belongCenter 所属中心
     * @param optName 员工姓名
     * @param optId 员工工号
     * @param status 使用状态
     * @return 场地信息
     */
    public List<Map> selectOptInfo(String belongStation,String belongCenter, String optName,Integer optId, Integer status);


    /**
     * 新增员工信息
     *
     * @param tblOptInfo 员工信息
     * @return 结果
     */
    public int insertOptInfo(TblOptInfo tblOptInfo);

    /**
     * 修改场地信息
     *
     * @param tblOptInfo 场地信息
     * @return 结果
     */
    public int updateOptInfo(TblOptInfo tblOptInfo);

    /**
     * 根据id,更改场地信息状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验员工工号
     *
     * @param tblOptInfo 员工信息
     * @return 结果
     */
    public String checkOptIdUnique(TblOptInfo tblOptInfo);
}
