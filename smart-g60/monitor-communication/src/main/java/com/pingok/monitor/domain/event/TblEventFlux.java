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
 * @time 2022/5/6 19:48
 */

@Data
@Table(name = "tbl_event_flux")
public class TblEventFlux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ubiLogicId; // 唯一编号
    private Integer uiRoadWayNum; // 交通参数统计的所在车道号
    private Long ubiDetectId; // 任务编号
    private Long ubiSourceId; // 视频源ID
    private Long ubiTime; // 记录流量时间（ms）
    private Integer uiYear; //记录时间的年份
    private Integer uiMonth; //记录时间的月份
    private Integer uiDay; // int 记录时间的天数
    private Integer uiHour; // int 记录时间的小时
    private Integer uiMin; // int 记录时间的分钟
    private Integer uiAvgOccupyRatio; // int 平均车道占有率，单位：%(百分比)
    private Integer uiAvgVehiDistince; // int 平均车头间距，单位：米
    private Integer uiAvgVehiSpeed; // int 平均车速，单位：km/h(公里/时)
    private Integer uiQueueLen; // int 队列长度，单位：米
    private Integer uiStatictisTime; // int 统计时间，精确到秒
    private Integer uiTraFluxLarge; // int 特大型车流量
    private Integer uiTraFluxBig; // int 大车流量
    private Integer uiTraFluxMiddle; // int 中车流量
    private Integer uiTraFluxSmall; // int 小车流量
    private Integer uiTraFluxTractor; // int 拖拉机流量
    private Integer uiTraFluxUnMotor; // int 摩托车流量
    private Integer uiTraFluxPerson; // int 行人流量
    private Integer uiTrafficFlux; // int 车流量
    private Float uiTraCongestionIndex; // float 交通拥堵指数
    private Float ufTimeHeadWay; // float 车头时距
    private Float ufCarFollowingPercent; // float 跟车百分比
    private Integer uiTimeInterval; // int 时间间隔(s)
    private Long ubiImgId; // long 大图
    private Long ubiStartTime; // long 开始时间(s)
    private Long ubiEndTime; // long 结束时间(s)
    private Long ubiSectionId; // long 断面id
}
