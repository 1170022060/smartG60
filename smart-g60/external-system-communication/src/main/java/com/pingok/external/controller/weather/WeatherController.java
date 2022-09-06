package com.pingok.external.controller.weather;

import com.pingok.external.service.weather.IWeatherService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController extends BaseController {

    @Autowired
    private IWeatherService iWeatherService;

    @GetMapping
    public AjaxResult getWeather() {
        iWeatherService.getWeather2();
        return AjaxResult.success();
    }

    @GetMapping("/status")
    public AjaxResult getRtStatus() {
        return AjaxResult.success(iWeatherService.getStatus());
    }
}
