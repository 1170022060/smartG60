package com.pingok.datacenter.domain.sectorlog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;
@Data
public class SectorLogVo {

    /** GID */
    @Excel(name = "GID")
    private String gid;

    /** 通行介质 5 ETC通行，6 CPC卡，254纸券 */
    @Excel(name = "通行介质")
    private Integer passType;

    /** 出口时间 */
    @Excel(name = "出口时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** ObuEF01,ETC通行必填 */
    @Excel(name = "ObuEF01,ETC通行必填")
    private String obuVehicleInfo;

    /** CPU用户卡0015文件信息,ETC通行必填 */
    @Excel(name = "CPU用户卡0015文件信息,ETC通行必填")
    private String file0015;

    /** CPU用户卡0019文件信息,ETC通行必填 */
    @Excel(name = "CPU用户卡0019文件信息,ETC通行必填")
    private String file0019;

    /** OBU/CPC EF04文件内容 */
    @Excel(name = "OBU/CPC EF04文件内容")
    private String ef04;

    /** CPC卡EF02文件内容 */
    @Excel(name = "CPC卡EF02文件内容")
    private String ef02;
}
