package com.pingok.datacenter.domain.gantry;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "TBL_GANTRY_CHARGE_INFO")
@Getter
@Setter
public class TblGantryChargeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;


}
