package com.pingok.vocational.controller.blackCard;

import com.pingok.vocational.service.blackCard.IBlackCardService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blackCard")
public class BlackCardControl extends BaseController {

    @Autowired
    private IBlackCardService iBlackCardService;

    @Log(title = "状态名单分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo getNowList(@RequestParam(name = "mediaType", required = false) Integer mediaType,
                                    @RequestParam(name = "mediaId",required = false) String mediaId,
                                    @RequestParam(name = "startDate",required = false) String startDate,
                                    @RequestParam(name = "endDate",required = false) String endDate) {

        startPage();
        List<Map> info = iBlackCardService.getNowList(mediaId, mediaType, startDate, endDate);
        return getDataTable(info);
    }

    @Log(title = "收费站在用名单版本分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/stationUsed")
    public TableDataInfo getStationUsedList() {
        startPage();
        List<Map> info = iBlackCardService.getStationUsedList();
        return getDataTable(info);
    }

    @Log(title = "历史版本分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/record")
    public TableDataInfo getRecordList(@RequestParam(name = "mediaId") String mediaId) {
        startPage();
        List<Map> info = iBlackCardService.getRecordList(mediaId);
        return getDataTable(info);
    }
}
