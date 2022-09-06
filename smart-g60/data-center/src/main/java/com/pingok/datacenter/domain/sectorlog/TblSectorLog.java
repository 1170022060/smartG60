package com.pingok.datacenter.domain.sectorlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 扇区日志表 TBL_SECTOR_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_SECTOR_LOG")
public class TblSectorLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(min = 0, max = 37, message = "GID长度不能超过37个字符")
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getPassType() {
        return passType;
    }

    public void setPassType(Integer passType) {
        this.passType = passType;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    @Size(min = 0, max = 256, message = "EF01文件长度不能超过256个字符")
    public String getObuVehicleInfo() {
        return obuVehicleInfo;
    }

    public void setObuVehicleInfo(String obuVehicleInfo) {
        this.obuVehicleInfo = obuVehicleInfo;
    }

    @Size(min = 0, max = 128, message = "0015文件长度不能超过128个字符")
    public String getFile0015() {
        return file0015;
    }

    public void setFile0015(String file0015) {
        this.file0015 = file0015;
    }

    @Size(min = 0, max = 128, message = "0019文件长度不能超过128个字符")
    public String getFile0019() {
        return file0019;
    }

    public void setFile0019(String file0019) {
        this.file0019 = file0019;
    }

    @Size(min = 0, max = 1024, message = "EF04文件长度不能超过1024个字符")
    public String getEf04() {
        return ef04;
    }

    public void setEf04(String ef04) {
        this.ef04 = ef04;
    }

    @Size(min = 0, max = 1024, message = "EF02文件长度不能超过1024个字符")
    public String getEf02() {
        return ef02;
    }

    public void setEf02(String ef02) {
        this.ef02 = ef02;
    }
}
