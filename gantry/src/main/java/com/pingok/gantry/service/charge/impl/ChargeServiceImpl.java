package com.pingok.gantry.service.charge.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.pingok.gantry.domain.entity.charge.*;
import com.pingok.gantry.mapper.charge.*;
import com.pingok.gantry.service.charge.IChargeService;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
@DS(value = "master")
public class ChargeServiceImpl implements IChargeService {

    @Autowired
    private TblGantryBaseinfoMapper tblGantryBaseinfoMapper;
    @Autowired
    private TblGantryRsuBaseinfoMapper tblGantryRsuBaseinfoMapper;
    @Autowired
    private TblGantryVplrBaseinfoMapper tblGantryVplrBaseinfoMapper;
    @Autowired
    private TblGantryMonitorStatusMapper tblGantryMonitorStatusMapper;
    @Autowired
    private TblGantryRsuMonitorMapper tblGantryRsuMonitorMapper;
    @Autowired
    private TblGantryVplrMonitorMapper tblGantryVplrMonitorMapper;
    @Autowired
    private TblGantryTransactionMapper tblGantryTransactionMapper;
    @Autowired
    private TblGantryTravelimageMapper tblGantryTravelimageMapper;
    @Autowired
    private TblGantryStateCollectMapper tblGantryStateCollectMapper;


    @Override
    public List<TblGantryTransaction> findTransactionUploadError(List<String> gantryId) {
        return tblGantryTransactionMapper.findTransactionUploadError(gantryId);
    }

    @Override
    public List<TblGantryTravelimage> findTravelImageUploadError(List<String> gantryId) {
        return tblGantryTravelimageMapper.findTravelimageUploadError(gantryId);
    }

    @Override
    public TblGantryStateCollect findStateByGantryId(String gantryId) {
        return tblGantryStateCollectMapper.findStateByGantryId(gantryId);
    }

    @Override
    public void saveAllGantryBaseinfo(List<TblGantryBaseinfo> gantryBaseInfos) {
        for(TblGantryBaseinfo info : gantryBaseInfos) {
            tblGantryBaseinfoMapper.updateByPrimaryKey(info);
        }
    }

    @Override
    public void saveAllRsuBaseinfo(List<TblGantryRsuBaseinfo> gantryRsuBaseinfos) {
        for(TblGantryRsuBaseinfo info : gantryRsuBaseinfos) {
            tblGantryRsuBaseinfoMapper.updateByPrimaryKey(info);
        }
    }

    @Override
    public void saveAllVplrBaseinfo(List<TblGantryVplrBaseinfo> gantryVplrBaseinfos) {
        for(TblGantryVplrBaseinfo info : gantryVplrBaseinfos) {
            tblGantryVplrBaseinfoMapper.updateByPrimaryKey(info);
        }
    }

    @Override
    public void saveAllGantryMonitor(List<TblGantryMonitorStatus> gantryMonitorStatuses) {
        for (TblGantryMonitorStatus g : gantryMonitorStatuses) {
            g.setHeartbeatStatus(1);
            g.setHeartbeatTime(new Date());
        }
        for(TblGantryMonitorStatus info : gantryMonitorStatuses) {
            tblGantryMonitorStatusMapper.updateByPrimaryKey(info);
        }

        List<TblGantryStateCollect> gantryStateCollects;
        String[] softState;
        for (TblGantryMonitorStatus g : gantryMonitorStatuses) {
            gantryStateCollects = tblGantryStateCollectMapper.findByGantryId(g.getGantryId());
            if (gantryStateCollects != null && gantryStateCollects.size() > 0) {
                for (TblGantryStateCollect s : gantryStateCollects) {
                    s.setDate(g.getStateTime());
                    switch (g.getFrontSysFlag()) {
                        case 1:
                            switch (g.getComputerOrder()) {
                                case 1: {
                                    if (g.getFrontSoftState() != null) {
                                        softState = g.getFrontSoftState().split("\\|");
                                        if (softState != null && softState.length > 0) {
                                            s.setFrontSoftStateMasterIpc("1");
                                            for (int i = 0; i < softState.length; i++) {
                                                if (softState[i].equals("1")) {
                                                    s.setFrontSoftStateMasterIpc("-1");
                                                    break;
                                                }
                                            }
                                        } else {
                                            s.setFrontSoftStateMasterIpc("0");
                                        }
                                    } else {
                                        s.setFrontSoftStateMasterIpc("0");
                                    }
                                    if (g.getFrontBeidouState() != null) {
                                        switch (g.getFrontBeidouState()) {
                                            case 0:
                                                s.setFrontBeidouStateMasterIpc("-1");
                                                break;
                                            case 1:
                                                s.setFrontBeidouStateMasterIpc("1");
                                                break;
                                            case 2:
                                                s.setFrontBeidouStateMasterIpc("0");
                                                break;
                                        }
                                    } else {
                                        s.setFrontBeidouStateMasterIpc("0");
                                    }
                                    break;
                                }
                                case 2: {
                                    if (g.getFrontSoftState() != null) {
                                        softState = g.getFrontSoftState().split("\\|");
                                        if (softState != null && softState.length > 0) {
                                            s.setFrontSoftStateStandbyIpc("1");
                                            for (int i = 0; i < softState.length; i++) {
                                                if (softState[i].equals("1")) {
                                                    s.setFrontSoftStateStandbyIpc("-1");
                                                    break;
                                                }
                                            }
                                        } else {
                                            s.setFrontSoftStateStandbyIpc("0");
                                        }
                                    } else {
                                        s.setFrontSoftStateStandbyIpc("0");
                                    }

                                    if (g.getFrontBeidouState() != null) {
                                        switch (g.getFrontBeidouState()) {
                                            case 0:
                                                s.setFrontBeidouStateStandbyIpc("-1");
                                                break;
                                            case 1:
                                                s.setFrontBeidouStateStandbyIpc("1");
                                                break;
                                            case 2:
                                                s.setFrontBeidouStateStandbyIpc("0");
                                                break;
                                        }
                                    } else {
                                        s.setFrontBeidouStateStandbyIpc("0");
                                    }
                                    break;
                                }
                            }
                            break;
                        case 2:
                            switch (g.getComputerOrder()) {
                                case 1: {
                                    if (g.getFrontSoftState() != null) {
                                        softState = g.getFrontSoftState().split("\\|");
                                        if (softState != null && softState.length > 0) {
                                            s.setFrontSoftStateMasterServer("1");
                                            for (int i = 0; i < softState.length; i++) {
                                                if (softState[i].equals("1")) {
                                                    s.setFrontSoftStateMasterServer("-1");
                                                    break;
                                                }
                                            }
                                        } else {
                                            s.setFrontSoftStateMasterServer("0");
                                        }
                                    } else {
                                        s.setFrontSoftStateMasterServer("0");
                                    }
                                    if (g.getFrontBeidouState() != null) {
                                        switch (g.getFrontBeidouState()) {
                                            case 0:
                                                s.setFrontBeidouStateMasterServer("-1");
                                                break;
                                            case 1:
                                                s.setFrontBeidouStateMasterServer("1");
                                                break;
                                            case 2:
                                                s.setFrontBeidouStateMasterServer("0");
                                                break;
                                        }
                                    } else {
                                        s.setFrontBeidouStateMasterServer("0");
                                    }
                                    s.setFrontDiskDataLeftSize(g.getFrontDiskDataLeftSize());
                                    s.setFrontDiskRunLeftSize(g.getFrontDiskRunLeftSize());
                                    break;
                                }
                                case 2: {
                                    if (g.getFrontSoftState() != null) {
                                        softState = g.getFrontSoftState().split("\\|");
                                        if (softState != null && softState.length > 0) {
                                            s.setFrontSoftStateStandbyServer("1");
                                            for (int i = 0; i < softState.length; i++) {
                                                if (softState[i].equals("1")) {
                                                    s.setFrontSoftStateStandbyServer("-1");
                                                    break;
                                                }
                                            }
                                        } else {
                                            s.setFrontSoftStateStandbyServer("0");
                                        }
                                    } else {
                                        s.setFrontSoftStateStandbyServer("0");
                                    }

                                    if (g.getFrontBeidouState() != null) {
                                        switch (g.getFrontBeidouState()) {
                                            case 0:
                                                s.setFrontBeidouStateStandbyServer("-1");
                                                break;
                                            case 1:
                                                s.setFrontBeidouStateStandbyServer("1");
                                                break;
                                            case 2:
                                                s.setFrontBeidouStateStandbyServer("0");
                                                break;
                                        }
                                    } else {
                                        s.setFrontBeidouStateStandbyServer("0");
                                    }
                                    s.setFrontDiskDataLeftSizeBack(g.getFrontDiskDataLeftSize());
                                    s.setFrontDiskRunLeftSizeBack(g.getFrontDiskRunLeftSize());
                                    break;
                                }
                            }
                            break;
                    }
                }
                for (TblGantryStateCollect c : gantryStateCollects) {
                    c.setStatus("1");
                    if ((c.getFrontBeidouStateStandbyIpc().equals("0") || c.getFrontBeidouStateStandbyIpc().equals("-1")) || (c.getFrontBeidouStateMasterIpc().equals("0") || c.getFrontBeidouStateMasterIpc().equals("-1")) || (c.getFrontBeidouStateMasterServer().equals("0") || c.getFrontBeidouStateMasterServer().equals("-1")) || (c.getFrontBeidouStateStandbyServer().equals("0") || c.getFrontBeidouStateStandbyServer().equals("-1")) || (c.getFrontSoftStateMasterIpc().equals("0") || c.getFrontSoftStateMasterIpc().equals("-1")) || (c.getFrontSoftStateMasterServer().equals("0") || c.getFrontSoftStateMasterServer().equals("-1")) || (c.getFrontSoftStateStandbyIpc().equals("0") || c.getFrontSoftStateStandbyIpc().equals("-1")) || (c.getFrontSoftStateStandbyServer().equals("0") || c.getFrontSoftStateStandbyServer().equals("-1"))) {
                        c.setStatus("-1");
                    }
                }
                for(TblGantryStateCollect info : gantryStateCollects) {
                    tblGantryStateCollectMapper.updateByPrimaryKey(info);
                }
            }
        }
    }

    @Override
    public void saveAllRsuMonitor(List<TblGantryRsuMonitor> gantryRsuMonitors) {
        for (TblGantryRsuMonitor r : gantryRsuMonitors) {
            r.setHeartbeatStatus(1);
            r.setHeartbeatTime(new Date());
        }
        for(TblGantryRsuMonitor info : gantryRsuMonitors) {
            tblGantryRsuMonitorMapper.updateByPrimaryKey(info);
        }
        String gantryId = "";
        String status = "1";
        JSONObject rsu;
        JSONArray rsus = new JSONArray();
        TblGantryRsuMonitor r;
        List<TblGantryStateCollect> gantryStateCollects;
        for (int i = 0; i < gantryRsuMonitors.size(); i++) {
            r = gantryRsuMonitors.get(i);
            if (!r.getGantryId().equals(gantryId)) {
                if (!StringUtils.isEmpty(gantryId)) {
                    gantryStateCollects = tblGantryStateCollectMapper.findByGantryId(gantryId);
                    if (gantryStateCollects != null && gantryStateCollects.size() > 0) {
                        for (TblGantryStateCollect c : gantryStateCollects) {
                            c.setStatus(status);
                            c.setRsuState(status);
                            c.setRsuStateDtl(rsus.toJSONString());
                        }
                        for(TblGantryStateCollect info : gantryStateCollects) {
                            tblGantryStateCollectMapper.updateByPrimaryKey(info);
                        }
                    }
                }
                status = "1";
                gantryId = r.getGantryId();
                rsus = new JSONArray();
            }
            if (r.getRSUID() != null) {
                rsu = new JSONObject();
                rsu.put("RSUID", r.getRSUID());
                rsu.put("Status", r.getStatus());
                rsu.put("ControlNetWork", r.getControlNetWork());
                rsu.put("PSAMInfoList", r.getPSAMInfoList());
                rsu.put("AntennalInfoList", r.getAntennalInfoList());
                rsus.add(rsu);
            }
//            if (r.getStatus() == null || r.getControlNetWork() == null || !r.getStatus().equals("00") || r.getControlNetWork() != 1) {
            if (r.getStatus() == null || r.getControlNetWork() == null) {//临时使用，待确认RSU状态描述
                status = "-1";
            }
            if (gantryRsuMonitors.size() == i + 1) {
                if (!StringUtils.isEmpty(gantryId)) {
                    gantryStateCollects = tblGantryStateCollectMapper.findByGantryId(gantryId);
                    if (gantryStateCollects != null && gantryStateCollects.size() > 0) {
                        for (TblGantryStateCollect c : gantryStateCollects) {
                            c.setStatus(status);
                            c.setRsuState(status);
                            c.setRsuStateDtl(rsus.toJSONString());
                        }
                        for(TblGantryStateCollect info : gantryStateCollects) {
                            tblGantryStateCollectMapper.updateByPrimaryKey(info);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void saveAllVplrMonitor(List<TblGantryVplrMonitor> gantryVplrMonitors) {
        for (TblGantryVplrMonitor g : gantryVplrMonitors) {
            g.setHeartbeatStatus(1);
            g.setHeartbeatTime(new Date());
        }
        for(TblGantryVplrMonitor info : gantryVplrMonitors) {
            tblGantryVplrMonitorMapper.updateByPrimaryKey(info);
        }

        String gantryId = "";
        String status = "1";
        JSONObject vplr;
        JSONArray vplrs = new JSONArray();
        TblGantryVplrMonitor r;
        List<TblGantryStateCollect> gantryStateCollects;
        for (int i = 0; i < gantryVplrMonitors.size(); i++) {
            r = gantryVplrMonitors.get(i);
            if (!r.getGantryId().equals(gantryId)) {
                if (!StringUtils.isEmpty(gantryId)) {
                    gantryStateCollects = tblGantryStateCollectMapper.findByGantryId(gantryId);
                    if (gantryStateCollects != null && gantryStateCollects.size() > 0) {
                        for (TblGantryStateCollect c : gantryStateCollects) {
                            c.setStatus(status);
                            c.setVplrState(status);
                            c.setVplrStateDtl(vplrs.toJSONString());
                        }
                        for(TblGantryStateCollect info : gantryStateCollects) {
                            tblGantryStateCollectMapper.updateByPrimaryKey(info);
                        }
                    }
                }
                status = "1";
                gantryId = r.getGantryId();
                vplrs = new JSONArray();
            }
            if (r.getLaneNum() != null) {
                vplr = new JSONObject();
                vplr.put("LaneNum", r.getLaneNum());
                vplr.put("ConnectStatus", r.getConnectStatus() != null ? r.getConnectStatus() : null);
                vplr.put("WorkStatus", r.getWorkStatus() != null ? r.getWorkStatus() : null);
                vplr.put("LightWorkStatus", r.getLightWorkStatus() != null ? r.getLightWorkStatus() : null);
                vplr.put("StatusCode", r.getStatusCode() != null ? r.getStatusCode() : null);
                vplrs.add(vplr);
            }
            if (r.getConnectStatus() == null || r.getWorkStatus() == null || r.getLightWorkStatus() == null || r.getStatusCode() == null || !r.getConnectStatus().equals("1") || !r.getWorkStatus().equals("1") || !r.getLightWorkStatus().equals("1") || !r.getStatusCode().equals("0")) {
                status = "-1";
            }
            if (gantryVplrMonitors.size() == i + 1) {
                if (!StringUtils.isEmpty(gantryId)) {
                    gantryStateCollects = tblGantryStateCollectMapper.findByGantryId(gantryId);
                    if (gantryStateCollects != null && gantryStateCollects.size() > 0) {
                        for (TblGantryStateCollect c : gantryStateCollects) {
                            c.setStatus(status);
                            c.setVplrState(status);
                            c.setVplrStateDtl(vplrs.toJSONString());
                        }
                        for(TblGantryStateCollect info : gantryStateCollects) {
                            tblGantryStateCollectMapper.updateByPrimaryKey(info);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveAllTransaction(List<TblGantryTransaction> gantryTransactions) {
        for(TblGantryTransaction info : gantryTransactions) {
            tblGantryTransactionMapper.updateByPrimaryKey(info);
        }
        TblGantryStateCollect tblGantryStateCollect;
        for (TblGantryTransaction t : gantryTransactions) {
            if (t.getUploadFlag() == 0 || t.getUploadFlag() == 2 || t.getMuploadflag() == 0 || t.getMuploadflag() == 2) {
                tblGantryStateCollect = tblGantryStateCollectMapper.findStateByGantryId(t.getGantryId());
                if (tblGantryStateCollect != null) {
                    tblGantryStateCollect.setTransactionNumber(tblGantryStateCollect.getTransactionNumber() + 1);
                }
            }
        }
    }

    @Override
    public void saveAllTravelimage(List<TblGantryTravelimage> gantryTravelImages) {
        for(TblGantryTravelimage info : gantryTravelImages) {
            tblGantryTravelimageMapper.updateByPrimaryKey(info);
        }
        TblGantryStateCollect tblGantryStateCollect;
        for (TblGantryTravelimage t : gantryTravelImages) {
            if (t.getUploadFlag() == 0 || t.getUploadFlag() == 2 || t.getMUploadFlag() == 0 || t.getMUploadFlag() == 2) {
                tblGantryStateCollect = tblGantryStateCollectMapper.findStateByGantryId(t.getGantryId());
                if (tblGantryStateCollect != null) {
                    tblGantryStateCollect.setTravelimageNumber(tblGantryStateCollect.getTravelimageNumber() + 1);
                }
            }
        }
    }

    @Override
    public void updateAllTransaction(List<TblGantryTransaction> gantryTransactions) {
        for(TblGantryTransaction info : gantryTransactions) {
            tblGantryTransactionMapper.updateByPrimaryKey(info);
        }
        TblGantryStateCollect tblGantryStateCollect;
        for (TblGantryTransaction t : gantryTransactions) {
            if (t.getUploadFlag() == 0 || t.getUploadFlag() == 2 || t.getMuploadflag() == 0 || t.getMuploadflag() == 2) {
                tblGantryStateCollect = tblGantryStateCollectMapper.findStateByGantryId(t.getGantryId());
                if (tblGantryStateCollect != null) {
                    if (tblGantryStateCollect.getTransactionNumber() != 0) {
                        tblGantryStateCollect.setTransactionNumber(tblGantryStateCollect.getTransactionNumber() - 1);
                    }
                }
            }
        }
    }

    @Override
    public void updateAllTravelimage(List<TblGantryTravelimage> gantryTravelImages) {
        for(TblGantryTravelimage info : gantryTravelImages) {
            tblGantryTravelimageMapper.updateByPrimaryKey(info);
        }
        TblGantryStateCollect tblGantryStateCollect;
        for (TblGantryTravelimage t : gantryTravelImages) {
            if (t.getUploadFlag() == 1 && t.getMUploadFlag() == 1) {
                tblGantryStateCollect = tblGantryStateCollectMapper.findStateByGantryId(t.getGantryId());
                if (tblGantryStateCollect != null) {
                    if (tblGantryStateCollect.getTravelimageNumber() != 0) {
                        tblGantryStateCollect.setTravelimageNumber(tblGantryStateCollect.getTravelimageNumber() - 1);
                    }
                }
            }
        }
    }
}
