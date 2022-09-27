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
 * @time 2022/5/6 19:59
 */
@Data
@Table(name = "tbl_event_parking_event")
public class TblEventParkingEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ubiLogicId; // int 唯一标识
    private Long ubiStartTime; // long 开始时间(秒数) 注：间隔开始时间,毫秒
    private Long ubiEndTime; // long 结束时间(秒数) 注：间隔开始时间,毫秒
    private Long ubiSourceId; // long 视频源ID
    private Long ubiTaskId; // long 任务编号
    private Long ubiImgId1; // Long 图片编号1
    private Long ubiImgId2; // Long 图片编号2
    private Long ubiImgId3; // Long 图片编号3
    private Long ubiImgId4; // Long 图片编号4
    private Long ubiVideoId1; // Long 视频下载ID
    private Long ubiVideoId2; // Long 视频下载ID
    private Long ubiVideoId3; // Long 视频下载ID
    private Long ubiVideoId4; // Long 视频下载ID
}
