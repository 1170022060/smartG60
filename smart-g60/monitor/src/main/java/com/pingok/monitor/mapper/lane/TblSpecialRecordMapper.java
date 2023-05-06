package com.pingok.monitor.mapper.lane;

import com.pingok.monitor.domain.lane.TblSpecialRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_SPECIAL_RECORD 数据层
 *
 * @author qiumin
 */
@Mapper
public interface TblSpecialRecordMapper extends CommonRepository<TblSpecialRecord> {

    @Select("SELECT  " +
            "a.ID as \"id\", " +
            "a.NET_WORK as \"netWork\", " +
            "a.STATION_ID as \"stationId\", " +
            "a.LANE_ID as \"laneId\", " +
            "a.LANE_TYPE as \"laneType\", " +
            "a.MARK_NAME as \"markName\", " +
            "to_char(a.WORK_DATE,'yyyy-mm-dd hh24:mi:ss') as \"workDate\", " +
            "to_char(a.TIME,'yyyy-mm-dd hh24:mi:ss') as \"time\", " +
            "a.OPT_ID as \"optId\", " +
            "a.OPT_NAME as \"optName\", " +
            "a.SHIFT as \"shift\", " +
            "case a.SPECIAL_LEVEL  " +
            "when 1 then '严重事件'  " +
            "when 2 then '需要报修' " +
            "when 3 then '需要投屏' " +
            "when 4 then '需要注意的' " +
            "when 5 then '可忽视的' end as \"specialLevel\", " +
            "a.TYPE as \"type\", " +
            "case TYPE " +
            "when 10 then 'Obu已过期'  " +
            "when 11 then 'Obu未启用'  " +
            "when 12 then 'Obu无卡'  " +
            "when 13 then 'Obu拆卸'  " +
            "when 14 then 'OBU读写错误'  " +
            "when 15 then 'OBU车情不合法'  " +
            "when 16 then 'OBU车型不合法'  " +
            "when 17 then 'Obu通讯失败'  " +
            "when 18 then 'OBU读写失败'  " +
            "when 19 then '清Obu失败'  " +
            "when 20 then 'Cpu卡读写失败'  " +
            "when 21 then '卡未启用'  " +
            "when 22 then '余额不足'  " +
            "when 23 then '卡签车牌不一致'  " +
            "when 24 then '无效卡类型'  " +
            "when 25 then '卡签不一致'  " +
            "when 40 then '无车辆信息'  " +
            "when 41 then '状态名单'  " +
            "when 42 then '车辆超载'  " +
            "when 43 then '无入口信息'  " +
            "when 44  then '金额异常'  " +
            "when 60 then '工班异常'  " +
            "when 61  then '减队列'  " +
            "when 62  then '加队列'  " +
            "when 63  then '车辆放行'  " +
            "when 64 then '车辆闯关'  " +
            "when 65  then '改判车型/车牌'  " +
            "when 66  then '改判车情'  " +
            "when 67  then '特殊车情'  " +
            "when 68  then '拖挂车'  " +
            "when 69 then '同站进出'  " +
            "when 70 then '异常流水'  " +
            "when 71 then '大件运输'  " +
            "when 72 then '绿通车'  " +
            "when 73 then '牵引车'  " +
            "when 82 then '治超异常'  " +
            "when 90 then '网络异常'  " +
            "when 80 then '车牌识别异常'  " +
            "when 81 then '摄像机异常'  " +
            "end as \"typeValue\", " +
            "a.MESSAGE as \"message\", " +
            "case a.STATUS  " +
            "when 0 then '未处理' " +
            "when 1 then '已处理' " +
            "when 2 then '忽略' " +
            "when 3 then '已弹窗' end as \"status\", " +
            "a.WIN_ID as \"winId\", " +
            "case a.TRANS_STATUS " +
            "when 0 then '未上传' " +
            "when 1 then '已上传' end as \"transStatus\", " +
            "case a.OPT_TYPE  " +
            "when 1 then '手持机' " +
            "when 2 then '手持天线' " +
            "when 3 then '超重劝离' " +
            "when 4 then 'CPC发卡' " +
            "when 9 then '其他' end as \"optType\", " +
            "a.HANDLE_MESSAGE as \"handleMessage\", " +
            "to_char(a.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"createTime\", " +
            "to_char(a.UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"updateTime\", " +
            "b.NICK_NAME as \"createUser\",  " +
            "c.NICK_NAME as \"updateUser\" " +
            "FROM TBL_SPECIAL_RECORD a " +
            "LEFT JOIN SYS_USER b on a.CREATE_USER_ID = b.USER_ID " +
            "LEFT JOIN SYS_USER c on a.UPDATE_USER_ID = c.USER_ID " +
            "WHERE a.STATION_ID=#{stationId} AND a.STATUS=0 AND LANE_ID=#{laneId} " +
            "order by a.TIME DESC ")
    List<Map> getSpecialList(@Param("stationId") String stationId,@Param("laneId")String laneId);
}
