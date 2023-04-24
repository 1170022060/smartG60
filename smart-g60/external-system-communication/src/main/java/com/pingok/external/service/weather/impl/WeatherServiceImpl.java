package com.pingok.external.service.weather.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.*;
import com.pingok.external.config.AlWeatherConfig;
import com.pingok.external.config.HfWeatherConfig;
import com.pingok.external.domain.weather.TblWeather;
import com.pingok.external.domain.weather.TblWeather2;
import com.pingok.external.mapper.weather.TblWeather2Mapper;
import com.pingok.external.service.weather.IWeatherService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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

    @Resource
    private TblWeather2Mapper tblWeather2Mapper;
    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    /**
     * 和风天气
     */
    @Override
    public void getWeather() {
        getWeatherByHF("上海");
        get3tdWeatherByHF("浙江");
    }

    /**
     * 阿里天气
     */
    @Override
    public void getWeather2() {
        getWeather2ByCity("上海");
        getWeather2ByCity("浙江");
    }

    @Override
    public List<Map> getStatus() {
        return tblWeather2Mapper.selectStatus();
    }



    /**
     * 使用和风天气 三天天气、当天天气、空气质量的合集
     */
    public void getWeatherByHF(String city) {
        ExecutorService threadPoolExecutor = getThreadPoolExecutor();
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(threadPoolExecutor);
        ListenableFuture<TblWeather> listenableFuture = executorService.submit(() -> {
            // 获取当前天气数据
            return getNowWeatherByHF(city);
        });
        ListenableFuture<TblWeather> listenableFuture1 = executorService.submit(() -> {
            // 获取三天数据
            return get3tdWeatherByHF(city);
        });
        ListenableFuture<TblWeather> listenableFuture2 = executorService.submit(() -> {
            // 获取空气质量
            return getAirByHF(city);
        });

        // 合并多个Future
        ListenableFuture<List<TblWeather>> listListenableFuture = Futures.allAsList(listenableFuture, listenableFuture1, listenableFuture2);
        Futures.addCallback(listListenableFuture, new FutureCallback<List<TblWeather>>() {
            @Override
            public void onSuccess(@Nullable List<TblWeather> tblWeathers) {
                TblWeather2 tblWeather2 = new TblWeather2();
                tblWeathers.forEach(item -> {
                    // 和风天气数据转换为阿里天气格式
                    String category = item.getCategory();
                    String tempMax = item.getTempMax();
                    if (!StringUtils.isEmpty(category)) {
                        // 空气质量
                        tblWeather2.setQuality(category);
                    } else if (!StringUtils.isEmpty(tempMax)) {
                        // 三天天气 查询温度区间
                        tblWeather2.setTemphigh(tempMax);
                        tblWeather2.setTemplow(item.getTempMin());
                    } else {
                        //当前天气
                        String obsTime = item.getObsTime();
                        LocalDateTime localDateTime = LocalDateTimeUtil.parse(obsTime, "yyyy-MM-dd'T'HH:mm+08:00");
                        String week = LocalDateTimeUtil.dayOfWeek(LocalDate.now()).toChinese();
                        tblWeather2.setWdate(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
                        tblWeather2.setWeek(week);
                        tblWeather2.setWeather(item.getText());
                        tblWeather2.setTemp(item.getTemp());
                        tblWeather2.setHumidity(item.getHumidity());
                        tblWeather2.setPressure(item.getPrecip());
                        tblWeather2.setPressure(item.getPrecip());
                        tblWeather2.setWindspeed(item.getWindSpeed());
                        tblWeather2.setWinddirect(item.getWindDir());
                        tblWeather2.setWindpower(item.getWindScale());
                        tblWeather2.setUpdatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        tblWeather2.setCity(city);
                        // TODO: 2023/4/24 0024 icon 编码转换
                    }
                });

                tblWeather2.setId(remoteIdProducerService.nextId());
                log.info("天气拼装结果：{}", tblWeather2);
                tblWeather2Mapper.insert(tblWeather2);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                log.info("和风天气接口调用失败");
            }
        });
    }

    void getWeather2ByCity(String city) {
        try {
//            用.form("city")，需要将中文html转码
            String resp = HttpRequest.get(AlWeatherConfig.HOST)
                    .header("Authorization", "APPCODE " + AlWeatherConfig.APPCODE)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .form("citycode", cvtCityCode(city))
                    .execute().body();
            if (!StringUtils.isEmpty(resp)) {
                JSONObject ret = JSON.parseObject(resp);
                if (0 == ret.getInteger("status")) {
                    JSONObject result = ret.getJSONObject("result");
                    TblWeather2 tblWeather = JSON.parseObject(result.toJSONString(), TblWeather2.class);
                    tblWeather.setId(remoteIdProducerService.nextId());
                    tblWeather.setWdate(tblWeather.getDate());
                    tblWeather.setCity(city);
                    tblWeather.setQuality(tblWeather.getAqi().getString("quality"));
                    tblWeather2Mapper.insert(tblWeather);
                } else {
                    log.error("调用天气接口失败。");
                }
            }
        } catch (Exception e) {
            log.error("获取天气信息失败：" + e.getMessage());
        }
    }

    String cvtCityCode(String city) {
        switch (city) {
            case "浙江":
                return "101210101";
            case "上海":
                return "101020100";
        }
        return "";
    }

    /**
     * 和风 - 获取当前天气
     */
    TblWeather getNowWeatherByHF(String city) {
        TblWeather tblWeather = null;
        try {
            Map<String, Object> params = getStringObjectMap(city);
            String resp = HttpUtil.get(HfWeatherConfig.HOST + "/v7/weather/now", params);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject ret = JSON.parseObject(resp);
                if (200 == ret.getInteger("code")) {
                    JSONObject now = ret.getJSONObject("now");
                    tblWeather = JSON.parseObject(now.toJSONString(), TblWeather.class);
                } else {
                    log.error("调用天气接口失败。");
                }
            }
        } catch (Exception e) {
            log.error("获取天气信息失败：" + e.getMessage());
        } finally {
            return tblWeather;
        }
    }

    /**
     * 和风 - 获取三天天气 主要获取最低最高温度
     *
     * @return
     */
    TblWeather get3tdWeatherByHF(String city) {
        TblWeather tblWeather = new TblWeather();
        try {
            Map<String, Object> params = getStringObjectMap(city);
            String resp = HttpUtil.get(HfWeatherConfig.HOST + "/v7/weather/3d", params);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject ret = JSON.parseObject(resp);
                if (200 == ret.getInteger("code")) {
                    JSONArray daily = ret.getJSONArray("daily");
                    log.info("和风天气 3天天气接口返回：{}", daily);
                    JSONObject jsonObject = daily.getJSONObject(0);
                    tblWeather.setTempMin(jsonObject.getString("tempMin"));
                    tblWeather.setTempMax(jsonObject.getString("tempMax"));
                } else {
                    log.error("调用天气接口失败。");
                }
            }
        } catch (Exception e) {
            log.error("获取天气信息失败：" + e.getMessage());
        } finally {
            return tblWeather;
        }
    }

    /**
     * 和风 - 获取空气质量
     *
     * @return
     */
    TblWeather getAirByHF(String city) {
        TblWeather tblWeather = new TblWeather();
        try {
            Map<String, Object> params = getStringObjectMap(city);
            String resp = HttpUtil.get(HfWeatherConfig.HOST + "/v7/air/now", params);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject ret = JSON.parseObject(resp);
                if (200 == ret.getInteger("code")) {
                    JSONObject now = ret.getJSONObject("now");
                    String category = now.getString("category");
                    tblWeather.setCategory(category);
                } else {
                    log.error("调用天气接口失败。");
                }
            }
        } catch (Exception e) {
            log.error("获取天气信息失败：" + e.getMessage());
        } finally {
            return tblWeather;
        }
    }

    /**
     * 包装请求参数
     *
     * @return
     */
    private Map<String, Object> getStringObjectMap(String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("location", cvtCityCode(city));
        params.put("key", HfWeatherConfig.KEY);
        return params;
    }

    /**
     * 创建一个线程池
     *
     * @return
     */
    public ExecutorService getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                3,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }
}
