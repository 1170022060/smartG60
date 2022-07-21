package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 门架基础信息表(tbl_gantry_baseinfo)
 * 
 * @author bianj
 * @version 1.0.0 2021-12-10
 */
@Getter
@Setter
@Table(name = "tbl_gantry_baseinfo")
public class TblGantryBaseinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 1 门架前端系统            2 门架后台系统 */
    @Column(name = "FrontSysFlag")
    private Integer frontSysFlag;

    /** 基础信息流水号=收费门架编号+批次号+批次内流水号（3位） */
    @Column(name = "GantryBaseId")
    private String gantryBaseId;

    /** 1-主机；2-备机 */
    @Column(name = "ComputerOrder")
    private Integer computerOrder;

    /** gantryId */
    @Column(name = "GantryId")
    private String gantryId;

    /** YYYYMMDDHHMI */
    @Column(name = "StateVersion")
    private String stateVersion;

    /** 0-正向，1-反向 */
    @Column(name = "Direction")
    private Integer direction;

    /** 例如：上行：101，102，103；下行：201，202，203 */
    @Column(name = "GantryOrderNum")
    private Integer gantryOrderNum;

    /** stateTime */
    @Column(name = "StateTime")
    private Date stateTime;

    /** frontModel */
    @Column(name = "FrontModel")
    private String frontModel;

    /** 前端主工控机配置            或者            后台主服务器配置 */
    @Column(name = "FrontComputer")
    private String frontComputer;

    /** 前端主工控机系统及版本            或者            后台主服务器系统及版本 */
    @Column(name = "FrontSystemVersion")
    private String frontSystemVersion;

    /** 前端主工控机IP            或者            后台主服务器IP */
    @Column(name = "Frontip")
    private String frontip;

    /** 前端主工控机MAC            或者            后台主服务器MAC */
    @Column(name = "FrontMac")
    private String frontMac;

    /** 前端工控机HostName            或者            后台服务器hostname */
    @Column(name = "FrontHostName")
    private String frontHostName;

    /** 前端工控机VIP            或者            后端工控机VIP */
    @Column(name = "Frontvip")
    private String frontvip;

    /** 前端或后端服务异常数            未修复、已修复、不可修复以“|”分隔 */
    @Column(name = "FrontErrorDataTotal")
    private String frontErrorDataTotal;

    /** 前端或后端服务异常事件总数            模块以”|”分割 */
    @Column(name = "FrontErrorEventTotal")
    private String frontErrorEventTotal;

    /** 前端时间同步服务器地址            或者            后台时间同步服务器地址 */
    @Column(name = "FrontTimeServer")
    private String frontTimeServer;

    /** 后台主服务器数据库软件版本            按门架服务器所用的数据库系统品牌和版本进行填写 */
    @Column(name = "Backdbversion")
    private String backdbversion;

    /** 后台的服务地址 */
    @Column(name = "BackServerAddress")
    private String backServerAddress;

    /** 门架的高清摄像机设备型号 */
    @Column(name = "Hdvmodel")
    private String hdvmodel;

    /** 门架的车辆检测器设备型号 */
    @Column(name = "VehicleDetectorModel")
    private String vehicleDetectorModel;

    /** 门架的气象检测设备型号 */
    @Column(name = "WeatherDetectorModel")
    private String weatherDetectorModel;

    /** 门架的车型检测设备型号 */
    @Column(name = "ClassDetectorModel")
    private String classDetectorModel;

    /** 门架的断面称重检测设备型号 */
    @Column(name = "LoadDetectionModel")
    private String loadDetectionModel;

    /** 门架的温控设备型号 */
    @Column(name = "TempControllerModel")
    private String tempControllerModel;

    /** 门架的供电设备型号 */
    @Column(name = "PowerControllerModel")
    private String powerControllerModel;

    /** 安全接入设备型号 */
    @Column(name = "SafeEquipModel")
    private String safeEquipModel;

    /** recvTime */
    @Column(name = "RecvTime")
    private Date recvTime;

    /** 上传标记            0 未上传            2 未完全上传            3 上传完成 */
    @Column(name = "UploadFlag")
    private Integer uploadFlag;

    /** 上传时间            保存最终上传的时间 */
    @Column(name = "UploadTime")
    private Date uploadTime;

    /** 归档标记            1 未归档            2 可归档 */
    @Column(name = "LnduceFlag")
    private Integer lnduceFlag;

    /** 归档标记时间 */
    @Column(name = "LnduceTime")
    private Date lnduceTime;

    /** muploadflag */
    @Column(name = "Muploadflag")
    private Integer muploadflag;

    /** muploadtime */
    @Column(name = "Muploadtime")
    private Date muploadtime;

    /** ruploadflag */
    @Column(name = "Ruploadflag")
    private String ruploadflag;

    /** ruploadtime */
    @Column(name = "Ruploadtime")
    private Date ruploadtime;

    /** cuploadflag */
    @Column(name = "Cuploadflag")
    private String cuploadflag;

    /** cuploadtime */
    @Column(name = "Cuploadtime")
    private Date cuploadtime;

}