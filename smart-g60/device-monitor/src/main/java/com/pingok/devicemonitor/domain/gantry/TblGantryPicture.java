package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * @author
 * @time 2022/5/20 17:56
 */
@Data
@Table(name = "TBL_GANTRY_PICTURE")
public class TblGantryPicture implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** picId */
    @Id
    private String picId;

    /** gantryId */
    private String gantryId;

    private String vehiclePlate;

    /** image */
    private String image;

    /** licenseImage */
    private String licenseImage;

    /** binImage */
    private String binImage;

    /** picTime */
    private Date picTime;

}
