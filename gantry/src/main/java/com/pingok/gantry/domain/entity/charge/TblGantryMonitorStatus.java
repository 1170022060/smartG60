package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_monitor_status
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "tbl_gantry_monitor_status")
public class TblGantryMonitorStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 1 门架前端系统            2 门架后台系统 */
    private Integer frontSysFlag;

    /** 流水号=门架编号+批次号（2019082412）+批次号内序号（3位）            门架前端必填 */
    private String runStateId;

    /** 门架前端必填 */
    private String gantryId;

    /** YYYYMMDDHHMI */
    private String stateVersion;

    /** YYYY-MM-DDTHH:mm:ss */
    private Date stateTime;

    /** 1-主            2-备 */
    private Integer computerOrder;

    /** 数据类型 */
    private String frontRateVersion;

    /** 用分隔符“|”分隔            或者            前端运行参数版本号 */
    private String frontParamVersion;

    /** frontSysVersion */
    private String frontSysVersion;

    /** frontSoftVersion */
    private String frontSoftVersion;

    /** frontSoftState */
    private String frontSoftState;

    /** frontOverTradeCount */
    private Integer frontOverTradeCount;

    /** frontTransErrTradeCount */
    private Integer frontTransErrTradeCount;

    /** frontOverVehDataCount */
    private Integer frontOverVehDataCount;

    /** frontTransErrVehDataCount */
    private Integer frontTransErrVehDataCount;

    /** frontOverVehPicCount */
    private Integer frontOverVehPicCount;

    /** 前端传输异常图片流水数            或者            后台传输异常图片流水数            -1：无效数据 */
    private String frontTransErrVehPicCount;

    /** 前端硬件资源平均负载（5分钟）            单位：%，            或者            后台硬件资源平均负载（5分钟） */
    private String frontLoadAverage;

    /** 单位：MB，即/etc_data的总容量            或者            后台数据盘总容量 */
    private String frontDiskDataTotalSize;

    /** 单位：MB，即/etc_data的剩余容量            或者            后台数据盘剩余容量 */
    private String frontDiskDataLeftSize;

    /** 单位：MB，即/etc_run的总容量            或者            后台运行盘总容量 */
    private String frontDiskRunTotalSize;

    /** 单位：MB，即/etc_run的剩余容量            或者            后台运行盘剩余容量 */
    private String frontDiskRunLeftSize;

    /** 单位：%，0~100            或者后台CPU使用率 */
    private String frontcpurate;

    /** 单位：%，0~100            或者后台物理内存使用率 */
    private String frontMemoryRate;

    /** frontBeidouState */
    private Integer frontBeidouState;

    /** 按门架服务器所用的数据库系统品牌和版本进行填写 */
    private String backdbversion;

    /** frontErrorDataTotal */
    private String frontErrorDataTotal;

    /** frontErrorEventTotal */
    private String frontErrorEventTotal;

    /** 0-无响应            1-正常响应            2-无设备            涉及多个气象检测设备状态以“|”分隔，按照由中置车道向应急车道方向按序填写。             */
    private String weatherDetectorState;

    /** 气象检测设备软件版本号 */
    private String weatherDetectorVersion;

    /** 0-无响应            1-正常响应            2-无设备            涉及多个车型检测器设备状态以“|”分隔，按照由中置车道向应急车道方向按序填写。             */
    private String classDetectorState;

    /** 车型检测器软件版本号 */
    private String classDetectorVersion;

    /** 0-无响应            1-正常响应            2-无设备            涉及多个载重检测器设备状态以“|”分隔，按照由中置车道向应急车道方向按序填写。             */
    private String loadDetectorState;

    /** 载重检测器软件版本号 */
    private String loadDetectorVersion;

    /** 每次心跳间隔之间，通行的车流量 */
    private String vehicleCount;

    /** recvTime */
    private Date recvTime;

    /** 上传标记            0 未上传            2 未完全上传            3 上传完成 */
    private Integer uploadFlag;

    /** 上传时间            保存最终上传的时间 */
    private Date uploadTime;

    /** 归档标记            1 未归档            2 可归档 */
    private Integer lnduceFlag;

    /** 归档标记时间 */
    private Date lnduceTime;

    /** 0-无响应            1-正常响应            2-无设备            涉及多个温控设备状态以“|”分隔。 */
    private String tempControllerStatus;

    /** 0-无响应            1-正常响应            2-无设备            涉及多个供电设备状态以“|”分隔。 */
    private String powerControllerStatus;

    /** muploadflag */
    private Integer muploadflag;

    /** muploadtime */
    private Date muploadtime;

    /** ruploadflag */
    private String ruploadflag;

    /** ruploadtime */
    private Date ruploadtime;

    /** cuploadflag */
    private String cuploadflag;

    /** cuploadtime */
    private Date cuploadtime;

    /** 心跳时间 */
    private Date heartbeatTime;

    /** 心跳状态：0异常 1正常 */
    private Integer heartbeatStatus;

}