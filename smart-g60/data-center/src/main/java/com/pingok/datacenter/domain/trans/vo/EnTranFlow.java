package com.pingok.datacenter.domain.trans.vo;

import lombok.Data;

import java.util.Date;

@Data
public class EnTranFlow {
    public Date startTime;
    public Date endTime;
    public String stations;
    public String tableName;

    public EnTranFlow(Date startTime, Date endTime, String stations, String tableName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.stations = stations;
        this.tableName = tableName;
    }
}
