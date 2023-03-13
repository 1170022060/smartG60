package com.pingok.vocational.service.assist;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoadForecastService {

    /**
     * 路况信息预测
     * @return
     */
    List<Map> roadForecast(Date startDate,Date endDate);
}
