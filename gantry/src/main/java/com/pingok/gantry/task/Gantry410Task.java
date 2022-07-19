package com.pingok.gantry.task;

import com.pingok.gantry.domain.entity.charge.*;
import com.pingok.gantry.domain.entity.gantry.*;
import com.pingok.gantry.service.charge.IChargeService;
import com.pingok.gantry.service.gantry110.IGantry310Service;
import com.pingok.gantry.service.gantry110.IGantry410Service;
import com.pingok.gantry.utils.BeanUtil;
import com.pingok.gantry.utils.DateUtil;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @time 2022/7/7 17:19
 */
@Slf4j
@Configurable
@EnableScheduling
@Component("gantry410Task")
public class Gantry410Task {
//    @Autowired
//    private IGantry410Service gantryService;
//    @Autowired
//    private IChargeService chargeService;
//
//    private List<String> gantryId = Arrays.asList("G006031001000410010");
//
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void travelImageUploadError() {
//        try {
//            List<TblGantryTravelimage> gantryTravelImages = chargeService.findTravelImageUploadError(gantryId);
//            //log.info("更新门架gantry410未上传牌识信息：" + gantryTravelImages);
//            if(gantryTravelImages!=null && gantryTravelImages.size()>0){
//                DfsGantryTravelimage travelImage;
//                for(TblGantryTravelimage g: gantryTravelImages){
//                    travelImage = gantryService.findTravelImageById(g.getPicId());
//                    BeanUtil.copyNotNullProperties(travelImage, g);
//                    gantryTravelImages.add(g);
//                }
//                chargeService.updateAllTravelimage(gantryTravelImages);
//            }
//        } catch (Exception e) {
//            log.error("更新门架gantry410未上传牌识信息错误：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void transactionUploadError() {
//        try {
//            List<TblGantryTransaction> gantryTransactions = chargeService.findTransactionUploadError(gantryId);
//            //log.info("更新门架gantry410未上传流水信息：" + gantryTransactions);
//            if(gantryTransactions!=null && gantryTransactions.size()>0){
//                DfsGantryTransaction transaction;
//                for(TblGantryTransaction g:gantryTransactions){
//                    transaction = gantryService.findTransactionById(g.getTradeId());
//                    BeanUtil.copyNotNullProperties(transaction, g);
//                    gantryTransactions.add(g);
//                }
//                chargeService.updateAllTransaction(gantryTransactions);
//            }
//        } catch (Exception e) {
//            log.error("更新门架gantry410未上传流水信息错误：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void gantryBaseInfo() {
//        String startTime = DateUtil.getTodayStart();
//        String endTime = DateUtil.getTodayEnd();
//        try {
//            List<DfsGantryBaseinfo> list = gantryService.findByStateTime(startTime, endTime);
//            //log.info(" 获取门架gantry410基础信息：" + list);
//            if (list != null && list.size() > 0) {
//                List<TblGantryBaseinfo> gantryBaseInfos = new ArrayList<>();
//                TblGantryBaseinfo tblGantryBaseinfo;
//                for (DfsGantryBaseinfo g : list) {
//                    tblGantryBaseinfo = new TblGantryBaseinfo();
//                    BeanUtil.copyNotNullProperties(g, tblGantryBaseinfo);
//                    tblGantryBaseinfo.setGantryBaseId(g.getId().getGantryBaseId());
//                    tblGantryBaseinfo.setComputerOrder(g.getId().getComputerOrder());
//                    gantryBaseInfos.add(tblGantryBaseinfo);
//                }
//                chargeService.saveAllGantryBaseinfo(gantryBaseInfos);
//            }
//        } catch (Exception e) {
//            log.error(" 获取门架gantry410基础信息报错：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void rsuBaseInfo() {
//        String startTime = DateUtil.getTodayStart();
//        String endTime = DateUtil.getTodayEnd();
//        try {
//            List<DfsGantryRsuBaseinfo> list = gantryService.findByCreateTime(startTime, endTime);
//            //log.info(" 获取门架gantry410RSU基础信息：" + list);
//            if (list != null && list.size() > 0) {
//                List<TblGantryRsuBaseinfo> gantryRsuBaseInfos = new ArrayList<>();
//                TblGantryRsuBaseinfo tblGantryRsuBaseinfo;
//                for (DfsGantryRsuBaseinfo g : list) {
//                    tblGantryRsuBaseinfo = new TblGantryRsuBaseinfo();
//                    BeanUtil.copyNotNullProperties(g, tblGantryRsuBaseinfo);
//                    tblGantryRsuBaseinfo.setGantryId(g.getId().getGantryId());
//                    tblGantryRsuBaseinfo.setControlId(g.getId().getControlId());
//                    tblGantryRsuBaseinfo.setStateVersion(g.getId().getStateVersion());
//                    gantryRsuBaseInfos.add(tblGantryRsuBaseinfo);
//                }
//                chargeService.saveAllRsuBaseinfo(gantryRsuBaseInfos);
//            }
//        } catch (Exception e) {
//            log.error(" 获取门架gantry410RSU基础信息报错：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void vprlBaseInfo() {
//        String startTime = DateUtil.getTodayStart();
//        String endTime = DateUtil.getTodayEnd();
//        try {
//            List<DfsGantryVplrBaseinfo> list = gantryService.findVplrByCreateTime(startTime, endTime);
//            //log.info(" 获取门架gantry410Vprl基础信息：" + list);
//            if (list != null && list.size() > 0) {
//                List<TblGantryVplrBaseinfo> gantryVplrBaseInfos = new ArrayList<>();
//                TblGantryVplrBaseinfo tblGantryVplrBaseinfo;
//                for (DfsGantryVplrBaseinfo g : list) {
//                    tblGantryVplrBaseinfo = new TblGantryVplrBaseinfo();
//                    BeanUtil.copyNotNullProperties(g, tblGantryVplrBaseinfo);
//                    gantryVplrBaseInfos.add(tblGantryVplrBaseinfo);
//                }
//                chargeService.saveAllVplrBaseinfo(gantryVplrBaseInfos);
//            }
//        } catch (Exception e) {
//            log.error(" 获取门架gantry410Vprl基础信息报错：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void gantryMonitor() {
//        String time = DateUtil.getPreMinute(-5);
//        try {
//            List<DfsGantryMonitorStatus> list = gantryService.findGantryMonitor(time);
//            //log.info(" 获取门架gantry410状态信息：" + list);
//            if (list != null && list.size() > 0) {
//                List<TblGantryMonitorStatus> gantryMonitorStatuses = new ArrayList<>();
//                TblGantryMonitorStatus tblGantryMonitorStatus;
//                TblGantryStateCollect tblGantryStateCollect;
//                for (DfsGantryMonitorStatus g : list) {
//                    tblGantryMonitorStatus = new TblGantryMonitorStatus();
//                    BeanUtil.copyNotNullProperties(g, tblGantryMonitorStatus);
//                    tblGantryMonitorStatus.setRunStateId(g.getId().getRunStateId());
//                    tblGantryMonitorStatus.setComputerOrder(g.getId().getComputerOrder());
//                    gantryMonitorStatuses.add(tblGantryMonitorStatus);
//                }
//                chargeService.saveAllGantryMonitor(gantryMonitorStatuses);
//            }
//        } catch (Exception e) {
//            log.error(" 获取门架gantry410状态信息报错：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0/1 * * * ?")
//    public void rsuMonitor() {
//        String time = DateUtil.getPreMinute(-5);
//        try {
//            List<DfsGantryRsuMonitor> list = gantryService.findRsuMonitor(time);
//            //log.info(" 获取门架gantry410rsu状态信息：" + list);
//            if (list != null && list.size() > 0) {
//                List<TblGantryRsuMonitor> gantryRsuMonitors = new ArrayList<>();
//                TblGantryRsuMonitor tblGantryRsuMonitor;
//                for (DfsGantryRsuMonitor g : list) {
//                    tblGantryRsuMonitor = new TblGantryRsuMonitor();
//                    BeanUtil.copyNotNullProperties(g, tblGantryRsuMonitor);
//                    tblGantryRsuMonitor.setGantryId(g.getId().getGantryId());
//                    tblGantryRsuMonitor.setControlId(g.getId().getControlId());
//                    tblGantryRsuMonitor.setStateVersion(g.getId().getStateVersion());
//                    gantryRsuMonitors.add(tblGantryRsuMonitor);
//                }
//                chargeService.saveAllRsuMonitor(gantryRsuMonitors);
//            }
//        } catch (Exception e) {
//            log.error(" 获取门架gantry410rsu状态信息报错：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void vplrMonitor() {
//        String time = DateUtil.getPreMinute(-5);
//        try {
//            List<DfsGantryVplrMonitor> list = gantryService.findVplrMonitor(time);
//            //log.info(" 获取门架gantry410vplr状态信息：" + list);
//            if (list != null && list.size() > 0) {
//                List<TblGantryVplrMonitor> gantryVplrMonitors = new ArrayList<>();
//                TblGantryVplrMonitor tblGantryVplrMonitor;
//                for (DfsGantryVplrMonitor g : list) {
//                    tblGantryVplrMonitor = new TblGantryVplrMonitor();
//                    BeanUtil.copyNotNullProperties(g, tblGantryVplrMonitor);
//                    gantryVplrMonitors.add(tblGantryVplrMonitor);
//                }
//                chargeService.saveAllVplrMonitor(gantryVplrMonitors);
//            }
//        } catch (Exception e) {
//            log.error(" 获取门架gantry410vplr状态信息报错：" + e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void gantryTransaction() {
//        Date startTime = DateUtils.parseDate(DateUtil.getPreMinute(-5));
//        Date endTime = new Date();
//        try {
//            List<DfsGantryTransaction> list = gantryService.findTransactionByTransTime(startTime,endTime);
//            //log.info("获取门架gantry410Transaction数据："+list);
//            if(list!=null && list.size()>0){
//                TblGantryTransaction tblGantryTransaction;
//                List<TblGantryTransaction> gantryTransactions = new ArrayList<>();
//                for(DfsGantryTransaction t:list){
//                    tblGantryTransaction = new TblGantryTransaction();
//                    BeanUtil.copyNotNullProperties(t,tblGantryTransaction);
//                    gantryTransactions.add(tblGantryTransaction);
//                }
//                chargeService.saveAllTransaction(gantryTransactions);
//            }
//        } catch (Exception e) {
//            log.error("获取门架gantry410Transaction数据报错："+e.getMessage());
//        }
//    }
//
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void gantryTravelImage() {
//        Date startTime = DateUtils.parseDate(DateUtil.getPreMinute(-5));
//        Date endTime = new Date();
//        try {
//            List<DfsGantryTravelimage> list = gantryService.findTravelImageByPicTime(startTime,endTime);
//            //log.info("获取门架gantry410TravelImage数据："+list);
//            if(list!=null && list.size()>0){
//                TblGantryTravelimage tblGantryTravelImage;
//                List<TblGantryTravelimage> gantryTravelimages = new ArrayList<>();
//                for(DfsGantryTravelimage t:list){
//                    tblGantryTravelImage = new TblGantryTravelimage();
//                    BeanUtil.copyNotNullProperties(t,tblGantryTravelImage);
//                    gantryTravelimages.add(tblGantryTravelImage);
//                }
//                chargeService.saveAllTravelimage(gantryTravelimages);
//            }
//        } catch (Exception e) {
//            log.error("获取门架gantry410Travelimage数据报错："+e.getMessage());
//        }
//    }
}
