package com.pingok.station.controller;

import com.pingok.station.service.auditList.IAuditListService;
import com.pingok.station.service.bulkList.IBulkListService;
import com.pingok.station.service.cardBlacklist.*;
import com.pingok.station.service.emergList.IEmergListService;
import com.pingok.station.service.greenList.IGreenListService;
import com.pingok.station.service.obuBlacklist.IObuBlacklistService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
public class Controller extends BaseController {
    @Autowired
    private ICardBlacklistService listService;
    @Autowired
    private IObuBlacklistService obulistService;
    @Autowired
    private IGreenListService greenListService;
    @Autowired
    private IBulkListService bulkListService;
    @Autowired
    private IAuditListService auditListService;
    @Autowired
    private IEmergListService emergListService;


    @PostMapping("/blackIncr")
    public AjaxResult blackIncr(@Validated @RequestBody String version)
    {
        listService.increment(version);
        return AjaxResult.success();
    }

    @PostMapping("/blackAll")
    public AjaxResult blackAll(@Validated @RequestBody String version)
    {
        listService.all(version);
        return AjaxResult.success();
    }

    @PostMapping("/unzipBlackAll")
    public AjaxResult unzipBlackAll(@Validated @RequestBody String version)
    {
        listService.unzipAll(version);
        return AjaxResult.success();
    }

    @PostMapping("/greenList")
    public AjaxResult greenList(@Validated @RequestBody String version)
    {
        greenListService.greenList(version);
        return AjaxResult.success();
    }
    @PostMapping("/obuBlackIncr")
    public AjaxResult obuBlackIncr(@Validated @RequestBody String version)
    {
        obulistService.increment(version);
        return AjaxResult.success();
    }

    @PostMapping("/obuBlackAll")
    public AjaxResult obuBlackAll(@Validated @RequestBody String version)
    {
        obulistService.all(version);
        return AjaxResult.success();
    }

    @PostMapping("/unzipObuBlackAll")
    public AjaxResult unzipObuBlackAll(@Validated @RequestBody String version)
    {
        obulistService.unzipAll(version);
        return AjaxResult.success();
    }

    @PostMapping("/bulkList")
    public AjaxResult bulkList(@Validated @RequestBody String version)
    {
        bulkListService.bulkList(version);
        return AjaxResult.success();
    }

    @PostMapping("/emergListAll")
    public AjaxResult emergListAll(@Validated @RequestBody String version)
    {
        emergListService.all(version);
        return AjaxResult.success();
    }

    @PostMapping("/unzipEmergListAll")
    public AjaxResult unzipEmergListAll(@Validated @RequestBody String version)
    {
        emergListService.unzipAll(version);
        return AjaxResult.success();
    }

    @PostMapping("/auditIncr")
    public AjaxResult auditIncr(@Validated @RequestBody String version)
    {
        auditListService.increment(version);
        return AjaxResult.success();
    }

    @PostMapping("/auditBlackAll")
    public AjaxResult auditBlackAll(@Validated @RequestBody String version)
    {
        auditListService.all(version);
        return AjaxResult.success();
    }

    @PostMapping("/unzipAuditBlackAll")
    public AjaxResult unzipAuditBlackAll(@Validated @RequestBody String version)
    {
        auditListService.unzipAll(version);
        return AjaxResult.success();
    }

    @PostMapping("/unzipAuditPreAll")
    public AjaxResult unzipAuditPreAll(@Validated @RequestBody String version)
    {
        auditListService.preAll(version);
        return AjaxResult.success();
    }
}
