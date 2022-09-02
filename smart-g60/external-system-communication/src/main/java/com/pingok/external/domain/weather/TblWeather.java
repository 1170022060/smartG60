package com.pingok.external.domain.weather;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "TBL_WEATHER")
public class TblWeather implements Serializable {

    private static final long serialVersionUID = -1;

    @Id
    private Long id;

    private String obsTime;

    private String temp;

    private String feelsLike;

    private String icon;

    private String text;

    private String wind360;

    private String windDir;

    private String windScale;

    private String windSpeed;

    private String humidity;

    private String precip;

    private String vis;

    private String cloud;

    private String dew;
}
