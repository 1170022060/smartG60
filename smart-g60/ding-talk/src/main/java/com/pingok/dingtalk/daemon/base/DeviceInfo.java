package com.pingok.dingtalk.daemon.base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * 设施设备信息表-钉钉
 * @author chenwg
 * @since 2023-04-13
 */
@Data
@Table(name ="TBL_DEVICE_INFO_DING")
@ApiModel(value="DeviceInfo对象", description="设施设备信息表-钉钉")
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Short id;


    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Short createUser;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    /**
     * 修改人id
     */
    @ApiModelProperty(value = "修改人id")
    private Short updateUser;


    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private BigDecimal status;


    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private BigDecimal isDeleted;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 设施设备分类ID
     */
    @ApiModelProperty(value = "设施设备分类ID")
    private Short pid;


    /**
     * 设施设备编号
     */
    @ApiModelProperty(value = "设施设备编号")
    private String facilityCode;


    /**
     * 设施设备名称
     */
    @ApiModelProperty(value = "设施设备名称")
    private String facilityName;


    /**
     * 计量单位键值
     */
    @ApiModelProperty(value = "计量单位键值")
    private String unitDictKey;


    /**
     * 位置
     */
    @ApiModelProperty(value = "位置")
    private String location;


    /**
     * 工程主管ID
     */
    @ApiModelProperty(value = "工程主管ID")
    private Short proChargeId;


    /**
     * 技术主管ID
     */
    @ApiModelProperty(value = "技术主管ID")
    private Short techChargeId;


    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;


    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;


    /**
     * 起点K数
     */
    @ApiModelProperty(value = "起点K数")
    private Short startKNumber;


    /**
     * 终点K数
     */
    @ApiModelProperty(value = "终点K数")
    private Short endKNumber;


    /**
     * 区域类别键值
     */
    @ApiModelProperty(value = "区域类别键值")
    private Short locationType;


    /**
     * 设施设备分类名称
     */
    @ApiModelProperty(value = "设施设备分类名称")
    private String categoryName;


    /**
     * 区域类别名称
     */
    @ApiModelProperty(value = "区域类别名称")
    private String locationTypeName;


    /**
     * 工程主管姓名
     */
    @ApiModelProperty(value = "工程主管姓名")
    private String proChargeName;


    /**
     * 技术主管姓名
     */
    @ApiModelProperty(value = "技术主管姓名")
    private String techChargeName;


    /**
     * 计量单位名称
     */
    @ApiModelProperty(value = "计量单位名称")
    private String unitDictKeyName;






}
