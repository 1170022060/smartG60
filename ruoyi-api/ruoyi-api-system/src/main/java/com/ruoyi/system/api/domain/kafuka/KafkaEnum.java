package com.ruoyi.system.api.domain.kafuka;

import lombok.Data;

import java.io.Serializable;

/**
 * TBL_KAFKA_FAIL_INFO
 *
 * @author qiumin
 * @version 1.0.0 2022-03-17
 */
@Data
public class KafkaEnum implements Serializable {

    /**
     * 消息标识
     */
    private String topIc;

    /**
     * 内容
     */
    private String data;

}