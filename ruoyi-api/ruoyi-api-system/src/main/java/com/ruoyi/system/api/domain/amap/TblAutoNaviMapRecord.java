package com.ruoyi.system.api.domain.amap;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * TBL_AUTO_NAVI_MAP_RECORD
 * @author 
 */
@Data
public class TblAutoNaviMapRecord implements Serializable {
    private Long keyId;

    private String adcode;

    private String clientKey;

    private Integer timestamp;

    private String digest;

    private String sourceId;

    private Long id;

    private Integer stateFlag;

    private Integer type;

    private Integer locType;

    private String roadName;

    private String direction;

    private String locs;

    private Integer level;

    private String lanes;

    private Date startDate;

    private Date endDate;

    private String desc;

    private String callback;

    private String noticeCode;

    private String broadcastCode;

    private String picUrl;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Long createUserId;

    private Long updateUserId;

    private static final long serialVersionUID = 1L;

}