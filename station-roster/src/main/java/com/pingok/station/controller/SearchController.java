package com.pingok.station.controller;

import com.pingok.station.service.cardBlacklist.ICardBlacklistService;
import com.pingok.station.service.obuBlacklist.IObuBlacklistService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ICardBlacklistService listService;

    @Autowired
    private IObuBlacklistService obulistService;

    @GetMapping("/black")
    public AjaxResult black(@RequestParam(name = "cardId",required = false) String cardId)
    {
        return AjaxResult.success(listService.findByCardId(cardId));
    }

    @GetMapping("/obuBlack")
    public AjaxResult obuBlack(@RequestParam(name = "cardId",required = false) String cardId)
    {
        return AjaxResult.success(obulistService.findByCardId(cardId));
    }
}
