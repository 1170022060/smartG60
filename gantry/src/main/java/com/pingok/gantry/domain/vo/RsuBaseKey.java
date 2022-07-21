package com.pingok.gantry.domain.vo;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RsuBaseKey implements Serializable {

    /** ETC */
    private String gantryId;

    /** RSU */
    private String controlId;


    /** stateVersion */
    private String stateVersion;

}
