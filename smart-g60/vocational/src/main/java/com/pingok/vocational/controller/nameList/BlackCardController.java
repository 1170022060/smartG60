package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.domain.nameList.TblBlackCardLogN;
import com.pingok.vocational.service.nameList.IBlackCardService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
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
@RequestMapping("/blackCardList")
public class BlackCardController extends BaseController {

    @Autowired
    private IBlackCardService iBlackCardService;

    @GetMapping
    public TableDataInfo list(String stationName,String version){
        startPage();
        List<Map> info = iBlackCardService.getBlackCardList(stationName,version);
        return getDataTable(info);
    }

    @GetMapping("/findById")
    public TableDataInfo findById(@RequestParam Long id){
        startPage();
        List<Map> info = iBlackCardService.findById(id);
        return getDataTable(info);
    }

    @GetMapping("/statisticsVersion")
    public AjaxResult statisticsVersion(){
        Object obj = iBlackCardService.statisticsVersion();
        return AjaxResult.success(obj);
    }
}
