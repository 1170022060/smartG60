package com.pingok.datacenter.domain.trans.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ExTranFlow {
    public Date startTime;
    public Date endTime;
    public String stations;
    public String tableName;

    public ExTranFlow(Date startTime, Date endTime, String stations, String tableName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.stations = stations;
        this.tableName = tableName;
    }
}
