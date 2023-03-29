package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * ETC出口流水通行信息表 TBL_EX_ETC_PASS_年份
 *
 * @author ruoyi
 */
public class TblExEtcPass implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 索引：与CPC、ETC、PAPER共用SEQ */
    @Id
    private Long id;

    private Long recordId;

    /** 长（2字节）宽（1字节）高（1字节） */
    private String vehLwh;

    /** ETC卡号 */
    private Long etcCardId;

    /** ETC卡版本号 */
    private Integer etcCardVer;

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

    /** ETC入口网络号 */
    private String etcCardEnNet;

    /** ETC卡入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date etcCardEnTime;

    /** ETC卡入口站 */
    private String etcCardEnStation;

    /** OBU号 */
    private String obuId;

    /** OBU版本号 */
    private Integer obuVer;

    /** OBU发行方标识 */
    private String obuProvider;

    /** OBU无卡次数 */
    private Integer obuNoCardTimes;

    /** OBU累计成功次数 */
    private Integer obuSuccTimes;

    /** OBU本省累计成功次数 */
    private Integer obuLocalSuccTimes;

    /** OBU卡车牌号 */
    private String obuPlate;

    /** OBU车牌颜色 */
    private Integer obuColor;

    /** OBU车型 */
    private Integer obuVeh;

    /** OBU入口网络号 */
    private String obuEnNet;

    /** OBU入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date obuEnTime;

    /** OBU入口站 */
    private String obuEnStation;

    /** 过省数量 */
    private Integer obuProCount;

    /** 交易前余额 */
    private Long balance;

    /** 电子钱包 脱机交易序号 */
    private Integer transNo;

    /** 终端 脱机交易序号 */
    private Long terminalTransId;

    /** 交易前余额 */
    private String mac;

    /** 交易金额 */
    private String tac;

    /** 交易花费总时间 */
    private Integer usedTime;

    /** 表名   */
    private String tableName;

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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

    public String getEtcCardEnNet() {
        return etcCardEnNet;
    }

    public void setEtcCardEnNet(String etcCardEnNet) {
        this.etcCardEnNet = etcCardEnNet;
    }

    public Date getEtcCardEnTime() {
        return etcCardEnTime;
    }

    public void setEtcCardEnTime(Date etcCardEnTime) {
        this.etcCardEnTime = etcCardEnTime;
    }

    public String getEtcCardEnStation() {
        return etcCardEnStation;
    }

    public void setEtcCardEnStation(String etcCardEnStation) {
        this.etcCardEnStation = etcCardEnStation;
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

    public Integer getObuNoCardTimes() {
        return obuNoCardTimes;
    }

    public void setObuNoCardTimes(Integer obuNoCardTimes) {
        this.obuNoCardTimes = obuNoCardTimes;
    }

    public Integer getObuSuccTimes() {
        return obuSuccTimes;
    }

    public void setObuSuccTimes(Integer obuSuccTimes) {
        this.obuSuccTimes = obuSuccTimes;
    }

    public Integer getObuLocalSuccTimes() {
        return obuLocalSuccTimes;
    }

    public void setObuLocalSuccTimes(Integer obuLocalSuccTimes) {
        this.obuLocalSuccTimes = obuLocalSuccTimes;
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

    public String getObuEnNet() {
        return obuEnNet;
    }

    public void setObuEnNet(String obuEnNet) {
        this.obuEnNet = obuEnNet;
    }

    public Date getObuEnTime() {
        return obuEnTime;
    }

    public void setObuEnTime(Date obuEnTime) {
        this.obuEnTime = obuEnTime;
    }

    public String getObuEnStation() {
        return obuEnStation;
    }

    public void setObuEnStation(String obuEnStation) {
        this.obuEnStation = obuEnStation;
    }

    public Integer getObuProCount() {
        return obuProCount;
    }

    public void setObuProCount(Integer obuProCount) {
        this.obuProCount = obuProCount;
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
