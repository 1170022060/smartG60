package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.service.nameList.IEpidemicService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@RestController
@RequestMapping("/epidemic")
public class EpidemicController extends BaseController {

    @Autowired
    private IEpidemicService iEpidemicService;

    @GetMapping
    public TableDataInfo list(String stationName,String version){
        startPage();
        List<Map> info = iEpidemicService.getEpidemicList(stationName,version);
        return getDataTable(info);
    }
}
