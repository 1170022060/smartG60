package com.pingok.station.mapper.bulkList;

import com.pingok.station.domain.bulkList.BulkRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BulkRecordMapper {

    /**
     * 大件运输预约名单入库
     *
     * @param bulkRecord
     * @return 结果
     */
    public int insertBulk(BulkRecord bulkRecord);

    /**
     * 清除大件运输预约名单
     *
     * @return 结果
     */
    public void deleteAll();
}
