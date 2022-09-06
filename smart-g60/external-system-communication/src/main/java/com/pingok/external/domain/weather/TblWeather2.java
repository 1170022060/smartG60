package com.pingok.external.domain.weather;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Table(name = "TBL_WEATHER2")
public class TblWeather2 implements Serializable {

    private static final long serialVersionUID = -1;

    @Id
    private Long id;

    @Transient
    private String date;

    private String wdate;

    private String week;

    private String weather;

    private String temp;

    private String temphigh;

    private String templow;

    private String img;

    private String humidity;

    private String pressure;

    private String windspeed;

    private String winddirect;

    private String windpower;

    private String updatetime;

    private String quality;

    @Transient
    private JSONObject aqi;

    private String city;
}
