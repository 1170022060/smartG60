package com.pingok.gantry.domain.entity.gantry;

import com.pingok.gantry.domain.vo.GantryBaseKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * dfs_gantry_baseinfo
 * 
 * @author bianj
 * @version 1.0.0 2021-12-10
 */
@Getter
@Setter
@Table(name = "dfs_gantry_baseinfo")
public class DfsGantryBaseinfo implements Serializable {

    private static final long serialVersionUID = 1L;

//    @EmbeddedId
//    private GantryBaseKey id;

    /**
     * 1 门架前端系统
     2 门架后台系统
     */
    private Integer frontSysFlag;

    private String GantryBaseId;

    private String gantryId;

    /**
     * YYYYMMDDHHMI
     */
    private String stateVersion;

    /**
     * 0-正向，1-反向
     */
    private Integer direction;

    /**
     * 例如：上行：101，102，103；下行：201，202，203
     */
    private Integer gantryOrderNum;

    private Integer computerOrder;

    private Date stateTime;

    private String frontModel;

    /**
     * 前端主工控机配置
     或者
     后台主服务器配置
     */
    private String frontComputer;

    /**
     * 前端主工控机系统及版本
     或者
     后台主服务器系统及版本
     */
    private String frontSystemVersion;

    /**
     * 前端主工控机IP
     或者
     后台主服务器IP
     */
    private String frontIP;

    /**
     * 前端主工控机MAC
     或者
     后台主服务器MAC
     */
    private String frontMac;

    /**
     * 前端工控机HostName
     或者
     后台服务器hostname
     */
    private String frontHostName;

    /**
     * 前端工控机VIP
     或者
     后端工控机VIP
     */
    private String frontVIP;

    /**
     * 前端或后端服务异常数
     未修复、已修复、不可修复以“|”分隔
     */
    private String frontErrorDataTotal;

    /**
     * 前端或后端服务异常事件总数
     模块以”|”分割
     */
    private String frontErrorEventTotal;

    /**
     * 前端时间同步服务器地址
     或者
     后台时间同步服务器地址
     */
    private String frontTimeServer;

    /**
     * 后台主服务器数据库软件版本
     按门架服务器所用的数据库系统品牌和版本进行填写
     */
    private String backDBVersion;

    /**
     * 后台的服务地址
     */
    private String backServerAddress;

    /**
     * 门架的高清摄像机设备型号
     */
    private String HDVModel;

    /**
     * 门架的车辆检测器设备型号
     */
    private String vehicleDetectorModel;

    /**
     * 门架的气象检测设备型号
     */
    private String weatherDetectorModel;

    /**
     * 门架的车型检测设备型号
     */
    private String classDetectorModel;

    /**
     * 门架的断面称重检测设备型号
     */
    private String loadDetectionModel;

    /**
     * 门架的温控设备型号
     */
    private String tempControllerModel;

    /**
     * 门架的供电设备型号
     */
    private String powerControllerModel;

    /**
     * 安全接入设备型号
     */
    private String safeEquipModel;

    private Date recvTime;

    /**
     * 上传标记
     0 未上传
     2 未完全上传
     3 上传完成
     */
    private Integer uploadFlag;

    /**
     * 上传时间
     保存最终上传的时间
     */
    private Date uploadTime;

    /**
     * 归档标记
     1 未归档
     2 可归档
     */
    private Integer lnduceFlag;

    /**
     * 归档标记时间
     */
    private Date lnduceTime;

    private Integer MUploadFlag;

    private Date MUploadTime;

//    private String RUploadFlag;
//
//    private Date RUploadTime;
//
//    private String CUploadFlag;
//
//    private Date CUploadTime;

}