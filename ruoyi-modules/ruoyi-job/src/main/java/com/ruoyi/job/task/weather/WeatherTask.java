package com.ruoyi.job.task.weather;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteArtemisService;
import com.ruoyi.system.api.RemoteWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author
 * @time 2022/05/07 16:58
 */
@Slf4j
@Component("weatherTask")
public class WeatherTask {

    @Resource
    private RemoteWeatherService remoteWeatherService;

    public void updateStatus() {
        R r = remoteWeatherService.getWeather();
        if (r.getCode() == 200) {
            log.info("气象信息接入定时任务getWeather----成功");
        } else {
            log.error("气象信息接入定时任务getWeather----失败：" + r.getMsg());
        }
    }
}