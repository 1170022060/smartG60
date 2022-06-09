package com.pingok.monitor.domain.gantry.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GantryEnum {
    /**
     * 未上传流水数
     */
    private Integer transactionNumber;

    /**
     * 未上传牌识数
     */
    private Integer travelimageNumber;

    /**
     * 门架列表
     */
    private List<Map> gantryInfos;
}
