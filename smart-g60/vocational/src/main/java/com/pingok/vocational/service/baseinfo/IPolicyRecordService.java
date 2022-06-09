package com.pingok.vocational.service.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblPolicyRecord;

import java.util.List;
import java.util.Map;

/**
 * 收费政策记录 业务层
 *
 * @author ruoyi
 */
public interface IPolicyRecordService {

    /**
     * 根据Id查询收费政策记录
     *
     * @param Id Id
     * @return 收费政策记录
     */
    public TblPolicyRecord selectPolicyRecordById(Long Id);

    /**
     * 根据标题、使用状态模糊查询收费政策记录
     *
     * @param title 标题
     * @param status 使用状态
     * @return 收费政策记录
     */
    public List<Map> selectPolicyRecord(String title,Integer status);

    /**
     * 新增收费政策记录
     *
     * @param tblPolicyRecord 收费政策记录
     * @return 结果
     */
    public int insertPolicyRecord(TblPolicyRecord tblPolicyRecord);

    /**
     * 修改收费政策记录
     *
     * @param tblPolicyRecord 收费政策记录
     * @return 结果
     */
    public int updatePolicyRecord(TblPolicyRecord tblPolicyRecord);

    /**
     * 根据id,更改收费政策记录状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验标题名
     *
     * @param tblPolicyRecord 收费政策记录
     * @return 结果
     */
    public String checkTitleUnique(TblPolicyRecord tblPolicyRecord);
}
