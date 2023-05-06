package com.pingok.monitor.controller.lane;

import com.pingok.monitor.service.lane.ILaneService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车道 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/lane")
public class LaneController extends BaseController {

    @Autowired
    private ILaneService iLaneService;

//    @RequiresPermissions("monitor:lane:findByStationId")
//    @Log(title = "车道监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findByStationId(@RequestParam String stationId) {
        return AjaxResult.success(iLaneService.findByStationId(stationId));
    }

    @GetMapping("/getStationFlowUpload")
    public AjaxResult getList(){
        return AjaxResult.success(iLaneService.getStationFlowUpload());
    }

    @GetMapping("/getFlowInfo")
    public AjaxResult getFlowInfo() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String dateStr = sdf.format(date);
        return AjaxResult.success(iLaneService.getStationTotalFlow(dateStr));
    }

    @GetMapping("/getStationInfo")
    public AjaxResult getInfo(){
        return AjaxResult.success(iLaneService.getStationInfo());
    }

    @GetMapping("/getDeviceStatus")
    public AjaxResult getDeviceStatus(String stationHex){
        return AjaxResult.success(iLaneService.getDeviceStatus(stationHex));
    }

    @GetMapping("/getSpecialList")
    public TableDataInfo getList(@RequestParam String stationId,@RequestParam String laneId){
        startPage();
        List<Map> info = iLaneService.getList(stationId,laneId);
        return getDataTable(info);
    }
}
