package com.pingok.monitor.service.weather.impl;

import com.pingok.monitor.domain.weather.TblWeather;
import com.pingok.monitor.mapper.weather.WeatherMapper;
import com.pingok.monitor.service.weather.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class WeatherServiceImpl implements IWeatherService {

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public List<TblWeather> getWeather() {

        return weatherMapper.weather();
    }
}
