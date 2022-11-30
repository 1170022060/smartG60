package com.pingok.event.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.event.domain.TblEventHandle;
import com.pingok.event.domain.TblEventRecord;
import com.pingok.event.domain.videoEvent.*;
import com.pingok.event.service.IEventService;
import com.pingok.event.service.IVideoEventService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件管理
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/eventControl")
public class EventController extends BaseController {

    @Autowired
    private IEventService iEventService;

    @Autowired
    private IVideoEventService iVideoEventService;

    @Autowired
    private RemoteKafkaService remoteKafkaService;


    @GetMapping("/selectByEventTypeAndPileNo")
    public AjaxResult selectByEventTypeAndPileNo(@RequestParam String eventType, @RequestParam String pileNo) {
        return AjaxResult.success(iEventService.selectByEventTypeAndPileNo(eventType, pileNo));
    }

    /**
     * 配置事件告警列表
     */
    @GetMapping("/eventAlarmAll")
    public AjaxResult eventAlarmAll() {
        return AjaxResult.success(iEventService.eventAlarmAll());
    }

    /**
     * 配置事件告警类型
     */
    @PostMapping("/eventAlarmAdd")
    public AjaxResult eventAlarmAdd(@RequestBody List<Integer> eventTypes) {
        iEventService.eventAlarmAdd(eventTypes);
        return AjaxResult.success();
    }


    @GetMapping("/searchEvent")
    public AjaxResult searchEvent() {
        return AjaxResult.success(iEventService.searchEvent());
    }

    /**
     * 事件误报
     */
//    @RequiresPermissions("event:eventControl:fault")
    @Log(title = "事件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/fault")
    public AjaxResult fault(@RequestParam Long id, @RequestParam String remark) {
        iEventService.fault(id, remark);
        return AjaxResult.success();
    }

    /**
     * 获取事件视频
     *
     * @return
     */
//    @RequiresPermissions("event:eventControl:getEventVideo")
//    @Log(title = "事件管理", businessType = BusinessType.OTHER)
    @GetMapping("/getEventVideo")
    public AjaxResult getEventVideo(@RequestParam Long eventId) {
        JSONObject data = new JSONObject();
        data.put("ubiLogicId", eventId);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_UPDATE_EVENT_VIDEO);
        kafkaEnum.setData(data.toJSONString());
        remoteKafkaService.send(kafkaEnum);
        return AjaxResult.success("请求成功，后台正在下载，请等待");
    }

    /**
     * 接收事件视频
     */
    @PostMapping("/updateEventVideo")
    public AjaxResult updateEventVideo(@RequestParam Long ubiLogicId, @RequestParam String url) {
        iEventService.updateEventVideo(ubiLogicId, url);
        return AjaxResult.success();
    }


    /**
     * 查看报告
     *
     * @return
     */
//    @RequiresPermissions("event:eventControl:report")
//    @Log(title = "事件管理", businessType = BusinessType.OTHER)
    @GetMapping("/report")
    public AjaxResult report(@RequestParam Long id) {
        return AjaxResult.success(iEventService.report(id));
    }

    /**
     * 事件解除
     */
//    @RequiresPermissions("event:eventControl:relieve")
    @Log(title = "事件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/relieve")
    public AjaxResult relieve(@RequestParam Long id) {
        iEventService.relieve(id);
        return AjaxResult.success();
    }

    /**
     * 填报处置内容
     */
//    @RequiresPermissions("event:eventControl:handleContent")
    @Log(title = "事件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/handleContent")
    public AjaxResult handleContent(@RequestBody List<TblEventHandle> tblEventHandles) {
        iEventService.handleContent(tblEventHandles);
        return AjaxResult.success();
    }

    /**
     * 应急处置
     */
//    @RequiresPermissions("event:eventControl:handle")
    @Log(title = "事件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/handle")
    public AjaxResult handle(@RequestParam Long id, @RequestBody JSONArray eventPlan) {
        iEventService.handle(id, eventPlan);
        return AjaxResult.success();
    }

    /**
     * 事件确认
     */
//    @RequiresPermissions("event:eventControl:confirm")
    @Log(title = "事件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestParam Long id, @RequestParam String eventType, @RequestParam String remark, @RequestParam String direction) {
        iEventService.confirm(id, eventType, remark, direction);
        return AjaxResult.success();
    }

    /**
     * 获取列表
     *
     * @return
     */
//    @RequiresPermissions("event:eventControl:list")
//    @Log(title = "事件管理", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(Integer status) {
        startPage();
        List<Map> list = iEventService.search(status);
        return getDataTable(list);
    }


    /**
     * 获取未确认、已确认事件列表
     *
     * @return
     */

//    @Log(title = "事件管理", businessType = BusinessType.OTHER)
    @GetMapping("/event")
    public AjaxResult event() {
        return AjaxResult.success(iEventService.event());
    }

    /**
     * 根据id查询
     */
//    @Log(title = "事件管理", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findById(@RequestParam Long id) {
        return AjaxResult.success(iEventService.findById(id));
    }


    /**
     * 新增应
     */
//    @RequiresPermissions("event:eventControl:add")
    @Log(title = "事件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblEventRecord tblEventRecord) {
        return toAjax(iEventService.insert(tblEventRecord));
    }

    /**
     * 修改
     */
//    @RequiresPermissions("event:eventControl:edit")
    @Log(title = "事件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblEventRecord tblEventRecord) {
        return toAjax(iEventService.update(tblEventRecord));
    }

    /**
     * 流量统计
     */
    @PostMapping("/flux")
    public AjaxResult fluxData(@RequestBody TblEventFlux tblEventFlux) {
        iVideoEventService.fluxData(tblEventFlux);
        return AjaxResult.success();
    }

    /**
     * 过车数据
     */
    @PostMapping("/plateInfo")
    public AjaxResult plateInfo(@RequestBody TblEventPlateInfo tblEventPlateInfo) {
        iVideoEventService.plateInfo(tblEventPlateInfo);
        iVideoEventService.parkVehInfo(tblEventPlateInfo);
        iVideoEventService.parkingStatistics(tblEventPlateInfo);
        return AjaxResult.success();
    }

    /**
     * 交通事件
     */
    @PostMapping("/vehicleEvent")
    public AjaxResult vehicleEvent(@RequestBody TblEventVehicleEvent tblEventVehicleEvent) {
        log.info("事件id：" + tblEventVehicleEvent.getUbiLogicId() + "----事件类型：" + tblEventVehicleEvent.getUiEventType() + "对应TrackId：" + tblEventVehicleEvent.getUiTrackId());
        switch (tblEventVehicleEvent.getUiEventType()) {
            //事件解除
            case 31:
            case 32:
            case 34:
            case 35:
            case 36:
            case 40:
            case 41:
                iVideoEventService.relieveEvent(tblEventVehicleEvent);
                break;
            default:
                iVideoEventService.vehicleEvent(tblEventVehicleEvent);
                break;
        }
        return AjaxResult.success();
    }

    /**
     * 客流量
     */
    @PostMapping("/passengerFlow")
    public AjaxResult passengerFlow(@RequestBody TblEventPassengerFlow tblEventPassengerFlow) {
        iVideoEventService.passengerFlow(tblEventPassengerFlow);
        return AjaxResult.success();
    }

    /**
     * 货车检查事件
     */
    @PostMapping("/parkingEvent")
    public AjaxResult parkingEvent(@RequestBody TblEventParkingEvent tblEventParkingEvent) {
        iVideoEventService.parkingEvent(tblEventParkingEvent);
        return AjaxResult.success();
    }

    /**
     * 获取分上下行的已确认的交通事件
     *
     * @return
     */
    @GetMapping("/GetfilterEvent")
    public AjaxResult getTable() {
        Map map = new HashMap();
        map.put("upstream", iEventService.filterUpEvent());
        map.put("downstream", iEventService.filterDownEvent());
        return AjaxResult.success(map);
    }
}
