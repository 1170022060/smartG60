package com.pingok.event.domain.videoEvent;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author
 * @time 2022/5/6 19:56
 */
@Data
@Table(name = "TBL_EVENT_VEHICLE_EVENT")
public class TblEventVehicleEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private Long ubiLogicId; // long 唯一编号
    private Integer uiEventType; // int 事件类型（见附录）
    private String szEventObjRect; // string 目标区域[x,y,w,h]
    private Long ubiVehicleId; // long 车序号
    private Integer uiVehicleTypeDetail; // int 车型细分（见附录）
    private Long ubiImgId; // long 事件图片ID
    private String szImg; // String 事件图片（Base64字符串)
    private Long ubiSmallImgId; // long 车牌图ID
    private Long ubiCarImgId; // long 目标图ID
    private Integer uiYear; // int 记录时间的年份
    private Long ubiTime; // long 记录时间 (ms)
    private Integer uiMonth; // int 记录时间的月份
    private Integer uiDay; // int 记录时间的天数
    private Integer uiHour; // int 记录时间的小时
    private Integer uiMin; // Integer 记录时间的分钟
    private BigDecimal uiWidth; // Integer 图片宽度
    private BigDecimal uiHeight; // int 图片高度
    private Integer uiVehiclePlateColor; // int 车牌颜色
    private Integer uiVehicleColor1; // int 车身颜色1（见附录）
    private Integer uiVehicleColor2; // int 车身颜色2（见附录）
    private Integer uiVehicleColor3; // int 车身颜色3（见附录）
    private Integer uiVehicleColorWeight1; // int 车身颜色权重1
    private Integer uiVehicleColorWeight2; // int 车身颜色权重2
    private Integer uiVehicleColorWeight3; // int 车身颜色权重3
    private Integer uiVehiclePlateLn; // int 车牌号行数（暂无）
    private Integer uiVehiclePlateConfidence; // int 置信度
    private Integer uiVehicleSpeed; // int 车速
    private Integer uiVehicleLane; // int 所在车道号
    private String szVehiclePlateRegion; // String 车牌区域所在的矩形（x,y,w,h）
    private Long ubiSourceId; // long 视频源id
    private String szSceneId; // String 场景id
    private Long ubiSectionId; // long 断面id
    private String szSourceCode;//相机ID
    private String uiTrackId;
    private Integer uiDeviceType;
    private Long ubiPtzShortId;//关联云台相机编号，不存在时为空
    @Transient
    private String video;
}
