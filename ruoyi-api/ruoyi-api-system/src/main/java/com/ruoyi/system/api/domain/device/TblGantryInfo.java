package com.ruoyi.system.api.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 门架设备基础信息表 TBL_GANTRY_INFO
 *
 * @author ruoyi
 */
@Data
public class TblGantryInfo {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private Long itemName;

    /** 立项时间 */
    @Excel(name = "立项时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date itemListTime;

    /** 项目类目 */
    @Excel(name = "项目类目")
    private Long deviceCategory;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备品牌 */
    @Excel(name = "设备品牌")
    private String deviceBrand;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String deviceModel;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String techPara;

    /** 厂商 */
    @Excel(name = "厂商")
    private String manufacturer;

    /** 购买日期 */
    @Excel(name = "购买日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 购买日期 */
    @Excel(name = "安装日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date installDate;

    /** 质保期限(月) */
    @Excel(name = "质保期限(月)")
    private Integer warrantyPeriod;

    /** 使用期限(月) */
    @Excel(name = "使用期限(月)")
    private Integer serviceLife;

    /** 管理方 */
    @Excel(name = "管理方")
    private Long managementSide;

    /** 使用方 */
    @Excel(name = "使用方")
    private Long userSide;

    /** 行政区域 */
    @Excel(name = "行政区域")
    private String adminArea;

    /** 起始路名 */
    @Excel(name = "起始路名")
    private String startRoad;

    /** 安装地点 */
    @Excel(name = "安装地点")
    private Long fieldBelong;

    /** 城建坐标 */
    @Excel(name = "城建坐标")
    private String urbanConstrCoordin;


    /** 桩号 */
    @Excel(name = "桩号")
    private String pileNo;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private Integer status;

    /** 设备照片 */
    @Excel(name = "设备照片")
    private String devicePhoto;

    /** 养护标准 */
    @Excel(name = "养护标准")
    private String maintainStandard;

    /** 所属路段 */
    @Excel(name = "所属路段")
    private String roadBelong;

    /** 所属站 */
    @Excel(name = "所属站")
    private String stationBelong;


    /** 方向：1.上行 2.下行 */
    @Excel(name = "方向",readConverterExp = "1=上行,2=下行")
    private Integer direction;


    /** 监控级别：1.中心 2.集中站 3.收费站 */
    @Excel(name = "监控级别",readConverterExp = "1=中心,2=集中站,3=收费站")
    private Integer monitorLevel;

    /** GPS位置 */
    @Excel(name = "GPS位置")
    private String gps;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;

    /** 备注*/
    @Excel(name = "备注")
    private String remark;

    /** 门架监管车道数*/
    @Excel(name = "门架监管车道数")
    private Integer laneCount;


}
