package com.pingok.event.domain.videoEvent;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 人像信息数据(TBL_FACE_INFO)
 *
 * @author bianj
 * @version 1.0.0 2023-03-09
 */
@Data
@Table(name = "TBL_FACE_INFO")
public class TblFaceInfo implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 1270118493899963976L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * ubiLogicId
     */
    private Long ubiLogicId;

    /**
     * 检测时间
     */
    private Long ubiTime;

    /**
     * 相机ID
     */
    private Long ubiSourceId;

    /**
     * 相机编号，唯一
     */
    private String szSourceCode;

    /**
     * 年龄段
     */
    private Integer uiAge;

    /**
     * 性别，0=男，1=女
     */
    private Integer uiSex;

    /**
     * 口罩，0=戴口罩，1=没口罩
     */
    private Integer uiMask;

    /**
     * 图片URL，大图
     */
    private String szImg;

    /**
     * 图片URL，目标(小图)
     */
    private String szSmallImg;

}