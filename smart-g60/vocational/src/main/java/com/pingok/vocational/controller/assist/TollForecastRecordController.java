package com.pingok.vocational.controller.assist;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.pingok.vocational.service.algorithm.IProfitPredictService;
import com.pingok.vocational.service.assist.ITollForecastRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通行费预测记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/tollForecast")
public class TollForecastRecordController extends BaseController {

    @Autowired
    private ITollForecastRecordService tollForecastRecordService;
    @Autowired
    private IProfitPredictService iProfitPredictService;

    //    @RequiresPermissions("vocational:tollForecast:info")
//    @Log(title = "通行费预测查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "forecastType", required = false) Integer forecastType, @RequestParam(name = "startDate", required = false) Date startDate, @RequestParam(name = "endDate", required = false) Date endDate) {
        startPage();
        List<Map> info = tollForecastRecordService.selectTollForecast(forecastType, startDate, endDate);
        return getDataTable(info);
    }

    @GetMapping("/predict")
    public AjaxResult getResult(@RequestParam(name = "startTime", required = false) Date startTime,
                                @RequestParam(name = "endTime", required = false) Date endTime,
                                @RequestParam(name = "type") Integer type) {

        return AjaxResult.success(iProfitPredictService.profitPredict(DateUtil.beginOfDay(startTime), DateUtil.endOfDay(endTime), type));
    }

    @GetMapping("/getProfitPredTotal")
    public AjaxResult getTotal(@RequestParam(name = "startTime", required = false) Date startTime,
                               @RequestParam(name = "endTime", required = false) Date endTime) {
        return AjaxResult.success(iProfitPredictService.getProfitPredTotal(DateUtil.beginOfDay(startTime), DateUtil.endOfDay(endTime)));
    }
}
