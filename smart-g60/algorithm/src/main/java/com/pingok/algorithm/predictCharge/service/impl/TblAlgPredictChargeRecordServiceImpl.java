package com.pingok.algorithm.predictCharge.service.impl;

import com.pingok.algorithm.predictCharge.domain.DealResult;
import com.pingok.algorithm.predictCharge.entity.TblAlgPredictChargeRecord;
import com.pingok.algorithm.predictCharge.mapper.TblAlgPredictChargeRecordMapper;
import com.pingok.algorithm.predictCharge.service.TblAlgPredictChargeRecordService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteDataCenterService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.gantry.ChargeFlowModel;
import com.ruoyi.system.api.domain.gantry.TblGantryChargeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 收益预测记录业务类
 */
@Service
@Slf4j
public class TblAlgPredictChargeRecordServiceImpl implements TblAlgPredictChargeRecordService {

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    @Resource
    private TblAlgPredictChargeRecordMapper predictChargeRecordMapper;

    @Resource
    private RemoteDataCenterService remoteDataCenterService;

    /**
     * 定时计算当天的收费预测记录
     */
    @Override
    public void autoCurrentDayPredictCharge() throws Exception {
        // 查询G60所有收费区间
        R<List<TblGantryChargeInfo>> resultChargeInfo = remoteDataCenterService.chargeInfo(null);
        List<TblGantryChargeInfo> gantryChargeInfoList = resultChargeInfo == null? null: resultChargeInfo.getData();
        if (gantryChargeInfoList == null || gantryChargeInfoList.size() == 0){
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        // 结束日期
        calendar.add(Calendar.DATE, -1);
        String endDateStr = sdf.format(calendar.getTime());
        // 开始日期(前90天)
        calendar.add(Calendar.DATE, -89);
        String startDateStr = sdf.format(calendar.getTime());
        String currentDate = DateUtils.getDate();
        Date currentTime = new Date();
        TblAlgPredictChargeRecord tempPredictChargeRecord = null;
        for (TblGantryChargeInfo dto : gantryChargeInfoList) {
            tempPredictChargeRecord = new TblAlgPredictChargeRecord();
            // 收费区间编号
            String chargeIntervalId = dto.getChargingUnitId();
            // 获取每个收费区间日车流量数据
            R<List<ChargeFlowModel>> resultChargeFlowList = remoteDataCenterService.selectChargeFlowList(chargeIntervalId, startDateStr, endDateStr);
            List<ChargeFlowModel> chargeFlowModelList = resultChargeFlowList == null? null: resultChargeFlowList.getData();
            if (chargeFlowModelList == null || chargeFlowModelList.size() == 0){
                continue;
            }
            // 数据处理
            DealResult dealResult = dealChargeIntervalFlowRecord(chargeFlowModelList);
            // 数据异常，跳过
            if (dealResult.getWeeks() == 0 || dealResult.getFirstFlow() == 0 || dealResult.getLastFlow() == 0){
                continue;
            }
            // 指数
            int exponent = (dealResult.getWeeks() - 1) <= 0? 1: (dealResult.getWeeks() - 1);
            // 计算平均增长率
            double Gi = Math.pow(((double)dealResult.getFirstFlow() / (double)dealResult.getLastFlow()), (double) 1 / exponent) - 1;
            // 计算该收费区间的日车流量预测
            double Qp = (double)dealResult.getLastFlow() * Math.pow((1 + Gi), exponent);
            // 计算该收费区间的理论日收益
            double C_pre = Qp * dto.getConstant();
            // 计算该收费区间的实际日收益
            double C2 = C_pre * 0.696507;
            // 保存数据
            tempPredictChargeRecord.setId(remoteIdProducerService.nextId());
            tempPredictChargeRecord.setChargeIntervalId(chargeIntervalId);
            tempPredictChargeRecord.setRecordDate(currentDate);
            tempPredictChargeRecord.setPreFlow((int)Math.round(Qp));
            tempPredictChargeRecord.setPreTheoryCharge(C_pre + "");
            tempPredictChargeRecord.setPreRealCharge(C2 + "");
            tempPredictChargeRecord.setCreateTime(currentTime);
            tempPredictChargeRecord.setUpdateTime(currentTime);
            predictChargeRecordMapper.insert(tempPredictChargeRecord);
        }
    }

    /**
     * 数据预处理
     * @param chargeFlowModelList
     */
    private DealResult dealChargeIntervalFlowRecord(List<ChargeFlowModel> chargeFlowModelList) throws Exception{
        int count = chargeFlowModelList.size();
        if (count == 0){
            return new DealResult();
        }
        DealResult dealResult = new DealResult();
        // 当前星期
        int currentWeek = DateUtils.getWeekOfDate(DateUtils.getDate());
        List<ChargeFlowModel> sameWeekList = new ArrayList<>();
        // 相同星期数据
        for (ChargeFlowModel chargeFlowModel : chargeFlowModelList) {
            // 获取相同星期数据
            int recordWeek = DateUtils.getWeekOfDate(chargeFlowModel.getStatisticsDate());
            if (currentWeek == recordWeek){
                sameWeekList.add(chargeFlowModel);
            }
        }
        if (sameWeekList.size() == 0){
            return new DealResult();
        }
        dealResult.setWeeks(sameWeekList.size());
        // 计算日车流量平均值
        double average = 0.0;
        OptionalDouble optionalDouble = chargeFlowModelList.stream().mapToDouble(ChargeFlowModel::getFlow).average();
        if (optionalDouble.isPresent()){
            average = optionalDouble.getAsDouble();
        }
        double squareSum = 0.0;
        // 计算流量平方和
        for (ChargeFlowModel chargeFlowModel : chargeFlowModelList) {
            // 日车流量与平均流量差的平方和
            squareSum = squareSum + Math.pow(chargeFlowModel.getFlow() - average, 2);
        }
        // 计算σ
        double σ = Math.pow((squareSum / count), (double) 1/2);
        double min = average - 3 * σ;
        double max = average + 3 * σ;
        // 第一个星期
        if (sameWeekList.get(0).getFlow() >= min && sameWeekList.get(0).getFlow() <= max){
            dealResult.setFirstFlow(sameWeekList.get(0).getFlow());
        } else {
            ChargeFlowModel retFirst = fillChargeIntervalFlowRecord(sameWeekList.get(0), min, max);
            dealResult.setFirstFlow(retFirst.getFlow());
        }
        // 最后一个星期
        if (sameWeekList.get((sameWeekList.size() - 1)).getFlow() >= min && sameWeekList.get((sameWeekList.size() - 1)).getFlow() <= max){
            dealResult.setLastFlow(sameWeekList.get((sameWeekList.size() - 1)).getFlow());
        } else {
            ChargeFlowModel retLast = fillChargeIntervalFlowRecord(sameWeekList.get((sameWeekList.size() - 1)), min, max);
            dealResult.setLastFlow(retLast.getFlow());
        }
        return dealResult;
    }

    /**
     * 数据递补
     * @param chargeFlowModel
     * @param min
     * @param max
     */
    private ChargeFlowModel fillChargeIntervalFlowRecord(ChargeFlowModel chargeFlowModel, double min, double max) throws Exception{
        // 统计日期
        String statisticsDate = chargeFlowModel.getStatisticsDate();
        // 计算前7天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(statisticsDate));
        calendar.add(Calendar.DATE, -7);
        String lastStatisticsDate = sdf.format(calendar.getTime());
        // 与当天日期相差42天，直接返回
        chargeFlowModel.setStatisticsDate(lastStatisticsDate);
        int diffSeconds = DateUtils.calSeconds(lastStatisticsDate + " 00:00:00", DateUtils.getDate() + " 00:00:00");
        int diffDays = diffSeconds / (60 * 60 * 24);
        // 超过5周，直接返回
        if (diffDays > 35){
            return new ChargeFlowModel();
        }
        // 根据收费区间、统计日期查询车流量数据
        R<ChargeFlowModel> resultChargeFlowModel = remoteDataCenterService.selectChargeFlow(chargeFlowModel.getChargingUnitId(), chargeFlowModel.getStatisticsDate());
        ChargeFlowModel result = resultChargeFlowModel == null? new ChargeFlowModel(): resultChargeFlowModel.getData();
        if (result != null && result.getFlow() >= min && result.getFlow() <= max){
            return result;
        } else {
            fillChargeIntervalFlowRecord(chargeFlowModel, min, max);
        }
        return new ChargeFlowModel();
    }

    /**
     * 输入日期范围预测收益
     * @param chargeIntervalId 收费区间编号
     * @param startDate 开始日期 例：2022-09-06
     * @param endDate 结束日期 例：2022-09-06
     */
    @Override
    public TblAlgPredictChargeRecord getPredictChargeRecordByDate(String chargeIntervalId, String startDate, String endDate){
        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            throw new IllegalArgumentException("请选择预测日期");
        }
        // 计算预测天数
        int diffSeconds = DateUtils.calSeconds(startDate + " 00:00:00", endDate + " 00:00:00");
        int diffDays = diffSeconds / (60 * 60 * 24) + 1;
        // 当前日期
        String currentDate = DateUtils.getDate();
        // 查询最新预测收益记录
        TblAlgPredictChargeRecord predictChargeRecord = predictChargeRecordMapper.getLatestPredictChargeRecord(currentDate, chargeIntervalId);
        if (predictChargeRecord == null){
            return new TblAlgPredictChargeRecord();
        }
        // 根据预测天数，计算收益
        int preFlowSum = predictChargeRecord.getPreFlow() * diffDays;
        double preTheoryChargeSum = Double.parseDouble(predictChargeRecord.getPreTheoryCharge()) * diffDays;
        double preRealChargeSum = Double.parseDouble(predictChargeRecord.getPreRealCharge()) * diffDays;
        predictChargeRecord.setPreFlow(preFlowSum);
        predictChargeRecord.setPreTheoryCharge(preTheoryChargeSum + "");
        predictChargeRecord.setPreRealCharge(preRealChargeSum + "");
        return predictChargeRecord;
    }
}
