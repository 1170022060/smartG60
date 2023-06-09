package com.pingok.external.domain.roadDoctor;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 道路病害表(TBL_ROAD_DISEASE)
 *
 * @author bianj
 * @version 1.0.0 2022-07-20
 */
@Data
@Table(name = "TBL_ROAD_DISEASE")
public class TblRoadDisease implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 4004259242255487693L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 病害类型ID
     */
    private Long questId;

    /**
     * 病害ID
     */
    private Long mId;

    /**
     * 图片地址
     */
    private String pPicurl;

    /**
     * 病害标注框坐标（需画图） 左上X
     */
    private String mXmin;

    /**
     * 病害标注框坐标 左上Y
     */
    private String mYmin;

    /**
     * 病害标注框坐标 右下X
     */
    private String mXmax;

    /**
     * 病害标注框坐标 右下Y
     */
    private String mYmax;

    /**
     * 病害程度
     */
    private String mQuestionDegree;

    /**
     * 桩号
     */
    private String pZhuangHao;
    private Integer status;
    private Integer uploadStatus;
    private String orderNum;
}