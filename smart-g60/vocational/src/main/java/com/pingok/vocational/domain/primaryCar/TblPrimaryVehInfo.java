package com.pingok.vocational.domain.primaryCar;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 两客一危车辆信息表(TBL_PRIMARY_VEH_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2022-12-12
 */
@Data
@Table(name = "TBL_PRIMARY_VEH_INFO")
public class TblPrimaryVehInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1931516126504881679L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 车辆基本id */
    @Id
    private BigDecimal id;

    /** 行业ID */
    private BigDecimal tradeId;

    /** 车辆营运证/道路运输证 */
    private BigDecimal licenseNumber;

    /** 经营范围ID */
    private BigDecimal scopeId;

    /** 车牌号码 */
    private String vehicleLicense;

    /** 车牌颜色编码 */
    private BigDecimal licenseSubject;

    /** 车辆状态编码 */
    private BigDecimal status;

    /** 车架号 */
    private String frameNumber;

    /** 车辆出厂日期 */
    private Date manufactureDate;

    /** 车辆取得营运证/道路运输证日期 */
    private Date operateDate;

    /** 行驶证初领日期 */
    private Date drivingLicenseFirstDate;

    /** 车辆类型代码 */
    private BigDecimal vehicleClass;

    /** 车身颜色 */
    private BigDecimal color;

    /** 燃料类型代码 */
    private BigDecimal fuel;

    /** 厂家品牌名称 */
    private String brand;

    /** 车辆型号 */
    private String model;

    /** 车身长度 毫米 */
    private BigDecimal bodyLength;

    /** 车身宽度 毫米 */
    private BigDecimal bodyWidth;

    /** 核定载质量 */
    private BigDecimal approvedQuality;

    /** 车座总数 */
    private BigDecimal seat;

    /** 轴距 */
    private BigDecimal wheelBase;

    /** 后悬 */
    private BigDecimal suspension;

    /** 整备质量 */
    private BigDecimal emptyWeight;

    /** 总质量 */
    private BigDecimal grossMass;

    /** 轴数 */
    private BigDecimal axle;

    /** 发动机号 */
    private String engineNumber;

    /** 发动机型号 */
    private String engineType;

    /** 发动机额定功率 */
    private BigDecimal power;

    /** 发动机排量 */
    private BigDecimal displacement;

    /** 审批/备案机构编码 */
    private String approvalAuthority;

    /** 具体管理部门编码 */
    private String managerDepartmanet;

    /** 有无车载GPS */
    private String gps;

    /** GPS终端号码 */
    private String gpsNum;

    /** GPS运营商 */
    private String gpsOperator;

    /** 有无车载DVR */
    private String dvr;

    /** 行车记录仪设备编号 */
    private String tachographNum;

    /** 行车记录仪设备供应商 */
    private String tachographOperator;

    /** 证照有效期起 */
    private Date beginValidDate;

    /** 证照有效期止 */
    private Date endValidDate;

    /** 审验年度 */
    private String nmugYear;

    /** 年审状态 */
    private String numgStatus;

    /** 车辆营运证/道路运输证初领日期 */
    private Date initialDate;

    /** 注销日期 */
    private Date cancelLationDate;

    /** 注销原因 */
    private String cancelLationReason;

    /** 是否逻辑删除 */
    private String isRemove;

    /** VIN码 */
    private String vin;

    /** 同步时间 */
    private Date dateForAdd;

    /** 证照有效期止 */
    private Date licenseValid;

    /** 车牌颜色 */
    private BigDecimal licenseColor;

    /** 燃料类型 */
    private BigDecimal fuelType;

    /** 车辆类型 */
    private BigDecimal vehicleType;

    /** 车辆经营范围 */
    private BigDecimal vehicleScope;

    /** 行业业态 */
    private String trade;

    /** 企业基本id */
    private BigDecimal enterPriseId;

    /** 所属区属 */
    private BigDecimal district;

    /** 管理单位 */
    private String managementUnit;

}