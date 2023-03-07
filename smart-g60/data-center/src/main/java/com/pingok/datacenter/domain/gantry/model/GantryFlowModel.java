package com.pingok.datacenter.domain.gantry.model;

import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @time 2022/5/23 12:56
 */
@Data
public class GantryFlowModel {
    public Date startTime;
    public String tableName;
    public Date endTime;
    public String gantrys;

    public GantryFlowModel(Date startTime, String tableName, Date endTime, String gantrys) {
        this.startTime = startTime;
        this.tableName = tableName;
        this.endTime = endTime;
        this.gantrys = gantrys;
    }
}
