package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 入口流水通行信息表 TBL_EN_TRANS_PASS_年份
 *
 * @author ruoyi
 */
public class TblEnEtcPass implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ETC卡号 */
    private Long etcCardId;

    /** ETC卡版本号 */
    private Integer etcCardVer;

    /** 入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 索引：与CPC、ETC共用SEQ */
    private Long recordId;

    /** 长（2字节）宽（1字节）高（1字节） */
    private String vehLwh;

    /** ETC卡网络号 */
    private String etcCardNet;

    /** ETC卡发行方标识 */
    private String etcCardProvider;

    /** ETC卡车牌号 */
    private String etcCardPlate;

    /** ETC卡车牌颜色 */
    private Integer etcCardColor;

    /** ETC卡车型 */
    private Integer etcCardVeh;

    /** ETC卡类型 */
    private Integer etcCardType;

    /** OBU号 */
    private String obuId;

    /** OBU版本号 */
    private Integer obuVer;

    /** OBU发行方标识 */
    private String obuProvider;

    /** OBU卡车牌号 */
    private String obuPlate;

    /** OBU车牌颜色 */
    private Integer obuColor;

    /** OBU车型 */
    private Integer obuVeh;

    /** 交易前余额 */
    private Long balance;

    /** 电子钱包 脱机交易序号 */
    private Integer transNo;

    /** 终端 脱机交易序号 */
    private Long terminalTransId;

    /** MAC */
    private String mac;

    /** TAC */
    private String tac;

    /** 交易花费总时间 */
    private Integer usedTime;

    /** 表名   */
    private String tableName;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getVehLwh() {
        return vehLwh;
    }

    public void setVehLwh(String vehLwh) {
        this.vehLwh = vehLwh;
    }

    public Long getEtcCardId() {
        return etcCardId;
    }

    public void setEtcCardId(Long etcCardId) {
        this.etcCardId = etcCardId;
    }

    public Integer getEtcCardVer() {
        return etcCardVer;
    }

    public void setEtcCardVer(Integer etcCardVer) {
        this.etcCardVer = etcCardVer;
    }

    public String getEtcCardNet() {
        return etcCardNet;
    }

    public void setEtcCardNet(String etcCardNet) {
        this.etcCardNet = etcCardNet;
    }

    public String getEtcCardProvider() {
        return etcCardProvider;
    }

    public void setEtcCardProvider(String etcCardProvider) {
        this.etcCardProvider = etcCardProvider;
    }

    public String getEtcCardPlate() {
        return etcCardPlate;
    }

    public void setEtcCardPlate(String etcCardPlate) {
        this.etcCardPlate = etcCardPlate;
    }

    public Integer getEtcCardColor() {
        return etcCardColor;
    }

    public void setEtcCardColor(Integer etcCardColor) {
        this.etcCardColor = etcCardColor;
    }

    public Integer getEtcCardVeh() {
        return etcCardVeh;
    }

    public void setEtcCardVeh(Integer etcCardVeh) {
        this.etcCardVeh = etcCardVeh;
    }

    public Integer getEtcCardType() {
        return etcCardType;
    }

    public void setEtcCardType(Integer etcCardType) {
        this.etcCardType = etcCardType;
    }

    public String getObuId() {
        return obuId;
    }

    public void setObuId(String obuId) {
        this.obuId = obuId;
    }

    public Integer getObuVer() {
        return obuVer;
    }

    public void setObuVer(Integer obuVer) {
        this.obuVer = obuVer;
    }

    public String getObuProvider() {
        return obuProvider;
    }

    public void setObuProvider(String obuProvider) {
        this.obuProvider = obuProvider;
    }

    public String getObuPlate() {
        return obuPlate;
    }

    public void setObuPlate(String obuPlate) {
        this.obuPlate = obuPlate;
    }

    public Integer getObuColor() {
        return obuColor;
    }

    public void setObuColor(Integer obuColor) {
        this.obuColor = obuColor;
    }

    public Integer getObuVeh() {
        return obuVeh;
    }

    public void setObuVeh(Integer obuVeh) {
        this.obuVeh = obuVeh;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getTransNo() {
        return transNo;
    }

    public void setTransNo(Integer transNo) {
        this.transNo = transNo;
    }

    public Long getTerminalTransId() {
        return terminalTransId;
    }

    public void setTerminalTransId(Long terminalTransId) {
        this.terminalTransId = terminalTransId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
