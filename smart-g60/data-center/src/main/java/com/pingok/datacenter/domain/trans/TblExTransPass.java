package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 出口流水通行信息表 TBL_EX_TRANS_PASS_年份
 *
 * @author ruoyi
 */
public class TblExTransPass {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long id;

    /** ETC：4发行方编号 +8卡号 + 8入口unix时间 CPC：”0000” + 8卡号 + 8入口unix时间 纸圈：”00” + 10车道Node十六进制码 + 8出口unix出口时间  */
    private String passId;

    /** 出口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** CPC卡ID */
    private String cpcCardId;

    /** CPC卡电量 */
    private Integer cpcElec;

    /** CPC卡车牌号 */
    private String cpcCardPlate;

    /** CPC卡车牌颜色 */
    private Integer cpcCardColor;

    /** CPC卡车型 */
    private Integer cpcCardVeh;

    /** CPC卡车情 */
    private Integer cpcCardVehStatus;

    /** CPC入口网络号 */
    private String cpcCardEnNet;

    /** CPC卡入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cpcCardEnTime;

    /** CPC卡入口站 */
    private String cpcCardEnStation;

    /** CPC卡过省数量 */
    private Integer cpcCardProCount;

    /** 长（2字节）宽（1字节）高（1字节） */
    private String vehLwh;

    /** ETC卡号 */
    private String etcCardId;

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

    /** 纸券入口网络号 */
    private String paperEnNet;

    /** 纸券入口站 */
    private String paperEnStation;

    /** 表名   */
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getCpcCardId() {
        return cpcCardId;
    }

    public void setCpcCardId(String cpcCardId) {
        this.cpcCardId = cpcCardId;
    }

    public Integer getCpcElec() {
        return cpcElec;
    }

    public void setCpcElec(Integer cpcElec) {
        this.cpcElec = cpcElec;
    }

    public String getCpcCardPlate() {
        return cpcCardPlate;
    }

    public void setCpcCardPlate(String cpcCardPlate) {
        this.cpcCardPlate = cpcCardPlate;
    }

    public Integer getCpcCardColor() {
        return cpcCardColor;
    }

    public void setCpcCardColor(Integer cpcCardColor) {
        this.cpcCardColor = cpcCardColor;
    }

    public Integer getCpcCardVeh() {
        return cpcCardVeh;
    }

    public void setCpcCardVeh(Integer cpcCardVeh) {
        this.cpcCardVeh = cpcCardVeh;
    }

    public Integer getCpcCardVehStatus() {
        return cpcCardVehStatus;
    }

    public void setCpcCardVehStatus(Integer cpcCardVehStatus) {
        this.cpcCardVehStatus = cpcCardVehStatus;
    }

    public String getCpcCardEnNet() {
        return cpcCardEnNet;
    }

    public void setCpcCardEnNet(String cpcCardEnNet) {
        this.cpcCardEnNet = cpcCardEnNet;
    }

    public Date getCpcCardEnTime() {
        return cpcCardEnTime;
    }

    public void setCpcCardEnTime(Date cpcCardEnTime) {
        this.cpcCardEnTime = cpcCardEnTime;
    }

    public String getCpcCardEnStation() {
        return cpcCardEnStation;
    }

    public void setCpcCardEnStation(String cpcCardEnStation) {
        this.cpcCardEnStation = cpcCardEnStation;
    }

    public Integer getCpcCardProCount() {
        return cpcCardProCount;
    }

    public void setCpcCardProCount(Integer cpcCardProCount) {
        this.cpcCardProCount = cpcCardProCount;
    }

    public String getVehLwh() {
        return vehLwh;
    }

    public void setVehLwh(String vehLwh) {
        this.vehLwh = vehLwh;
    }

    public String getEtcCardId() {
        return etcCardId;
    }

    public void setEtcCardId(String etcCardId) {
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

    public String getPaperEnNet() {
        return paperEnNet;
    }

    public void setPaperEnNet(String paperEnNet) {
        this.paperEnNet = paperEnNet;
    }

    public String getPaperEnStation() {
        return paperEnStation;
    }

    public void setPaperEnStation(String paperEnStation) {
        this.paperEnStation = paperEnStation;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
