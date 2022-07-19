package com.pingok.event.domain.radarCamera;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author
 * @time 2022/7/14 16:39
 */
@Getter
@Setter
public class TblRadarCameraLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time;

    private String devId;

    //规则序号，1~8
    private Integer ruleId;

    private Integer eventType;

    //报警区域，json
    private String direct;

    //场景id
    private Integer sceneId;

    private Integer direction;

    //PTZ坐标，json
    private String ptz;

    private Integer laneNo;

    private String picFilename;
}
