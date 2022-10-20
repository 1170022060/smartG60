package com.pingok.monitor.controller.weather;

import com.pingok.monitor.service.weather.IWeatherService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lal
 */
@RestController
@RequestMapping("/weather")
public class weatherController extends BaseController {

    @Autowired
    private IWeatherService iWeatherService;

    @GetMapping
    public AjaxResult getWeather(){

        return AjaxResult.success(iWeatherService.getWeather());
    }
}
