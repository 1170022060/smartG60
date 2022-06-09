package com.pingok.datacenter.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @time 2022/5/22 13:59
 */
@Table(name="TBL_GANTRY_INFO_DTL")
@Data
public class TblGantryInfoDtl implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 基础信息id
     */
    private Long infoId;

    /**
     * 类型：1-服务器 2-工控机 3-RSU 4-牌识 5-北斗授时 6-智能机柜 7-存储 8-全景相机 9-前端软件 10-后端软件
     */
    private Short type;

    /**
     * 主机IP
     */
    private String mainIp;

    /**
     * 备机IP
     */
    private String standbyIp;

    /**
     * 管理账户
     */
    private String loginName;

    /**
     * 管理密码
     */
    private String loginPsw;

    /**
     * 设备协议
     */
    private String protocol;

    // 多对一查询
    private TblGantryInfo tblGantryInfo;

    private static final long serialVersionUID = 1L;
}
