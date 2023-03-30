package com.pingok.vocational.service.release;

import com.pingok.vocational.domain.release.TblReleaseRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 信息发布记录 业务层
 *
 * @author ruoyi
 */
public interface IReleaseRecordService {


    /**
     * 新增信息发布记录
     * @param tblReleaseRecord
     */
    void add(TblReleaseRecord tblReleaseRecord);
    /**
     * 根据信息类型、设备编码、设备名称、桩号、开始时间、结束时间查询信息发布记录信息
     *
     * @param infoType 信息类型
     * @param deviceId 设备编码
     * @param deviceName 设备名称
     * @param pileNo 桩号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 信息发布预设信息
     */
    public List<Map> selectReleaseRecord(String deviceId, String deviceName, String pileNo, String startTime, String endTime);

}
