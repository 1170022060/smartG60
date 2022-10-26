package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.domain.nameList.TblEmgAppend;
import com.pingok.vocational.service.nameList.IRescueService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@RestController
@RequestMapping("/rescueList")
public class RescueController extends BaseController {

    @Autowired
    private IRescueService iRescueService;

    @GetMapping
    public TableDataInfo list(String stationName,String version){
        startPage();
        List<Map> info = iRescueService.getEmgAppendList(stationName,version);
        return getDataTable(info);
    }

    @GetMapping("/findById")
    public TableDataInfo findById(@RequestParam Long id){
        startPage();
        List<Map> info = iRescueService.findById(id);
        return getDataTable(info);
    }
}
