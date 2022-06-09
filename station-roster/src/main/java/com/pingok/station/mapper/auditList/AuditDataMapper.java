package com.pingok.station.mapper.auditList;


import com.pingok.station.domain.auditList.AuditData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditDataMapper {

    /**
     * 稽核黑名单入库
     *
     * @param auditData
     * @return 结果
     */
    public int insertAuditData(AuditData auditData);

    /**
     * 清除稽核黑名单
     *
     * @return 结果
     */
    public void deleteAll();

    /**
     * 更新稽核黑名单
     *
     * @return 结果
     */
    public int updateAuditData(AuditData auditData);

    /**
     * 查询稽核黑名单
     *
     * @return 结果
     * @param vehicleId
     */
    public int selectVehicleId(String vehicleId);

    /**
     * 删除稽核黑名单
     *
     * @return 结果
     * @param vehicleId
     */
    public void deleteVehicleId(String vehicleId);
}
