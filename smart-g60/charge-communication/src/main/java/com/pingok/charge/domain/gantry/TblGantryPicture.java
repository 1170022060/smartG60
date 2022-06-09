package com.pingok.charge.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_picture
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
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