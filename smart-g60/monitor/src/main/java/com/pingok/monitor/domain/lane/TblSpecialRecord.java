package com.pingok.monitor.domain.lane;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.system.api.domain.SysDictData;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 特情表(TBL_SPECIAL_RECORD)
 *
 * @author qiumin
 * @version 1.0.0 2022-04-11
 */
@Data
@Table(name = "TBL_SPECIAL_RECORD")
public class TblSpecialRecord implements Serializable {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * Network+StationID+LaneID+yyyyMMddHHmmssfff
     */
    private String recordId;

    /**
     * 网络编号
     */
    private String netWork;

    /**
     * 收费站ID
     */
    private String stationId;

    /**
     * 车道ID
     */
    private String laneId;

    /**
     * 1:MTC入 2：MTC出 3：ETC入 4：ETC出
     */
    private Integer laneType;

    /**
     * 车道名称（圈圈号)
     */
    private String markName;

    /**
     * 工班日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    /**
     * 告警时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 操作员ID
     */
    private Integer optId;

    /**
     * 操作员姓名
     */
    private String optName;

    /**
     * 工班号
     */
    private Integer shift;

    /**
     * 5:可忽视的，4:需要注意的，3:需要投屏，2:需要报修，1：严重事件
     */
    private Integer specialLevel;

    /**
     * 特情类型
     */
    private Integer type;

    /**
     * 特情信息
     */
    private String message;

    /**
     * 状态 0：未处理，1：已处理，2：忽略 ，3：已弹窗
     */
    private Integer status;

    /**
     * 窗口号
     */
    private Integer winId;

    /**
     * 上传中心标识 0：未上传；1：已上传
     */
    private Integer transStatus;

    private Integer optType;

    private String handleMessage;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    @Transient
    private JSONObject messageJson;
    @Transient
    private String typeValue;

    public TblSpecialRecord(Long id, String recordId, String netWork, String stationId, String laneId, Integer laneType, String markName, Date workDate, Date time, Integer optId, String optName, Integer shift, Integer specialLevel, Integer type, String message, Integer status, Integer winId, Integer transStatus, Integer optType, String handleMessage, Date createTime, Date updateTime, Long createUserId, Long updateUserId) {
        this.id = id;
        this.recordId = recordId;
        this.netWork = netWork;
        this.stationId = stationId;
        this.laneId = laneId;
        this.laneType = laneType;
        this.markName = markName;
        this.workDate = workDate;
        this.time = time;
        this.optId = optId;
        this.optName = optName;
        this.shift = shift;
        this.specialLevel = specialLevel;
        this.type = type;
        this.message = message;
        this.status = status;
        this.winId = winId;
        this.transStatus = transStatus;
        this.optType = optType;
        this.handleMessage = handleMessage;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        if (message != null) {
            this.messageJson = JSONObject.parseObject(message);
        }
        if (type != null) {
            List<SysDictData> sysDictDataList = DictUtils.getDictCache("special_type");
            for (SysDictData d : sysDictDataList) {
                if (d.getDictValue().equals(type.toString())) {
                    this.typeValue = d.getDictLabel();
                }
            }
        }
    }
}