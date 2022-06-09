package com.pingok.monitor.domain.gantry;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 门架设备状态详情表(TBL_GANTRY_STATUS_DTL)
 * 
 * @author qiumin
 * @version 1.0.0 2022-04-19
 */
@Table(name = "TBL_GANTRY_STATUS_DTL")
@Data
public class TblGantryStatusDtl implements Serializable {


    /** id */
    @Id
    private Long id;

    /** 状态id */
    private Long statusId;

    /** 类型：1-服务器 2-工控机 3-RSU 4-牌识 5-北斗授时 6-智能机柜 7-存储 8-全景相机 9-前端软件 10-后端软件 */
    private Integer type;

    /** 主机状态：0-异常  1-正常 */
    private Integer mainStatus;

    /** 主机状态描述 */
    private String mainStatusDesc;

    /** 备机状态：0-异常  1-正常 */
    private Integer standbyStatus;

    /** 备机状态描述 */
    private String standbyStatusDesc;

    /** 主机状态详情JSON */
    private String mainStatusDtl;

    /** 备机状态详情JSON */
    private String standbyStatusDtl;

    @Transient
    private JSON mainStatusDtlJson;
    @Transient
    private JSON standbyStatusDtlJson;

    public TblGantryStatusDtl(Long id, Long statusId, Integer type, Integer mainStatus, String mainStatusDesc, Integer standbyStatus, String standbyStatusDesc, String mainStatusDtl, String standbyStatusDtl) {
        this.id = id;
        this.statusId = statusId;
        this.type = type;
        this.mainStatus = mainStatus;
        this.mainStatusDesc = mainStatusDesc;
        this.standbyStatus = standbyStatus;
        this.standbyStatusDesc = standbyStatusDesc;
        this.mainStatusDtl = mainStatusDtl;
        this.standbyStatusDtl = standbyStatusDtl;
        if(mainStatusDtl!=null){
            switch (type){
                case 1:
                case 6:
                case 7:
                    this.mainStatusDtlJson = JSON.parseObject(mainStatusDtl);
                    break;
                case 3:
                case 4:
                    this.mainStatusDtlJson = JSON.parseArray(mainStatusDtl);
                    break;
            }
        }

        if(standbyStatusDtl!=null){
            switch (type){
                case 1:
                case 6:
                case 7:
                    this.standbyStatusDtlJson = JSON.parseObject(standbyStatusDtl);
                    break;
                case 3:
                case 4:
                    this.standbyStatusDtlJson = JSON.parseArray(standbyStatusDtl);
                    break;
            }
        }
    }
}