package com.pingok.vocational.domain.roster;

import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 追讨名单信息表(TBL_PURSUES_LIST_RECORD)
 *
 * @author xia
 * @version 1.0.0 2022-06-15
 */
@Table(name = "TBL_PURSUES_LIST_RECORD")
public class TblPursuesListRecord implements java.io.Serializable {

    /** 版本号 */
    private static final long serialVersionUID = 3658730533557698374L;

    /** 协调ID */
    @Excel(name = "协调ID")
    @Id
    private String concertId;

    /** passId */
    @Excel(name = "passId")
    private String passId;

    /** 发起方省中心 */
    @Excel(name = "发起方省中心")
    private String initiatorProv;

    /** 路段 */
    @Excel(name = "路段")
    private String road;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String vehPlate;

    /** 车牌颜色 */
    @Excel(name = "车牌颜色")
    private String vehColor;

    /** ETC卡号 */
    @Excel(name = "ETC卡号")
    private String etcCardId;

    /** 通行介质 */
    @Excel(name = "通行介质")
    private String mediaType;

    /** 介质编号 */
    @Excel(name = "介质编号")
    private String mediaNo;

    /** 涉及金额（元） */
    @Excel(name = "涉及金额（元）")
    private BigDecimal fee;

    /** 计费终点时间 */
    @Excel(name = "计费终点时间")
    private Date endTime;

    /** 协调提交时间 */
    @Excel(name = "协调提交时间")
    private Date submitTime;

    /** 状态更新时间 */
    @Excel(name = "状态更新时间")
    private Date statusUpdateTime;

    /** 路段更新时间 */
    @Excel(name = "路段更新时间")
    private Date roadUpdateTime;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String vehClass;

    /** 最后通行门架编号(部) */
    @Excel(name = "最后通行门架编号(部)")
    private String lastGantryDe;

    /** 最后通行门架编号(省) */
    @Excel(name = "最后通行门架编号(省)")
    private String lastGantryProv;

    /** 最后通行门架时间 */
    @Excel(name = "最后通行门架时间")
    private Date lastGantryTime;

    /** 流转状态 */
    @Excel(name = "流转状态")
    private String circulationStatus;

    /** 上传状态 */
    @Excel(name = "上传状态")
    private String uploadStatus;

    /** 上传状态描述 */
    @Excel(name = "上传状态描述")
    private String uploadDescribe;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    /** 处理状态 */
    @Excel(name = "处理状态")
    private String handleStatus;

    /** 举证出口省份 */
    @Excel(name = "举证出口省份")
    private String proofProv;

    /** 协调结束时间 */
    @Excel(name = "协调结束时间")
    private String concertEndTime;

    /** 工单剩余时间(小时) */
    @Excel(name = "工单剩余时间")
    private String remainTime;

    /** 路段处理剩余时间 */
    @Excel(name = "路段处理剩余时间")
    private String handleRemainTime;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String operateType;

    /** 操作说明 */
    @Excel(name = "操作说明")
    private String operateExplain;

    /** 出口passId */
    @Excel(name = "出口passId")
    private String realPassId;

    /** 优惠类型 */
    @Excel(name = "优惠类型")
    private String discountType;

    /** 退回类型 */
    @Excel(name = "退回类型")
    private String backType;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /**
     * 获取协调ID
     *
     * @return 协调ID
     */
    public String getConcertId() {
        return this.concertId;
    }

    /**
     * 设置协调ID
     *
     * @param concertId
     *          协调ID
     */
    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    /**
     * 获取passId
     *
     * @return passId
     */
    public String getPassId() {
        return this.passId;
    }

    /**
     * 设置passId
     *
     * @param passId
     *          passId
     */
    public void setPassId(String passId) {
        this.passId = passId;
    }

    /**
     * 获取发起方省中心
     *
     * @return 发起方省中心
     */
    public String getInitiatorProv() {
        return this.initiatorProv;
    }

    /**
     * 设置发起方省中心
     *
     * @param initiatorProv
     *          发起方省中心
     */
    public void setInitiatorProv(String initiatorProv) {
        this.initiatorProv = initiatorProv;
    }

    /**
     * 获取路段
     *
     * @return 路段
     */
    public String getRoad() {
        return this.road;
    }

    /**
     * 设置路段
     *
     * @param road
     *          路段
     */
    public void setRoad(String road) {
        this.road = road;
    }

    /**
     * 获取车牌号
     *
     * @return 车牌号
     */
    public String getVehPlate() {
        return this.vehPlate;
    }

    /**
     * 设置车牌号
     *
     * @param vehPlate
     *          车牌号
     */
    public void setVehPlate(String vehPlate) {
        this.vehPlate = vehPlate;
    }

    /**
     * 获取车牌颜色
     *
     * @return 车牌颜色
     */
    public String getVehColor() {
        return this.vehColor;
    }

    /**
     * 设置车牌颜色
     *
     * @param vehColor
     *          车牌颜色
     */
    public void setVehColor(String vehColor) {
        this.vehColor = vehColor;
    }

    /**
     * 获取ETC卡号
     *
     * @return ETC卡号
     */
    public String getEtcCardId() {
        return this.etcCardId;
    }

    /**
     * 设置ETC卡号
     *
     * @param etcCardId
     *          ETC卡号
     */
    public void setEtcCardId(String etcCardId) {
        this.etcCardId = etcCardId;
    }

    /**
     * 获取通行介质
     *
     * @return 通行介质
     */
    public String getMediaType() {
        return this.mediaType;
    }

    /**
     * 设置通行介质
     *
     * @param mediaType
     *          通行介质
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * 获取介质编号
     *
     * @return 介质编号
     */
    public String getMediaNo() {
        return this.mediaNo;
    }

    /**
     * 设置介质编号
     *
     * @param mediaNo
     *          介质编号
     */
    public void setMediaNo(String mediaNo) {
        this.mediaNo = mediaNo;
    }

    /**
     * 获取涉及金额（元）
     *
     * @return 涉及金额
     */
    public BigDecimal getFee() {
        return this.fee;
    }

    /**
     * 设置涉及金额（元）
     *
     * @param fee
     *          涉及金额
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取计费终点时间
     *
     * @return 计费终点时间
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * 设置计费终点时间
     *
     * @param endTime
     *          计费终点时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取协调提交时间
     *
     * @return 协调提交时间
     */
    public Date getSubmitTime() {
        return this.submitTime;
    }

    /**
     * 设置协调提交时间
     *
     * @param submitTime
     *          协调提交时间
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 获取状态更新时间
     *
     * @return 状态更新时间
     */
    public Date getStatusUpdateTime() {
        return this.statusUpdateTime;
    }

    /**
     * 设置状态更新时间
     *
     * @param statusUpdateTime
     *          状态更新时间
     */
    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    /**
     * 获取路段更新时间
     *
     * @return 路段更新时间
     */
    public Date getRoadUpdateTime() {
        return this.roadUpdateTime;
    }

    /**
     * 设置路段更新时间
     *
     * @param roadUpdateTime
     *          路段更新时间
     */
    public void setRoadUpdateTime(Date roadUpdateTime) {
        this.roadUpdateTime = roadUpdateTime;
    }

    /**
     * 获取车辆类型
     *
     * @return 车辆类型
     */
    public String getVehClass() {
        return this.vehClass;
    }

    /**
     * 设置车辆类型
     *
     * @param vehClass
     *          车辆类型
     */
    public void setVehClass(String vehClass) {
        this.vehClass = vehClass;
    }

    /**
     * 获取最后通行门架编号(部)
     *
     * @return 最后通行门架编号
     */
    public String getLastGantryDe() {
        return this.lastGantryDe;
    }

    /**
     * 设置最后通行门架编号(部)
     *
     * @param lastGantryDe
     *          最后通行门架编号
     */
    public void setLastGantryDe(String lastGantryDe) {
        this.lastGantryDe = lastGantryDe;
    }

    /**
     * 获取最后通行门架编号(省)
     *
     * @return 最后通行门架编号
     */
    public String getLastGantryProv() {
        return this.lastGantryProv;
    }

    /**
     * 设置最后通行门架编号(省)
     *
     * @param lastGantryProv
     *          最后通行门架编号
     */
    public void setLastGantryProv(String lastGantryProv) {
        this.lastGantryProv = lastGantryProv;
    }

    /**
     * 获取最后通行门架时间
     *
     * @return 最后通行门架时间
     */
    public Date getLastGantryTime() {
        return this.lastGantryTime;
    }

    /**
     * 设置最后通行门架时间
     *
     * @param lastGantryTime
     *          最后通行门架时间
     */
    public void setLastGantryTime(Date lastGantryTime) {
        this.lastGantryTime = lastGantryTime;
    }

    /**
     * 获取流转状态
     *
     * @return 流转状态
     */
    public String getCirculationStatus() {
        return this.circulationStatus;
    }

    /**
     * 设置流转状态
     *
     * @param circulationStatus
     *          流转状态
     */
    public void setCirculationStatus(String circulationStatus) {
        this.circulationStatus = circulationStatus;
    }

    /**
     * 获取上传状态
     *
     * @return 上传状态
     */
    public String getUploadStatus() {
        return this.uploadStatus;
    }

    /**
     * 设置上传状态
     *
     * @param uploadStatus
     *          上传状态
     */
    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    /**
     * 获取上传状态描述
     *
     * @return 上传状态描述
     */
    public String getUploadDescribe() {
        return this.uploadDescribe;
    }

    /**
     * 设置上传状态描述
     *
     * @param uploadDescribe
     *          上传状态描述
     */
    public void setUploadDescribe(String uploadDescribe) {
        this.uploadDescribe = uploadDescribe;
    }

    /**
     * 获取处理结果
     *
     * @return 处理结果
     */
    public String getHandleResult() {
        return this.handleResult;
    }

    /**
     * 设置处理结果
     *
     * @param handleResult
     *          处理结果
     */
    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    /**
     * 获取处理状态
     *
     * @return 处理状态
     */
    public String getHandleStatus() {
        return this.handleStatus;
    }

    /**
     * 设置处理状态
     *
     * @param handleStatus
     *          处理状态
     */
    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 获取举证出口省份
     *
     * @return 举证出口省份
     */
    public String getProofProv() {
        return this.proofProv;
    }

    /**
     * 设置举证出口省份
     *
     * @param proofProv
     *          举证出口省份
     */
    public void setProofProv(String proofProv) {
        this.proofProv = proofProv;
    }

    /**
     * 获取协调结束时间
     *
     * @return 协调结束时间
     */
    public String getConcertEndTime() {
        return this.concertEndTime;
    }

    /**
     * 设置协调结束时间
     *
     * @param concertEndTime
     *          协调结束时间
     */
    public void setConcertEndTime(String concertEndTime) {
        this.concertEndTime = concertEndTime;
    }

    /**
     * 获取工单剩余时间(小时)
     *
     * @return 工单剩余时间
     */
    public String getRemainTime() {
        return this.remainTime;
    }

    /**
     * 设置工单剩余时间(小时)
     *
     * @param remainTime
     *          工单剩余时间
     */
    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    /**
     * 获取路段处理剩余时间
     *
     * @return 路段处理剩余时间
     */
    public String getHandleRemainTime() {
        return this.handleRemainTime;
    }

    /**
     * 设置路段处理剩余时间
     *
     * @param handleRemainTime
     *          路段处理剩余时间
     */
    public void setHandleRemainTime(String handleRemainTime) {
        this.handleRemainTime = handleRemainTime;
    }

    /**
     * 获取操作类型
     *
     * @return 操作类型
     */
    public String getOperateType() {
        return this.operateType;
    }

    /**
     * 设置操作类型
     *
     * @param operateType
     *          操作类型
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    /**
     * 获取操作说明
     *
     * @return 操作说明
     */
    public String getOperateExplain() {
        return this.operateExplain;
    }

    /**
     * 设置操作说明
     *
     * @param operateExplain
     *          操作说明
     */
    public void setOperateExplain(String operateExplain) {
        this.operateExplain = operateExplain;
    }

    /**
     * 获取出口passId
     *
     * @return 出口passId
     */
    public String getRealPassId() {
        return this.realPassId;
    }

    /**
     * 设置出口passId
     *
     * @param realPassId
     *          出口passId
     */
    public void setRealPassId(String realPassId) {
        this.realPassId = realPassId;
    }

    /**
     * 获取优惠类型
     *
     * @return 优惠类型
     */
    public String getDiscountType() {
        return this.discountType;
    }

    /**
     * 设置优惠类型
     *
     * @param discountType
     *          优惠类型
     */
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    /**
     * 获取退回类型
     *
     * @return 退回类型
     */
    public String getBackType() {
        return this.backType;
    }

    /**
     * 设置退回类型
     *
     * @param backType
     *          退回类型
     */
    public void setBackType(String backType) {
        this.backType = backType;
    }

    /**
     * 获取操作人
     *
     * @return 操作人
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * 设置操作人
     *
     * @param operator
     *          操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}