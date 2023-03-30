package com.pingok.monitor.domain.weather;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author lal
 */
@Data
public class TblWeather implements Serializable {
    private String weather;
    private String airTemp;
    private String tempHigh;
    private String tempLow;
    private String windDirect;
    private String windPower;
    private String windSpeed;
    private String airQuality;

}
