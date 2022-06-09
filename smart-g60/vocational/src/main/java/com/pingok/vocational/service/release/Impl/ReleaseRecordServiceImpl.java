package com.pingok.vocational.service.release.Impl;

import com.pingok.vocational.domain.release.TblReleaseRecord;
import com.pingok.vocational.mapper.release.TblReleaseRecordMapper;
import com.pingok.vocational.service.release.IReleaseRecordService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 信息发布记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class ReleaseRecordServiceImpl implements IReleaseRecordService {

    @Autowired
    private TblReleaseRecordMapper tblReleaseRecordMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void add(TblReleaseRecord tblReleaseRecord) {
        tblReleaseRecord.setPresetTime(DateUtils.getNowDate());
        tblReleaseRecord.setPresetUserId(SecurityUtils.getUserId());
        tblReleaseRecord.setId(remoteIdProducerService.nextId());
        tblReleaseRecordMapper.insert(tblReleaseRecord);
    }

    @Override
    public List<Map> selectReleaseRecord(Integer infoType, String deviceId, String deviceName, String pileNo, Date startTime, Date endTime) {
        return tblReleaseRecordMapper.selectReleaseRecord(infoType, deviceId, deviceName, pileNo, startTime, endTime);
    }
}
