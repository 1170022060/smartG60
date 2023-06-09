package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.service.nameList.IGreenService;
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
@RequestMapping("/green")
public class GreenController extends BaseController {

    @Autowired
    private IGreenService iGreenService;

    @GetMapping
    public TableDataInfo list(String stationName,String version){
        startPage();
        List<Map> info = iGreenService.getGreenList(stationName,version);
        return getDataTable(info);
    }
    @GetMapping("/findById")
    public TableDataInfo findById(Long id){
        startPage();
        List<Map> info = iGreenService.findById(id);
        return getDataTable(info);
    }
}
