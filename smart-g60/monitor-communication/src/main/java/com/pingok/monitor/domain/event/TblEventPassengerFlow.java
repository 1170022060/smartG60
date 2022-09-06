package com.pingok.monitor.domain.event;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @time 2022/5/6 19:58
 */
@Data
@Table(name = "t_event_passenger_flow")
public class TblEventPassengerFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer uiType; // int 客流类型
    private Long ubiStartTime; // long 开始时间(秒数) 注：间隔开始时间,毫秒
    private Long ubiEndTime; // long 结束时间(秒数) 注: 间隔结束时间,毫秒
    private Integer uiCountingInterval; // int 统计时间间隔,单位秒
    private Long ubiSourceId; // long 视频id
    private String szAreaId; // String 区域id(检测区域编号)
    private Integer uiGetInPeos; // int 进人数(20秒内进入的人数)
    private Integer uiGetOutPeos; // int 出人数(20秒内出去的人数)
    private Integer uiInPeos; // int 在内人数(当前人数)
    private Long ubiImgId; // long 图片ID
}
