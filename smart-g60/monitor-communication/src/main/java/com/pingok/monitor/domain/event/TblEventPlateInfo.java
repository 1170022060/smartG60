package com.pingok.monitor.domain.event;

import lombok.Data;
import org.springframework.data.annotation.Id;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @time 2022/5/6 19:53
 */
@Data
@Table(name = "tbl_event_plate_info")
public class TblEventPlateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ubiLogicId; // long 唯一编号
    private String szText; // String 车牌号
    private Integer uiStatType; // int 车辆类型（见附录）
    private Float ufTypeConfidence; // float 车辆类型置信度
    private Integer uiColor; // int 车牌颜色(见附录)
    private Integer uiCarColor1; // int 车身颜色1(见附录)
    private Integer uiCarColor2; // int 车身颜色2(见附录)
    private Integer uiCarColor3; // int 车身颜色3(见附录)
    private Integer uiSpeed; // int 车速
    private Long ubiImgId; // long 大图
//    private String szImg; // String 过车图片，Base64字符串
    private Long ubiSmallImgId; // long 车牌图
//    private String szPlateImg; // String 车牌小图，Base64字符串
    private Long ubiCarImgId; // long 目标图
    private Integer uiCarConfidence; // int 车牌号置信度
    private Integer uiSubType; // int 车型细分(见附录)
    private Integer uiYear; // int 记录时间的年份
    private Integer uiMonth; // int 记录时间的月份
    private Integer uiDay; // int 记录时间的天数
    private Integer uiHour; // int 记录时间的小时
    private Integer uiMin; // int 记录时间的分钟
    private Integer uiWidth; // int 图片宽度
    private Integer uiHeight; // int 图片高度
    private Long ubiTime; // long 记录时间 (ms)
    private String szVehiclePlateRegion; // String 车牌所在区域矩形
    private Long ubiSourceId; // long 视频源id
    private Float ufSubTypeConfidence; // float 细分置信度
    private Integer uiVehicleLane; // int 车道号
    private String szSceneId; // String 场景编号
    private Long ubiVehicleRatio; // long 车道占有率
    private Long ubiSectionId; // long 断面
    private String szCarPosition; // String 车辆位置(w,y,w,h)
    private String szSourceCode;//相机ID
}
