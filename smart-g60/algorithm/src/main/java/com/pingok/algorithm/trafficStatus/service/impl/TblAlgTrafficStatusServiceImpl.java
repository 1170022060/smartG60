package com.pingok.algorithm.trafficStatus.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.pingok.algorithm.trafficStatus.domian.GantryDto;
import com.pingok.algorithm.trafficStatus.domian.GantryVehicleRecordDto;
import com.pingok.algorithm.trafficStatus.domian.TblAlgTrafficStatusVO;
import com.pingok.algorithm.trafficStatus.entity.TblAlgTrafficStatus;
import com.pingok.algorithm.trafficStatus.mapper.TblAlgTrafficStatusMapper;
import com.pingok.algorithm.trafficStatus.mapper.TblAlgTrafficStatusSpeedMapper;
import com.pingok.algorithm.trafficStatus.service.TblAlgTrafficStatusService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteDataCenterService;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 * 交通状态业务实现类
 */
@Service
@Slf4j
public class TblAlgTrafficStatusServiceImpl implements TblAlgTrafficStatusService {

    @Resource
    private TblAlgTrafficStatusMapper trafficStatusMapper;

    @Resource
    private RemoteDataCenterService remoteDataCenterService;

    @Resource
    private TblAlgTrafficStatusSpeedMapper tblAlgTrafficStatusSpeedMapper;

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    /**
     * 生成交通状态记录
     * @param direction 方向 0:上行 1:下行
     */
    @Override
    public void saveTrafficStatusService(Integer direction) throws Exception{
        // 门架集合，门架需要按行驶方向倒序排序，从后往前进行统计
        R<List<Map>> resultGantryList = remoteDataCenterService.gantryList(direction);
        if (200 != resultGantryList.getCode()){
            return;
        }
        // 1、获取门架集合
        List<GantryDto> gantryDtoList = JSONArray.parseArray(JSONArray.toJSONString(resultGantryList.getData()), GantryDto.class);
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        // 前5分钟
        calendar.add(Calendar.MINUTE, -5);
        String before5MinStr= format.format(calendar.getTime());
        // 前30分钟
        calendar.add(Calendar.MINUTE, -30);
        String before30MinStr= format.format(calendar.getTime());
        // 当前时间
        String currentTimeStr= format.format(currentDate);
        TblAlgTrafficStatus tempTrafficStatus = null;
        // 2、遍历门架集合，查询门架过车数据, 最后一个门架不做计算
        for (int index = 0; index < gantryDtoList.size() - 1; index++){
            // 当前门架
            GantryDto currentGantryDto = gantryDtoList.get(index);
            // 前一门架
            GantryDto lastGantryDto = gantryDtoList.get(index + 1);
            // 前两门架
            GantryDto lastTwoGantryDto = lastGantryDto.getType() == 1 && index <= gantryDtoList.size() - 2? gantryDtoList.get(index + 2): null;
            // 获取前五分钟数据，递补规则
            List<GantryVehicleRecordDto> gantryVehicleRecord5MinList = selectGantryVehicleRecordList(currentGantryDto, lastGantryDto, lastTwoGantryDto, before5MinStr, currentTimeStr);
            if (gantryVehicleRecord5MinList == null){
                continue;
            }
            // 获取前30分钟门架数据，需要按照经过时间升序排序
            R<List<Map>> result30Min = remoteDataCenterService.passRecord(lastGantryDto.getGantryId(), before30MinStr, currentTimeStr);
            if (200 != result30Min.getCode()){
                continue;
            }
            List<GantryVehicleRecordDto> gantryVehicleRecord30MinList = JSONArray.parseArray(JSONArray.toJSONString(result30Min.getData()), GantryVehicleRecordDto.class);
            if (gantryVehicleRecord30MinList == null || gantryVehicleRecord30MinList.size() == 0){
                continue;
            }
            // 比较两个集合，在第二个集合中获取第一个集合的对应车牌数据
            List<TblAlgTrafficStatusVO> trafficStatusVOList = selectTrafficStatusVOList(gantryVehicleRecord5MinList, gantryVehicleRecord30MinList);
            // 剔除数据
            trafficStatusVOList = cullTrafficStatusVOList(trafficStatusVOList, currentGantryDto.getLastGantryDistance());
            // 统计数据，计算平均时间
            DoubleSummaryStatistics statistics = trafficStatusVOList.stream().collect(Collectors.summarizingDouble(TblAlgTrafficStatusVO::getTravelTime));
            // 平均时间
            double travelTime = statistics.getAverage();
            // 计算平均速度
            double averageSpeed = new BigDecimal(currentGantryDto.getLastGantryDistance())
                    .divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP)
                    .divide(new BigDecimal(travelTime), 2, RoundingMode.HALF_UP).doubleValue();
            // 根据限速、平均速度获取交通状态
            Integer trafficStatus = tblAlgTrafficStatusSpeedMapper.getTrafficStatusSpeed(currentGantryDto.getLimitSpeed(), averageSpeed);
            // 保存记录
            tempTrafficStatus = new TblAlgTrafficStatus();
            tempTrafficStatus.setId(remoteIdProducerService.nextId());
            tempTrafficStatus.setGantryId(currentGantryDto.getGantryId());
            tempTrafficStatus.setLastGantryId(lastGantryDto.getGantryId());
            tempTrafficStatus.setType(currentGantryDto.getType() == 1 || lastGantryDto.getType() == 1? 1: 0);
            tempTrafficStatus.setTravelTime(travelTime + "");
            tempTrafficStatus.setAverageSpeed(averageSpeed + "");
            tempTrafficStatus.setTrafficStatus(trafficStatus);
            tempTrafficStatus.setCreateTime(currentDate);
            // TODO 计算流量、密度
            trafficStatusMapper.insert(tempTrafficStatus);

            // 前一门架为收费站时，分别计算当前门架到收费站平均时间，当前门架到下一门架平均时间
            if (lastGantryDto.getType() == 1 && lastTwoGantryDto != null){
                // 获取前30分钟门架数据，需要按照经过时间升序排序
                R<List<Map>> result30MinGantry = remoteDataCenterService.passRecord(lastTwoGantryDto.getGantryId(), before30MinStr, currentTimeStr);
                if (200 != result30Min.getCode()){
                    continue;
                }
                List<GantryVehicleRecordDto> gantryVehicleRecord30MinGantryList = JSONArray.parseArray(JSONArray.toJSONString(result30MinGantry.getData()), GantryVehicleRecordDto.class);
                if (gantryVehicleRecord30MinGantryList == null || gantryVehicleRecord30MinGantryList.size() == 0){
                    continue;
                }
                // 比较两个集合，在第二个集合中获取第一个集合的对应车牌数据
                trafficStatusVOList = selectTrafficStatusVOList(gantryVehicleRecord5MinList, gantryVehicleRecord30MinGantryList);
                // 剔除数据
                trafficStatusVOList = cullTrafficStatusVOList(trafficStatusVOList, currentGantryDto.getLastGantryDistance() + lastGantryDto.getLastGantryDistance());
                // 统计数据，计算平均时间
                DoubleSummaryStatistics summaryStatistics = trafficStatusVOList.stream().collect(Collectors.summarizingDouble(TblAlgTrafficStatusVO::getTravelTime));
                // 平均时间
                double travelTime1 = summaryStatistics.getAverage();
                // 计算平均速度
                double averageSpeed1 = new BigDecimal(currentGantryDto.getLastGantryDistance() + lastGantryDto.getLastGantryDistance())
                        .divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP)
                        .divide(new BigDecimal(travelTime1), 2, RoundingMode.HALF_UP).doubleValue();
                // 根据限速、平均速度获取交通状态
                Integer trafficStatus1 = tblAlgTrafficStatusSpeedMapper.getTrafficStatusSpeed(currentGantryDto.getLimitSpeed(), averageSpeed1);
                // TODO 计算流量、密度
                // 保存记录
                tempTrafficStatus = new TblAlgTrafficStatus();
                tempTrafficStatus.setId(remoteIdProducerService.nextId());
                tempTrafficStatus.setGantryId(currentGantryDto.getGantryId());
                tempTrafficStatus.setLastGantryId(lastTwoGantryDto.getGantryId());
                tempTrafficStatus.setType(currentGantryDto.getType() == 1 || lastTwoGantryDto.getType() == 1? 1: 0);
                tempTrafficStatus.setTravelTime(travelTime1 + "");
                tempTrafficStatus.setAverageSpeed(averageSpeed1 + "");
                tempTrafficStatus.setTrafficStatus(trafficStatus1);
                tempTrafficStatus.setCreateTime(currentDate);
                trafficStatusMapper.insert(tempTrafficStatus);
            }
        }
    }

    /**
     * 比较两个集合，以前五分钟集合为准，在第二个集合中获取对应数据
     * @param gantryVehicleRecord5MinList
     * @param gantryVehicleRecord30MinList
     * @return
     */
    public List<TblAlgTrafficStatusVO> selectTrafficStatusVOList(List<GantryVehicleRecordDto> gantryVehicleRecord5MinList, List<GantryVehicleRecordDto> gantryVehicleRecord30MinList){
        if (gantryVehicleRecord5MinList == null ||
                gantryVehicleRecord5MinList.size() == 0 ||
                gantryVehicleRecord30MinList == null ||
                gantryVehicleRecord30MinList.size() == 0){
            return new ArrayList<>();
        }
        // 结果集
        List<TblAlgTrafficStatusVO> resultList = new ArrayList<>();
        TblAlgTrafficStatusVO tempTrafficStatusVO = null;
        for (GantryVehicleRecordDto item5Min : gantryVehicleRecord5MinList) {
            tempTrafficStatusVO = new TblAlgTrafficStatusVO();
            for (GantryVehicleRecordDto item30Min : gantryVehicleRecord30MinList) {
                // 获取同一车牌数据
                if (item5Min.getLicensePlate().equals(item30Min.getLicensePlate())){
                    tempTrafficStatusVO.setGantryId(item5Min.getGantryId());
                    tempTrafficStatusVO.setLastGantryId(item30Min.getGantryId());
                    tempTrafficStatusVO.setLicensePlate(item5Min.getLicensePlate());
                    tempTrafficStatusVO.setPassGantryTime(item5Min.getPassTime());
                    tempTrafficStatusVO.setPassLastGantryTime(item30Min.getPassTime());
                    // 计算时间差
                    int travelTime = DateUtils.calSeconds(item5Min.getPassTime(), item30Min.getPassTime());
                    BigDecimal travelTimeBdl = new BigDecimal(travelTime).divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP);
                    tempTrafficStatusVO.setTravelTime(travelTimeBdl.doubleValue());
                    resultList.add(tempTrafficStatusVO);
                }
            }
        }
        return resultList;
    }

    /**
     * 剔除异常数据
     * @param trafficStatusVOList
     * @param gantryDistance
     * @return
     */
    public List<TblAlgTrafficStatusVO> cullTrafficStatusVOList(List<TblAlgTrafficStatusVO> trafficStatusVOList, String gantryDistance){
        if (trafficStatusVOList == null || trafficStatusVOList.size() == 0 || StringUtils.isEmpty(gantryDistance)){
            return trafficStatusVOList;
        }
        List<TblAlgTrafficStatusVO> resultList = new ArrayList<>();
        // 剔除最大值最小值后的集合
        List<TblAlgTrafficStatusVO> tempMaxMinList = new ArrayList<>();
        // 根据最大值最小值剔除数据
        for (TblAlgTrafficStatusVO item : trafficStatusVOList) {
            // 剔除行程时间大于0.5h, 小于 S/160 h
            // 计算最小行程时间
            BigDecimal minTravelTimeBdl = new BigDecimal(gantryDistance).divide(new BigDecimal(160), 2, RoundingMode.HALF_UP);
            if (item.getTravelTime() > 0.5 || item.getTravelTime() < minTravelTimeBdl.doubleValue()){
                continue;
            }
            tempMaxMinList.add(item);
        }
        // 数量
        int count = tempMaxMinList.size();
        // 获取中位值索引
        int index = count % 2 == 0? count / 2: (count + 1) / 2;
        // 中位数据
        TblAlgTrafficStatusVO medTrafficStatusVO = tempMaxMinList.get(index - 1);
        // 绝对偏差和
        double sumAbsDiff = 0.0;
        // 计算平均偏差集合
        List<TblAlgTrafficStatusVO> tempDiffList = new ArrayList<>();
        // 统计偏差
        for (TblAlgTrafficStatusVO item : tempMaxMinList) {
            // 计算绝对偏差的和
            double absDiff = Math.abs(item.getTravelTime() - medTrafficStatusVO.getTravelTime());
            // 求和
            sumAbsDiff = sumAbsDiff + absDiff;
            tempDiffList.add(item);
        }
        // 计算偏差系数
        Double coefficient = sumAbsDiff / tempDiffList.size() * 3;
        // 根据中位值剔除数据
        for (TblAlgTrafficStatusVO item : tempDiffList) {
            if (item.getTravelTime() > (medTrafficStatusVO.getTravelTime() + coefficient)){
                continue;
            }
            resultList.add(item);
        }
        return resultList;
    }

    /**
     * 计算密度
     * @param currentGantryDto 当前门架
     * @param lastGantryDto 前一门架
     * @param currentFlow 当前门架流量 辆/5min
     * @param lastFlow 前一门架流量 辆/30min
     * @param averageSpeed 区间平均速度
     * @return
     */
    public String calculateDensity(GantryDto currentGantryDto, GantryDto lastGantryDto, Integer currentFlow, Integer lastFlow, Double averageSpeed){
        // 流量单位转换 辆/小时
        double Q1 = currentFlow * 12;
        double Q2 = lastFlow * 2;
        // 计算每车道流量
        double q1 = Q1 / currentGantryDto.getLaneCount();
        double q2 = Q2 / lastGantryDto.getLaneCount();
        // 计算车时距
        double m1 = 1 / q1;
        double m2 = 1 / q2;
        // 平均速度
        double V = averageSpeed <= 40? 2.5*averageSpeed: averageSpeed;
        // 计算车间距
        double h1 = m1 * V;
        double h2 = m2 * V;
        // 计算平均车间距
        double h = (h1 + h2) / 2;
        // 计算密度
        return new BigDecimal(1).divide(new BigDecimal(h), 2, RoundingMode.HALF_UP).toString();
    }

    /**
     * 获取门架记录
     * @param currentGantryDto
     * @param lastGantryDto
     * @param lastTwoGantryDto
     * @param startTime
     * @param endTime
     * @return
     */
    public List<GantryVehicleRecordDto> selectGantryVehicleRecordList(GantryDto currentGantryDto, GantryDto lastGantryDto, GantryDto lastTwoGantryDto, String startTime, String endTime) throws Exception{
        // 门架过车记录，需要按照经过时间升序排序
        R<List<Map>> result5Min = remoteDataCenterService.passRecord(currentGantryDto.getGantryId(), startTime, endTime);
        if (200 != result5Min.getCode()){
            return null;
        }
        // 获取门架数据
        List<GantryVehicleRecordDto> gantryVehicleRecordList = JSONArray.parseArray(JSONArray.toJSONString(result5Min.getData()), GantryVehicleRecordDto.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        String timeFormat = "HH:mm:ss";
        SimpleDateFormat currentTimeScaleFormat = new SimpleDateFormat("HH:mm:ss");
        // 当前时刻
        String currentTimeScaleStr = currentTimeScaleFormat.format(currentDate);
        Date nowTime = new SimpleDateFormat(timeFormat).parse(currentTimeScaleStr);
        Date startDate = new SimpleDateFormat(timeFormat).parse("06:00:00");
        Date endDate = new SimpleDateFormat(timeFormat).parse("23:00:00");
        // 当前时间为白天6：00-23：00，若无数据表示非常拥堵，若有数据，行程时间为第一条行车记录的时间距离此时的时间，无需查找前一门架记录
        if (DateUtils.isEffectiveDate(nowTime, startDate, endDate)) {
            // 递补规则
            if (gantryVehicleRecordList == null || gantryVehicleRecordList.size() == 0){
                // 前10分钟
                calendar.add(Calendar.MINUTE, -10);
                String before10MinStr= format.format(calendar.getTime());
                // 获取前10分钟门架数据
                R<List<Map>> result10Min = remoteDataCenterService.passRecord(currentGantryDto.getGantryId(), before10MinStr, endTime);
                if (200 != result10Min.getCode()){
                    return null;
                }
                // 前10分钟门架数据
                gantryVehicleRecordList = JSONArray.parseArray(JSONArray.toJSONString(result10Min.getData()), GantryVehicleRecordDto.class);
                // 前10分钟为查询到门架数据
                if (gantryVehicleRecordList == null || gantryVehicleRecordList.size() == 0){
                    // 前15分钟
                    calendar.add(Calendar.MINUTE, -15);
                    String before15MinStr= format.format(calendar.getTime());
                    // 获取前15分钟门架数据
                    R<List<Map>> result15Min = remoteDataCenterService.passRecord(currentGantryDto.getGantryId(), before15MinStr, endTime);
                    if (200 != result15Min.getCode()){
                        return null;
                    }
                    // 前10分钟门架数据
                    gantryVehicleRecordList = JSONArray.parseArray(JSONArray.toJSONString(result15Min.getData()), GantryVehicleRecordDto.class);
                    // 前15分钟为查询到门架数据
                    if (gantryVehicleRecordList == null || gantryVehicleRecordList.size() == 0){
                        // 前20分钟
                        calendar.add(Calendar.MINUTE, -20);
                        String before20MinStr= format.format(calendar.getTime());
                        // 获取前15分钟门架数据
                        R<List<Map>> result20Min = remoteDataCenterService.passRecord(currentGantryDto.getGantryId(), before20MinStr, endTime);
                        if (200 != result20Min.getCode()){
                            return null;
                        }
                        // 前20分钟门架数据
                        gantryVehicleRecordList = JSONArray.parseArray(JSONArray.toJSONString(result20Min.getData()), GantryVehicleRecordDto.class);
                        // 前20分钟为查询到门架数据
                        if (gantryVehicleRecordList == null || gantryVehicleRecordList.size() == 0){
                            // 前25分钟
                            calendar.add(Calendar.MINUTE, -25);
                            String before25MinStr= format.format(calendar.getTime());
                            // 获取前15分钟门架数据
                            R<List<Map>> result25Min = remoteDataCenterService.passRecord(currentGantryDto.getGantryId(), before25MinStr, endTime);
                            if (200 != result25Min.getCode()){
                                return null;
                            }
                            // 前25分钟门架数据
                            gantryVehicleRecordList = JSONArray.parseArray(JSONArray.toJSONString(result25Min.getData()), GantryVehicleRecordDto.class);
                            if (gantryVehicleRecordList == null || gantryVehicleRecordList.size() == 0){
                                // 非常拥堵
                                TblAlgTrafficStatus tempTrafficStatus = new TblAlgTrafficStatus();
                                tempTrafficStatus.setId(remoteIdProducerService.nextId());
                                tempTrafficStatus.setGantryId(currentGantryDto.getGantryId());
                                tempTrafficStatus.setLastGantryId(lastGantryDto.getGantryId());
                                tempTrafficStatus.setType(currentGantryDto.getType() == 1 || lastTwoGantryDto.getType() == 1? 1: 0);
                                tempTrafficStatus.setTravelTime(null);
                                tempTrafficStatus.setAverageSpeed(null);
                                tempTrafficStatus.setTrafficStatus(3);
                                tempTrafficStatus.setCreateTime(currentDate);
                                trafficStatusMapper.insert(tempTrafficStatus);
                                if (lastTwoGantryDto != null){
                                    tempTrafficStatus = new TblAlgTrafficStatus();
                                    tempTrafficStatus.setId(remoteIdProducerService.nextId());
                                    tempTrafficStatus.setGantryId(currentGantryDto.getGantryId());
                                    tempTrafficStatus.setLastGantryId(lastTwoGantryDto.getGantryId());
                                    tempTrafficStatus.setType(currentGantryDto.getType() == 1 || lastTwoGantryDto.getType() == 1? 1: 0);
                                    tempTrafficStatus.setTravelTime(null);
                                    tempTrafficStatus.setAverageSpeed(null);
                                    tempTrafficStatus.setTrafficStatus(3);
                                    tempTrafficStatus.setCreateTime(currentDate);
                                    trafficStatusMapper.insert(tempTrafficStatus);
                                }
                            } else {
                                // 取第一条记录
                                GantryVehicleRecordDto firstGantryVehicleRecord = gantryVehicleRecordList.get(0);
                                // 计算行车时间
                                int travelTimeInt = DateUtils.calSeconds(firstGantryVehicleRecord.getPassTime(), endTime);
                                if (travelTimeInt == 0){
                                    return null;
                                }
                                BigDecimal travelTimeBdl = new BigDecimal(travelTimeInt).divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP);
                                // 计算平均速度
                                double averageSpeed = new BigDecimal(currentGantryDto.getLastGantryDistance())
                                        .divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP)
                                        .divide(travelTimeBdl, 2, RoundingMode.HALF_UP).doubleValue();
                                // 根据限速、平均速度获取交通状态
                                Integer trafficStatus = tblAlgTrafficStatusSpeedMapper.getTrafficStatusSpeed(currentGantryDto.getLimitSpeed(), averageSpeed);
                                // 保存记录
                                TblAlgTrafficStatus tempTrafficStatus = new TblAlgTrafficStatus();
                                tempTrafficStatus.setId(remoteIdProducerService.nextId());
                                tempTrafficStatus.setGantryId(currentGantryDto.getGantryId());
                                tempTrafficStatus.setLastGantryId(lastGantryDto.getGantryId());
                                tempTrafficStatus.setType(currentGantryDto.getType() == 1 || lastGantryDto.getType() == 1? 1: 0);
                                tempTrafficStatus.setTravelTime(travelTimeBdl.toString());
                                tempTrafficStatus.setAverageSpeed(averageSpeed + "");
                                tempTrafficStatus.setTrafficStatus(trafficStatus);
                                tempTrafficStatus.setCreateTime(currentDate);
                                trafficStatusMapper.insert(tempTrafficStatus);
                                if (lastTwoGantryDto != null){
                                    // 计算平均速度
                                    double averageSpeed1 = new BigDecimal(currentGantryDto.getLastGantryDistance() + lastGantryDto.getLastGantryDistance())
                                            .divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP)
                                            .divide(travelTimeBdl, 2, RoundingMode.HALF_UP).doubleValue();
                                    // 根据限速、平均速度获取交通状态
                                    Integer trafficStatus1 = tblAlgTrafficStatusSpeedMapper.getTrafficStatusSpeed(currentGantryDto.getLimitSpeed(), averageSpeed);
                                    // 保存记录
                                    tempTrafficStatus = new TblAlgTrafficStatus();
                                    tempTrafficStatus.setId(remoteIdProducerService.nextId());
                                    tempTrafficStatus.setGantryId(currentGantryDto.getGantryId());
                                    tempTrafficStatus.setLastGantryId(lastTwoGantryDto.getGantryId());
                                    tempTrafficStatus.setType(currentGantryDto.getType() == 1 || lastTwoGantryDto.getType() == 1? 1: 0);
                                    tempTrafficStatus.setTravelTime(travelTimeBdl.toString());
                                    tempTrafficStatus.setAverageSpeed(averageSpeed1 + "");
                                    tempTrafficStatus.setTrafficStatus(trafficStatus1);
                                    tempTrafficStatus.setCreateTime(currentDate);
                                    trafficStatusMapper.insert(tempTrafficStatus);
                                }
                            }
                            return null;
                        }
                    }
                }
            }
        } // 当前时间为夜间23：00-次日6：00，则发布行程时间和交通状态都与前一发布时刻的相同
        else {
            // 未查询到数据
            if (gantryVehicleRecordList == null || gantryVehicleRecordList.size() == 0) {
                // 该门架查询上一条记录
                TblAlgTrafficStatus lastTrafficStatus = trafficStatusMapper.getTrafficStatus(currentGantryDto.getGantryId(), lastGantryDto.getGantryId());
                if (lastTrafficStatus == null){
                    return null;
                }
                lastTrafficStatus.setId(remoteIdProducerService.nextId());
                lastTrafficStatus.setCreateTime(currentDate);
                trafficStatusMapper.insert(lastTrafficStatus);
                if (lastTwoGantryDto != null){
                    TblAlgTrafficStatus lastTwoTrafficStatus = trafficStatusMapper.getTrafficStatus(currentGantryDto.getGantryId(), lastTwoGantryDto.getGantryId());
                    if (lastTwoTrafficStatus == null){
                        return null;
                    }
                    lastTwoTrafficStatus.setId(remoteIdProducerService.nextId());
                    lastTwoTrafficStatus.setCreateTime(currentDate);
                    trafficStatusMapper.insert(lastTwoTrafficStatus);
                }
            }
        }
        return gantryVehicleRecordList;
    }

    /**
     * 查询最新交通状态列表
     * @param tblAlgTrafficStatus
     * @return
     */
    @Override
    public List<TblAlgTrafficStatus> listByBean(TblAlgTrafficStatus tblAlgTrafficStatus){
        return trafficStatusMapper.selectTrafficStatusListByBean(tblAlgTrafficStatus);
    }
}
