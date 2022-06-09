package com.ruoyi.system.api.domain.kafuka;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * TBL_KAFKA_FAIL_INFO
 * 
 * @author qiumin
 * @version 1.0.0 2022-03-17
 */
public class TblKafkaFailInfo implements Serializable {

    /** id */
    @Id
    private Long id;

    /** 消息标识 */
    private String topIc;

    /** 内容 */
    private String data;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /**
     * 获取id
     *
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取消息标识
     *
     * @return 消息标识
     */
    public String getTopIc() {
        return this.topIc;
    }

    /**
     * 设置消息标识
     *
     * @param topIc
     *          消息标识
     */
    public void setTopIc(String topIc) {
        this.topIc = topIc;
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public String getData() {
        return this.data;
    }

    /**
     * 设置内容
     *
     * @param data
     *          内容
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取发送时间
     *
     * @return 发送时间
     */
    public Date getSendTime() {
        return this.sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime
     *          发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("topIc", getTopIc())
                .append("data", getData())
                .append("sendTime", getSendTime())
                .toString();
    }
}