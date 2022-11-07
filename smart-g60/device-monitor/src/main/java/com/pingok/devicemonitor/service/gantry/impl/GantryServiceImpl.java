package com.pingok.devicemonitor.service.gantry.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.config.GantryConfig;
import com.pingok.devicemonitor.domain.gantry.*;
import com.pingok.devicemonitor.mapper.gantry.*;
import com.pingok.devicemonitor.service.gantry.IGantryService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Service
public class GantryServiceImpl implements IGantryService {
    @Autowired
    private TblGantryStatusMapper tblGantryStatusMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblGantryStatusDtlMapper tblGantryStatusDtlMapper;
    @Autowired
    private TblGantryStatusLogMapper tblGantryStatusLogMapper;
    @Autowired
    private TblGantryStatusDtlLogMapper tblGantryStatusDtlLogMapper;

    @Autowired
    private TblGantryEventReleaseMapper tblGantryEventReleaseMapper;

    @Override
    public void eventProcessing(TblGantryEventRelease tblGantryEventRelease) {
        if (tblGantryEventRelease.getGantryIds() != null && tblGantryEventRelease.getGantryIds().size() > 0) {
            TblGantryEventRelease info;
            JSONObject param;
            List<String> gantryIds = tblGantryEventRelease.getGantryIds();
            for (String gantryId : gantryIds) {
                info = new TblGantryEventRelease();
                BeanUtils.copyNotNullProperties(tblGantryEventRelease, info);
                info.setGantryId(gantryId);
                info.setId(remoteIdProducerService.nextId());
                info.setCreateTime(DateUtils.getNowDate());
                info.setCreateUserId(SecurityUtils.getUserId());
                info.setStatus(0);
                tblGantryEventReleaseMapper.insert(info);

                param = new JSONObject();
                param.put("gantryId", gantryId);
//                param.put("OBUDelay", tblGantryEventRelease.getObuDelay() != null ? tblGantryEventRelease.getObuDelay() : 0);
                param.put("stakeNum", tblGantryEventRelease.getStakeNum());
                param.put("direction", tblGantryEventRelease.getDirection());
                param.put("broadcastRange", tblGantryEventRelease.getBroadcastRange() != null ? tblGantryEventRelease.getBroadcastRange() : 0);
                param.put("eventType", tblGantryEventRelease.getEventType());
                param.put("eventId", tblGantryEventRelease.getEventId());
                param.put("eventPosition", tblGantryEventRelease.getEventPosition() != null ? tblGantryEventRelease.getEventPosition() : 0);
                param.put("eventDiscount", tblGantryEventRelease.getEventDiscount() != null ? tblGantryEventRelease.getEventDiscount()*10 : 0);
                param.put("eventInfo", tblGantryEventRelease.getEventInfo());
                param.put("reportBeginTime", DateUtils.dateTime(tblGantryEventRelease.getReportBeginTime(), DateUtils.YYYY_MM_DD)+"T"+DateUtils.dateTime(tblGantryEventRelease.getReportBeginTime(), DateUtils.HH_MM_SS));
                param.put("reportEndTime", DateUtils.dateTime(tblGantryEventRelease.getReportEndTime(), DateUtils.YYYY_MM_DD)+"T"+DateUtils.dateTime(tblGantryEventRelease.getReportEndTime(), DateUtils.HH_MM_SS));
                param.put("cryptoGraphicDigest", tblGantryEventRelease.getStakeNum());

                String res = HttpUtil.post(GantryConfig.HOST + "/preservice/eventprocessing/notify/event", param.toJSONString());
                JSONObject resj = JSONObject.parseObject(res);
                if(!resj.containsKey("subCode")){
                    throw new ServiceException(resj.getString("msg"));
                }
                if(!resj.getString("subCode").equals("200")){
                    throw new ServiceException(resj.getString("info"));
                }
                info.setStatus(1);
                tblGantryEventReleaseMapper.updateByPrimaryKey(info);
            }
        } else {
            throw new ServiceException("门架编号不能为空");
        }
    }

    @Override
    @Transactional
    public void updateStatus(TblGantryStatus gantryStatus) {

        TblGantryStatusLog gantryStatusLog = new TblGantryStatusLog();
        BeanUtils.copyNotNullProperties(gantryStatus, gantryStatusLog);
        gantryStatusLog.setId(remoteIdProducerService.nextId());
        tblGantryStatusLogMapper.insert(gantryStatusLog);
        if (gantryStatus.getGantryStatusDtls() != null && gantryStatus.getGantryStatusDtls().size() > 0) {
            List<TblGantryStatusDtl> gantryStatusDtls = gantryStatus.getGantryStatusDtls();
            TblGantryStatusDtlLog gantryStatusDtlLog;
            for (TblGantryStatusDtl dtl : gantryStatusDtls) {
                gantryStatusDtlLog = new TblGantryStatusDtlLog();
                BeanUtils.copyNotNullProperties(dtl, gantryStatusDtlLog);
                gantryStatusDtlLog.setId(remoteIdProducerService.nextId());
                gantryStatusDtlLog.setStatusId(gantryStatusLog.getId());
                tblGantryStatusDtlLogMapper.insert(gantryStatusDtlLog);
            }
        }

        Example example = new Example(TblGantryStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", gantryStatus.getDeviceId());

        List<TblGantryStatusDtl> gantryStatusDtls;
        TblGantryStatus gs = tblGantryStatusMapper.selectOneByExample(example);
        if (gs == null) {
            gs = new TblGantryStatus();
            gs.setId(remoteIdProducerService.nextId());
            BeanUtils.copyNotNullProperties(gantryStatus, gs);
            tblGantryStatusMapper.insert(gs);

            if (gantryStatus.getGantryStatusDtls() != null && gantryStatus.getGantryStatusDtls().size() > 0) {
                gantryStatusDtls = gantryStatus.getGantryStatusDtls();
                for (TblGantryStatusDtl dtl : gantryStatusDtls) {
                    dtl.setId(remoteIdProducerService.nextId());
                    dtl.setStatusId(gs.getId());
                    tblGantryStatusDtlMapper.insert(dtl);
                }
            }
        } else {
            BeanUtils.copyNotNullProperties(gantryStatus, gs);
            tblGantryStatusMapper.updateByPrimaryKey(gs);

            example = new Example(TblGantryStatusDtl.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("statusId", gs.getId());
            tblGantryStatusDtlMapper.deleteByExample(example);

            if (gantryStatus.getGantryStatusDtls() != null && gantryStatus.getGantryStatusDtls().size() > 0) {
                gantryStatusDtls = gantryStatus.getGantryStatusDtls();
                for (TblGantryStatusDtl dtl : gantryStatusDtls) {
                    dtl.setId(remoteIdProducerService.nextId());
                    dtl.setStatusId(gs.getId());
                    tblGantryStatusDtlMapper.insert(dtl);
                }
            }
        }
    }
}
