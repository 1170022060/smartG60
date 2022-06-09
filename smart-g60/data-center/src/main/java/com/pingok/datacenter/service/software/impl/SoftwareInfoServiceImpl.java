package com.pingok.datacenter.service.software.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.datacenter.domain.software.TblSoftwareDownloadStatus;
import com.pingok.datacenter.domain.software.TblSoftwareHeartbeat;
import com.pingok.datacenter.domain.software.TblSoftwareInfo;
import com.pingok.datacenter.domain.software.TblSoftwareUploadStatus;
import com.pingok.datacenter.mapper.software.TblSoftwareDownloadStatusMapper;
import com.pingok.datacenter.mapper.software.TblSoftwareHeartbeatMapper;
import com.pingok.datacenter.mapper.software.TblSoftwareInfoMapper;
import com.pingok.datacenter.mapper.software.TblSoftwareUploadStatusMapper;
import com.pingok.datacenter.service.software.ISoftwareInfoService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 软件信息 服务层处理
 *
 * @author qiumin
 */
@Service
public class SoftwareInfoServiceImpl implements ISoftwareInfoService {

    @Autowired
    private TblSoftwareInfoMapper tblSoftwareInfoMapper;
    @Autowired
    private TblSoftwareHeartbeatMapper tblSoftwareHeartbeatMapper;
    @Autowired
    private TblSoftwareDownloadStatusMapper tblSoftwareDownloadStatusMapper;
    @Autowired
    private TblSoftwareUploadStatusMapper tblSoftwareUploadStatusMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void heartbeat(TblSoftwareHeartbeat tblSoftwareHeartbeat) {
        Date time = DateUtils.getNowDate();
        Example example = new Example(TblSoftwareInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("num", tblSoftwareHeartbeat.getNum());
        TblSoftwareInfo tblSoftwareInfo = tblSoftwareInfoMapper.selectOneByExample(example);
        if (tblSoftwareInfo != null) {
            BeanUtils.copyNotNullProperties(tblSoftwareHeartbeat, tblSoftwareInfo);
            tblSoftwareInfo.setTime(time);
            if (tblSoftwareHeartbeat.getDownloadFlag() == 1 && tblSoftwareHeartbeat.getDownloadStatus() != null) {
                tblSoftwareInfo.setDownloadStatus(JSON.toJSONString(tblSoftwareHeartbeat.getDownloadStatus()));
            }
            if (tblSoftwareHeartbeat.getUploadFlag() == 1 && tblSoftwareHeartbeat.getUploadStatus() != null) {
                tblSoftwareInfo.setUploadStatus(JSON.toJSONString(tblSoftwareHeartbeat.getUploadStatus()));
            }


            TblSoftwareHeartbeat heartbeat = new TblSoftwareHeartbeat();
            BeanUtils.copyNotNullProperties(tblSoftwareHeartbeat, heartbeat);
            heartbeat.setId(remoteIdProducerService.nextId());
            heartbeat.setTime(time);
            heartbeat.setSoftwareId(tblSoftwareInfo.getId());
            tblSoftwareHeartbeatMapper.insert(heartbeat);

            int status = 1;
            String statusCode = "正常";
            if (tblSoftwareHeartbeat.getDownloadFlag() == 1) {
                List<TblSoftwareDownloadStatus> downloadStatusList = tblSoftwareHeartbeat.getDownloadStatus();
                for (TblSoftwareDownloadStatus d : downloadStatusList) {
                    d.setId(remoteIdProducerService.nextId());
                    d.setHeartbeatId(heartbeat.getId());
                    tblSoftwareDownloadStatusMapper.insert(d);
                    if (d.getStatus() == 0) {
                        status = 0;
                        statusCode = d.getStatusCode();
                    }
                }
            }

            if (tblSoftwareHeartbeat.getUploadFlag() == 1) {
                List<TblSoftwareUploadStatus> uploadStatusesList = tblSoftwareHeartbeat.getUploadStatus();
                for (TblSoftwareUploadStatus u : uploadStatusesList) {
                    u.setId(remoteIdProducerService.nextId());
                    u.setHeartbeatId(heartbeat.getId());
                    tblSoftwareUploadStatusMapper.insert(u);
                    if (u.getStatus() == 0) {
                        status = 0;
                        statusCode = u.getStatusCode();
                    }
                }
            }
            tblSoftwareInfo.setStatus(status);
            tblSoftwareInfo.setStatusCode(statusCode);
            tblSoftwareInfoMapper.updateByPrimaryKey(tblSoftwareInfo);
        } else {
            throw new ServiceException("软件编号错误或未纳入监控范围");
        }
    }
}
