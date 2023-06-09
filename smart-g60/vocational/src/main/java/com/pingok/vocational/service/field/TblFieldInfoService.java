package com.pingok.vocational.service.field;

import com.pingok.vocational.domain.device.vo.TreeSelect;
import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.domain.field.vo.FieldVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 场地信息 业务层
 *
 * @author ruoyi
 */
public interface TblFieldInfoService {

    /**
     * 根据Id查询场地信息
     *
     * @param Id Id
     * @return 场地信息
     */
    public TblFieldInfo selectFieldInfoById(Long Id);

    /**
     * 根据所属站、所属路段、场地名称、类型、使用状态查询场地信息
     *
     * @param stationBelong 所属站
     * @param roadBelong 所属路段
     * @param fieldName 场地名称
     * @param type 类型
     * @param status 使用状态
     * @return 场地信息
     */
    public List<Map> selectFieldInfo(String stationBelong, String roadBelong, String fieldName, Integer type,Integer status);


    /**
     * 新增场地信息
     *
     * @param tblFieldInfo 场地信息
     * @return 结果
     */
    public int insertFieldInfo(TblFieldInfo tblFieldInfo);

    /**
     * 修改场地信息
     *
     * @param tblFieldInfo 场地信息
     * @return 结果
     */
    public int updateFieldInfo(TblFieldInfo tblFieldInfo);

    /**
     * 根据id,更改场地信息状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验场地名
     *
     * @param tblFieldInfo 场地信息
     * @return 结果
     */
    public String checkFieldNameUnique(TblFieldInfo tblFieldInfo);

    /**
     * 返回ID和场地信息
     *
     * @return 场地信息
     */
    public List<FieldVo> selectFieldTypeName();

    List<Map> selectServiceName();

    /**
     * 查询所有场地管理信息
     * @return
     */
    List<TblFieldInfo> selectAll();

    /**
     * 构建场地信息树形结构
     * @param fieldInfo
     * @return
     */
    List<TblFieldInfo> buildTreeMenu(List<TblFieldInfo> fieldInfo);

    /**
     * 得到场地信息下拉数据
     * @param menu
     * @return
     */
    List<TreeSelect> fieldTreeMenu(List<TblFieldInfo> menu);

    /**
     * 获取服务区场地
     * @return
     */
    List<Map> selectServiceField();

    /**
     * 获取服务区子集场地
     * @param id
     * @return
     */
    List<Map> getChildrenField(Long id);
}
