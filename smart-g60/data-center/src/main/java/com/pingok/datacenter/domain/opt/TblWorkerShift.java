package com.pingok.datacenter.domain.opt;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "TBL_WORKER_SHIFT")
public class TblWorkerShift implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long id;

    private String laneHex;

    private Integer optId;

    private Integer shift;

    private Date onTime;

    private Date offTime;

    private Date workDate;

    private Integer cash;

    private Integer chargeCard;

    private Integer storedCard;

    private Integer thirdPayment;

    private Integer unionPayCard;

    public Integer aliPay;

    public Integer weChat;

    public Integer busCard;

    public Integer digitalYuan;

    public Integer receiptCnt;

    public Integer cardCnt;

}
