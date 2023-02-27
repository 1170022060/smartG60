package com.pingok.datacenter.service.provincialCenters;

import com.alibaba.fastjson.JSONArray;

import java.util.Date;

public interface IProvincialCentersService {

    /**
     * 获数据
     * @param dateTimeNow
     */
    JSONArray getData(String name,Date dateTimeNow);
}
