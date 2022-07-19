package com.pingok.gantry.service.charge;

import com.pingok.gantry.domain.entity.charge.*;

import java.util.List;

public interface IChargeService {
    List<TblGantryTransaction> findTransactionUploadError(List<String> gantryId);
    List<TblGantryTravelimage> findTravelImageUploadError(List<String> gantryId);
    TblGantryStateCollect findStateByGantryId(String gantryId);
    void saveAllGantryBaseinfo(List<TblGantryBaseinfo> gantryBaseInfos);
    void saveAllRsuBaseinfo(List<TblGantryRsuBaseinfo> gantryRsuBaseInfos);
    void saveAllVplrBaseinfo(List<TblGantryVplrBaseinfo> gantryVplrBaseInfos);
    void saveAllGantryMonitor(List<TblGantryMonitorStatus> gantryMonitorStatuses);
    void saveAllRsuMonitor(List<TblGantryRsuMonitor> gantryRsuMonitors);
    void saveAllVplrMonitor(List<TblGantryVplrMonitor> gantryVplrMonitors);
    void saveAllTransaction(List<TblGantryTransaction> gantryTransactions);
    void saveAllTravelimage(List<TblGantryTravelimage> gantryTravelImages);

    void updateAllTransaction(List<TblGantryTransaction> gantryTransactions);
    void updateAllTravelimage(List<TblGantryTravelimage> gantryTravelImages);
}