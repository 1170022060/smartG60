package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_state_collect
 * @author 
 */
@Getter
@Setter
@Table(name = "tbl_gantry_state_collect")
public class TblGantryStateCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 门架编号
     */
    private String gantryId;

    /**
     * 状态日期
     */
    private Date date;

    /**
     * 状态-1异常 0未知 1正常
     */
    private String status;

    /**
     * 主工控机状态 -1异常 0未知 1正常
     */
    private String frontSoftStateMasterIpc;

    /**
     * 备工控机状态 -1异常 0未知 1正常
     */
    private String frontSoftStateStandbyIpc;

    /**
     * 主服务器状态 -1异常 0未知 1正常
     */
    private String frontSoftStateMasterServer;

    /**
     * 备服务器状态 -1异常 0未知 1正常
     */
    private String frontSoftStateStandbyServer;

    /**
     * 主工控机北斗状态 -1异常 0未知 1正常
     */
    private String frontBeidouStateMasterIpc;

    /**
     * 备工控机北斗状态 -1异常 0未知 1正常
     */
    private String frontBeidouStateStandbyIpc;

    /**
     * 主服务器北斗状态 -1异常 0未知 1正常
     */
    private String frontBeidouStateMasterServer;

    /**
     * 备服务器北斗状态 -1异常 0未知 1正常
     */
    private String frontBeidouStateStandbyServer;

    /**
     * RSU总状态-1异常 0未知 1正常
     */
    private String rsuState;

    /**
     * 牌识总状态-1异常 0未知 1正常
     */
    private String vplrState;

    /**
     * RSU状态明细
     */
    private String rsuStateDtl;

    /**
     * 牌识状态明细
     */
    private String vplrStateDtl;
    private Integer transactionNumber;
    private Integer travelimageNumber;

    private Integer type;


    /** 单位：MB，即/etc_data的剩余容量            或者            后台数据盘剩余容量 */
    private String frontDiskDataLeftSize;


    /** 单位：MB，即/etc_run的剩余容量            或者            后台运行盘剩余容量 */
    private String frontDiskRunLeftSize;

    /** 单位：MB，即/etc_data的剩余容量            或者            后台数据盘剩余容量 */
    private String frontDiskDataLeftSizeBack;


    /** 单位：MB，即/etc_run的剩余容量            或者            后台运行盘剩余容量 */
    private String frontDiskRunLeftSizeBack;
}