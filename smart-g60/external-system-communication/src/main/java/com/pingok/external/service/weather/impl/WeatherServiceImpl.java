package com.pingok.external.service.weather.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.http.HttpUtils;
import com.pingok.external.config.AlWeatherConfig;
import com.pingok.external.config.HfWeatherConfig;
import com.pingok.external.domain.weather.TblWeather;
import com.pingok.external.domain.weather.TblWeather2;
import com.pingok.external.mapper.weather.TblWeather2Mapper;
import com.pingok.external.mapper.weather.TblWeatherMapper;
import com.pingok.external.service.weather.IWeatherService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
中国气象局（更新不及时）
参考：https://blog.csdn.net/weixin_29311017/article/details/114875306
格式：省 + 区域 + 市
- 上海：101020100
- 浙江：101210101

和风天气
https://devapi.qweather.com/v7/weather/now?location=区域&key=b2bb5a5d8e564c23ba60957809dfaf0b
上海：101020100
浙江杭州：101210101

阿里天气
https://market.aliyun.com/products/57126001/cmapi014302.html?spm=5176.21213303.4134825510.7.60dd53c9og3xjV#sku=yuncode830200000

 */
@Slf4j
@Service
public class WeatherServiceImpl implements IWeatherService {

    @Autowired
    private TblWeatherMapper tblWeatherMapper;

    @Autowired
    private TblWeather2Mapper tblWeather2Mapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    //和风天气
    @Override
    public void getWeather() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("location", HfWeatherConfig.LOCSH);
            params.put("key", HfWeatherConfig.KEY);
            String resp = HttpUtil.get(HfWeatherConfig.HOST, params);
            if(!StringUtils.isEmpty(resp)) {
                JSONObject ret = JSON.parseObject(resp);
                if(200 == ret.getInteger("code")) {
                    JSONObject now = ret.getJSONObject("now");
                    TblWeather tblWeather = JSON.parseObject(now.toJSONString(), TblWeather.class);
                    tblWeather.setId(remoteIdProducerService.nextId());
                    tblWeatherMapper.insert(tblWeather);
                }
                else {
                    log.error("调用天气接口失败。");
                }
            }
        } catch (Exception e) {
            log.error("获取天气信息失败："+e.getMessage());
        }
    }

    //阿里天气
    @Override
    public void getWeather2() {
        getWeather2ByCity("上海");
        getWeather2ByCity("浙江");
    }

    @Override
    public List<Map> getStatus() {
        return tblWeather2Mapper.selectStatus();
    }

    void getWeather2ByCity(String city) {
        try {
//            用.form("city")，需要将中文html转码
            String resp = HttpRequest.get(AlWeatherConfig.HOST)
                    .header("Authorization", "APPCODE " + AlWeatherConfig.APPCODE)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .form("citycode", cvtCityCode(city))
                    .execute().body();
            if(!StringUtils.isEmpty(resp)) {
                JSONObject ret = JSON.parseObject(resp);
                if(0 == ret.getInteger("status")) {
                    JSONObject result = ret.getJSONObject("result");
                    TblWeather2 tblWeather = JSON.parseObject(result.toJSONString(), TblWeather2.class);
                    tblWeather.setId(remoteIdProducerService.nextId());
                    tblWeather.setWdate(tblWeather.getDate());
                    tblWeather.setCity(city);
                    tblWeather.setQuality(tblWeather.getAqi().getString("quality"));
                    tblWeather2Mapper.insert(tblWeather);
                }
                else {
                    log.error("调用天气接口失败。");
                }
            }
        } catch (Exception e) {
            log.error("获取天气信息失败："+e.getMessage());
        }
    }

    String cvtCityCode(String city) {
        switch (city){
            case "浙江": return "101210101";
            case "上海": return "101020100";
        }
        return "";
    }
}
