package com.pingok.external.domain.baidu;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 百度地图事件记录表(TBL_BAI_DU_MAP_RECORD)
 *
 * @author qiumin
 * @version 1.0.0 2022-05-23
 */
@Data
@Table(name = "TBL_BAI_DU_MAP_RECORD")
public class TblBaiDuMapRecord implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 4677141446832158909L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 用户ak
     */
    private String ak;

    /**
     * 辖区ID
     */
    private Integer nodeId;

    /**
     * 辖区名称
     */
    private String nodeName;

    /**
     * 省份ID
     */
    private Integer provinceCode;

    /**
     * 城市ID
     */
    private Integer cityCode;

    /**
     * 事件ID
     */
    private String eventId;

    /**
     * 事件类型101:拥堵102:交通事故103:交通管制104:封路105:故障车110:施工112:交通阻断201:路面塌陷202:桥梁坍塌210:路面坑洼301:交通信号灯故障401：大雾403：结冰404：积雪405：积水901:通告702:事故高发
     */
    private Integer eventType;

    /**
     * 车辆类型0:全部 1:货车
     */
    private Integer vehicleType;

    /**
     * 事件级别1: 一般2: 重要
     */
    private Integer eventLevel;

    /**
     * 道路名称
     */
    private String roadName;

    /**
     * 对道路通行能力的影响：0: 影响未知1: 无影响2: 通行能力下降3: 完全阻断
     */
    private Integer traffic;

    /**
     * 受事件影响不能通行的车道描述，例如:外侧两车道不能通行
     */
    private String lane;

    /**
     * 受事件影响通行方向1: 南向北2: 北向南3: 东向西4: 西向东如果不满足方向需求，可用汉字描述
     */
    private String direction;

    /**
     * 事件开始时间
     */
    private Date startTime;

    /**
     * 事件结束时间
     */
    private Integer endTime;

    /**
     * 事件描述标题
     */
    private String title;

    /**
     * 事件详细描述文案规则：“事件详情描述”=“事件开始时间”，“道路名称”“方向”“桩号信息”有“事件类型”，影响“受影响车道信息”通行，预计“事件结束时间”结束。（如果“桩号信息”为空，则删掉“桩号信息”）
     */
    private String content;

    /**
     * 事件位置坐标
     */
    private String location;

    /**
     * 坐标类型1:国测局02坐标2:WGS84坐标3:百度坐标
     */
    private Integer locationType;

    /**
     * 路段坐标集合
     */
    private String locationSet;

    /**
     * 上下线标示 1:上线,2:下线
     */
    private Integer onlineFlag;

    /**
     * 图片url
     */
    private String pictureUrl;

    /**
     * 路线规划结果集
     */
    private String planRetArr;

    /**
     * 起点桩号
     */
    private String startNum;

    /**
     * 终点桩号
     */
    private String endNum;

    /**
     * 数据类型，dataType=online（生产数据）dataType=test（测试数据）
     */
    private String dataType;

    /**
     * 受影响车道车道数为从左到右以此编号，道路间以,分隔，应急车道为99
     */
    private String lanevalue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    /**
     * 百度事件ID
     */
    private Long baiduEventId;

}