package com.pingok.station.mapper.emergList;

import com.pingok.station.domain.auditList.AuditData;
import com.pingok.station.domain.emergList.EmgData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmgDataMapper {
    /**
     * 抢险救灾名单入库
     *
     * @param emgData
     * @return 结果
     */
    public int insertEmgData(EmgData emgData);

    /**
     * 清除抢险救灾名单
     *
     * @return 结果
     */
    public void deleteAll();

    /**
     * 更新抢险救灾名单
     *
     * @return 结果
     */
    public int updateEmgData(EmgData EmgData);

    /**
     * 查询抢险救灾名单
     *
     * @return 结果
     * @param id
     */
    public int selectId(String id);

    /**
     * 删除抢险救灾名单
     *
     * @return 结果
     * @param id
     */
    public void deleteId(String id);
}
