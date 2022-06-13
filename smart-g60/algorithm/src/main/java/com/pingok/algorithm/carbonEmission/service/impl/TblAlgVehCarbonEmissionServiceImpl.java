package com.pingok.algorithm.carbonEmission.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.pingok.algorithm.carbonEmission.domian.GantryVehicleRecordDto;
import com.pingok.algorithm.carbonEmission.domian.TblAlgOilConsumeVO;
import com.pingok.algorithm.carbonEmission.entity.TblAlgBaseParam;
import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgBaseParamMapper;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgVehCarbonEmissionMapper;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgOilConsumeMapper;
import com.pingok.algorithm.carbonEmission.service.TblAlgVehCarbonEmissionService;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 碳排放业务实现类
 */
@Service
@Slf4j
public class TblAlgVehCarbonEmissionServiceImpl implements TblAlgVehCarbonEmissionService {

    @Resource
    private TblAlgVehCarbonEmissionMapper algVehCarbonEmissionMapper;

    @Resource
    private TblAlgOilConsumeMapper algOilConsumeMapper;

    @Resource
    private TblAlgBaseParamMapper algBaseParamMapper;

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    @Resource
    private RemoteDataCenterService remoteDataCenterService;

    /**
     * 按时获取门架过车数据，保存车辆碳排放记录
     */
    @Override
    public void saveAlgVehCarbonEmission() {
        // 获取门架过车数据
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat startTimeSf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        SimpleDateFormat endTimeSf = new SimpleDateFormat("yyyy-MM-dd HH:59:59");
        String startTimeStr= startTimeSf.format(calendar.getTime());
        String endTimeStr= endTimeSf.format(calendar.getTime());
        // 门架过车记录，需要按照车牌升序，门架方向升序（或经过时间升序）排序
        R<List<Map>> result = remoteDataCenterService.info(startTimeStr, endTimeStr);
        if (200 != result.getCode()){
            return;
        }
        List<Map> resultData = result.getData();
        // 解析结果
        String jsonArrayStr = JSONArray.toJSONString(resultData);
        // 转换为对象集合
        List<GantryVehicleRecordDto> gantryVehicleRecordDtoList = JSONArray.parseArray(jsonArrayStr, GantryVehicleRecordDto.class);
        // 1、获取门架过车数据
        if (gantryVehicleRecordDtoList == null || gantryVehicleRecordDtoList.size() <= 1){
            return;
        }
        // 2、计算每辆车经过门架平均速度
        for (int index = 0; index <= gantryVehicleRecordDtoList.size() - 1; index++) {
            GantryVehicleRecordDto tempGantryVehicleRecordDto = gantryVehicleRecordDtoList.get(index);
            if (StringUtils.isEmpty(tempGantryVehicleRecordDto.getGantryId())
                    || StringUtils.isEmpty(tempGantryVehicleRecordDto.getLicensePlate())
                    || tempGantryVehicleRecordDto.getVehicleType() == null
                    || tempGantryVehicleRecordDto.getVehicleStyle() == null
                    || tempGantryVehicleRecordDto.getVehicleColor() == null){
                continue;
            }
            // 当前车辆第一条数据
            if (index == 0 || !tempGantryVehicleRecordDto.getLicensePlate().equals(gantryVehicleRecordDtoList.get(index - 1).getLicensePlate())){
                // 该车只存在一条记录，过滤掉
                if (!tempGantryVehicleRecordDto.getLicensePlate().equals(gantryVehicleRecordDtoList.get(index + 1).getLicensePlate())) {
                    continue;
                }
                // 当前过车时间
                String currentPassTime = tempGantryVehicleRecordDto.getPassTime();
                // 下一条数据过车时间
                String nextPassTime = gantryVehicleRecordDtoList.get(index + 1).getPassTime();
                // 计算时间差 单位：s
                int timeDifference = DateUtils.calSeconds(currentPassTime, nextPassTime);
                // 转换时间格式 单位：h
                BigDecimal timeDifferenceBdl = new BigDecimal(timeDifference).divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP);
                // 下一门架距离
                BigDecimal nextGantryDistanceBdl = new BigDecimal(tempGantryVehicleRecordDto.getNextGantryDistance()).divide(new BigDecimal(1000), RoundingMode.HALF_UP);
                // 计算车辆经过门架平均速度
                BigDecimal vehicleAverageSpeedBdl = nextGantryDistanceBdl.divide(timeDifferenceBdl, 2, RoundingMode.HALF_UP);
                // 车辆平均速度
                tempGantryVehicleRecordDto.setVehicleAverageSpeed(vehicleAverageSpeedBdl.doubleValue());

            } // 当前车辆最后一条数据
            else if (index == gantryVehicleRecordDtoList.size() - 1 ||
                    !tempGantryVehicleRecordDto.getLicensePlate().equals(gantryVehicleRecordDtoList.get(index + 1).getLicensePlate())){
                // 当前过车时间
                String currentPassTime = tempGantryVehicleRecordDto.getPassTime();
                // 上一条数据过车时间
                String lastPassTime = gantryVehicleRecordDtoList.get(index - 1).getPassTime();
                // 计算时间差 单位：s
                int timeDifference = DateUtils.calSeconds(lastPassTime, currentPassTime);
                // 转换时间格式 单位：h
                BigDecimal timeDifferenceBdl = new BigDecimal(timeDifference).divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP);
                // 上一门架距离
                BigDecimal lastGantryDistanceBdl = new BigDecimal(gantryVehicleRecordDtoList.get(index - 1).getNextGantryDistance()).divide(new BigDecimal(1000), RoundingMode.HALF_UP);
                // 计算车辆经过门架平均速度
                BigDecimal vehicleAverageSpeedBdl = lastGantryDistanceBdl.divide(timeDifferenceBdl, 2, RoundingMode.HALF_UP);
                // 车辆平均速度
                tempGantryVehicleRecordDto.setVehicleAverageSpeed(vehicleAverageSpeedBdl.doubleValue());

            } // 中间数据
            else {
                // 当前过车时间
                String currentPassTime = tempGantryVehicleRecordDto.getPassTime();
                // 上一条数据过车时间
                String lastPassTime = gantryVehicleRecordDtoList.get(index - 1).getPassTime();
                // 计算与上一数据时间差 单位：s
                int lastTimeDifference = DateUtils.calSeconds(lastPassTime, currentPassTime);
                // 转换时间格式 单位：h
                BigDecimal lastTimeDifferenceBdl = new BigDecimal(lastTimeDifference).divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP);
                // 上一门架距离
                BigDecimal lastGantryDistanceBdl = new BigDecimal(gantryVehicleRecordDtoList.get(index - 1).getNextGantryDistance()).divide(new BigDecimal(1000), RoundingMode.HALF_UP);
                // 计算车辆经过上一门架区间平均速度
                BigDecimal lastVehicleAverageSpeedBdl = lastGantryDistanceBdl.divide(lastTimeDifferenceBdl, 2, RoundingMode.HALF_UP);

                // 下一条数据过车时间
                String nextPassTime = gantryVehicleRecordDtoList.get(index + 1).getPassTime();
                // 计算与下一数据时间差 单位：s
                int nestTimeDifference = DateUtils.calSeconds(currentPassTime, nextPassTime);
                // 转换时间格式 单位：h
                BigDecimal nextTimeDifferenceBdl = new BigDecimal(nestTimeDifference).divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP);
                // 上一门架距离
                BigDecimal nextGantryDistanceBdl = new BigDecimal(tempGantryVehicleRecordDto.getNextGantryDistance()).divide(new BigDecimal(1000), RoundingMode.HALF_UP);
                // 计算车辆经过下一门架区间平均速度
                BigDecimal nextVehicleAverageSpeedBdl = nextGantryDistanceBdl.divide(nextTimeDifferenceBdl, 2, RoundingMode.HALF_UP);

                // 计算平均区间速度
                BigDecimal vehicleAverageSpeedBdl = (lastVehicleAverageSpeedBdl.add(nextVehicleAverageSpeedBdl)).divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
                // 车辆平均速度
                tempGantryVehicleRecordDto.setVehicleAverageSpeed(vehicleAverageSpeedBdl.doubleValue());
            }
        }
        // 将集合根据门架编号，车型，车牌颜色聚合统计
        Map<String, DoubleSummaryStatistics> summaryStatisticsMap = gantryVehicleRecordDtoList.stream().collect(Collectors.groupingBy(
                i -> i.getGantryId() + "," + i.getVehicleStyle() + "," + i.getVehicleType() + "," + i.getVehicleColor(),
                Collectors.summarizingDouble(GantryVehicleRecordDto::getVehicleAverageSpeed)));
        // 碳排放记录
        TblAlgVehCarbonEmission tempCarbonEmission = null;
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:00");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = formatTime.format(new Date());
        String currentDate = formatDate.format(new Date());
        // 查询基础参数表
        List<TblAlgBaseParam> baseParamList = algBaseParamMapper.selectAll();
        // 解析map
        for (Map.Entry<String, DoubleSummaryStatistics> entry: summaryStatisticsMap.entrySet()){
            tempCarbonEmission = new TblAlgVehCarbonEmission();
            tempCarbonEmission.setId(remoteIdProducerService.nextId());
            String[] keys = entry.getKey().split(",");
            // 门架编号
            String gantryId = keys[0];
            tempCarbonEmission.setGantryId(gantryId);
            // 车种
            Integer vehicleStyle = Integer.valueOf(keys[1]);
            tempCarbonEmission.setVehicleStyle(vehicleStyle);
            // 车型
            Integer vehicleType = Integer.valueOf(keys[2]);
            tempCarbonEmission.setVehicleType(vehicleType);
            // 车牌颜色
            Integer vehicleColor = Integer.valueOf(keys[3]);
            tempCarbonEmission.setVehicleColor(vehicleColor);
            // 门架流量
            long gantryFlow = entry.getValue().getCount();
            // 门架平均速度
            double gantryAverageSpeed = entry.getValue().getAverage();
            // 计费里程Lo
            double Lo = Double.parseDouble(getBaseParam(baseParamList, 0, gantryId));
            // 根据车流量，计算总里程数L
            double L = gantryFlow * Lo;
            // 一、计算燃油车碳排放
            if (vehicleColor != null && vehicleColor == 0){
                // 燃油密度 kg/L
                double p = Double.parseDouble(getBaseParam(baseParamList, 3, null));
                // 燃油车系数
                double a = vehicleStyle != null && vehicleStyle == 0? Double.parseDouble(getBaseParam(baseParamList, 1, "0")): Double.parseDouble(getBaseParam(baseParamList, 1, "1"));
                // 根据门架平均速度，获取百公里油耗记录
                double F = 0.0;
                if (gantryAverageSpeed <= 42.5){
                    TblAlgOilConsumeVO algOilConsumeVO = algOilConsumeMapper.getAlgOilConsumeByBean(vehicleType, 42.5);
                    F = algOilConsumeVO.getF();
                } else if (gantryAverageSpeed >= 117.5) {
                    TblAlgOilConsumeVO algOilConsumeVO = algOilConsumeMapper.getAlgOilConsumeByBean(vehicleType, 117.5);
                    F = algOilConsumeVO.getF();
                } else {
                    TblAlgOilConsumeVO algorithmOilConsumeVOFirst = algOilConsumeMapper.getAlgOilConsumeFirst(vehicleType, gantryAverageSpeed);
                    double V1 = algorithmOilConsumeVOFirst.getV();
                    double F1 = algorithmOilConsumeVOFirst.getF();
                    TblAlgOilConsumeVO algorithmOilConsumeVOLast = algOilConsumeMapper.getAlgOilConsumeLast(vehicleType, gantryAverageSpeed);
                    double V2 = algorithmOilConsumeVOLast.getV();
                    double F2 = algorithmOilConsumeVOLast.getF();
                    // 1、根据门架平均速度，计算百公里油耗F
                    if (gantryAverageSpeed == V1) {
                        F = algorithmOilConsumeVOFirst.getF();
                    } else if (gantryAverageSpeed == V2) {
                        F = algorithmOilConsumeVOLast.getF();
                    } else {
                        F = F1 + (gantryAverageSpeed - V1) / (V2 - gantryAverageSpeed) * (F2 - F1);
                    }
                }
                // 2、根据百公里油耗F，每公里碳排放E
                double E = a * p * F / 100;
                // 3、根据总里程数L和每公里碳排放E，计算燃油车每小时碳排放总量
                BigDecimal C =  new BigDecimal(E).multiply(new BigDecimal(L)).setScale(2, RoundingMode.HALF_UP);
                // 4、保存燃油车碳排放记录
                tempCarbonEmission.setCarbonEmission(C.toString());
                tempCarbonEmission.setCarbonEmissionTime(currentTime);
                tempCarbonEmission.setCarbonEmissionDate(currentDate);

            } // 二、计算新能源车碳排放
            else if (vehicleColor != null && vehicleColor == 11){
                // 1、根据车型，获取碳排放折算系数
                double a = Double.parseDouble(getBaseParam(baseParamList, 2, vehicleType + ""));
                double E = Double.parseDouble(getBaseParam(baseParamList, 4, "E"));
                double constant = Double.parseDouble(getBaseParam(baseParamList, 4, null));
                // 2、根据总里程数L和碳排放折算系数，计算新能源车每小时碳排放总量
                BigDecimal C = new BigDecimal(E).multiply(new BigDecimal(a)).multiply(new BigDecimal(L)).multiply(new BigDecimal(constant)).setScale(2, RoundingMode.HALF_UP);
                // 3、保存新能源车碳排放记录
                tempCarbonEmission.setCarbonEmission(C.toString());
                tempCarbonEmission.setCarbonEmissionTime(currentTime);
                tempCarbonEmission.setCarbonEmissionDate(currentDate);
            }
            algVehCarbonEmissionMapper.insert(tempCarbonEmission);
        }
    }

    /**
     * 按时间查询碳排放
     * @param queryTime
     * @return
     */
    @Override
    public TblAlgVehCarbonEmission getAlgVehCarbonEmission(String queryTime){
        return algVehCarbonEmissionMapper.getAlgVehCarbonEmission(queryTime);
    }

    /**
     * 查询车辆碳排放列表
     * @param tblAlgVehCarbonEmission
     * @return
     */
    @Override
    public List<TblAlgVehCarbonEmission> listByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission){
        return algVehCarbonEmissionMapper.select(tblAlgVehCarbonEmission);
    }

    /**
     * 查询车辆碳排放详情
     * @param tblAlgVehCarbonEmission
     * @return
     */
    @Override
    public TblAlgVehCarbonEmission selectByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission){
        return algVehCarbonEmissionMapper.selectOne(tblAlgVehCarbonEmission);
    }

    /**
     * 查询基础参数
     * @return
     */
    private String getBaseParam(List<TblAlgBaseParam> baseParamList, Integer paramType, String paramKey){
        if (baseParamList == null || baseParamList.size() == 0 || paramType == null){
            return null;
        }
        for (TblAlgBaseParam item : baseParamList) {
            if (StringUtils.isNotEmpty(paramKey) && paramType.equals(item.getParamType()) && paramKey.equals(item.getParamKey())){
                return item.getParamValue();
            } else if (paramType.equals(item.getParamType())) {
                return item.getParamValue();
            }
        }
        return null;
    }
}
