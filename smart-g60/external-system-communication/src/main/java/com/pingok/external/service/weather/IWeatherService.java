package com.pingok.external.service.weather;

import java.util.List;
import java.util.Map;

public interface IWeatherService {
    void getWeather();
    void getWeather2();
    List<Map> getStatus();
}
