package com.pingok.monitor.service.weather;

import com.pingok.monitor.domain.weather.TblWeather;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IWeatherService {
    /**
     * 获取天气情况
     */
    List<TblWeather> getWeather();
}
