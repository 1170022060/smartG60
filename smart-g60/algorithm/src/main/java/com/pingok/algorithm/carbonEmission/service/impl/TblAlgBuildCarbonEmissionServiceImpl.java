package com.pingok.algorithm.carbonEmission.service.impl;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBaseParam;
import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgBaseParamMapper;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgBuildCarbonEmissionMapper;
import com.pingok.algorithm.carbonEmission.service.TblAlgBuildCarbonEmissionService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 建筑碳排放业务实现类
 */
@Service
@Slf4j
public class TblAlgBuildCarbonEmissionServiceImpl implements TblAlgBuildCarbonEmissionService {

    @Resource
    private TblAlgBuildCarbonEmissionMapper buildCarbonEmissionMapper;

    @Resource
    private TblAlgBaseParamMapper baseParamMapper;

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    /**
     * 保存建筑碳排放记录
     *
     * @param tblAlgBuildCarbonEmission
     * @return Boolean
     */
    @Override
    public Boolean saveAlgBuildCarbonEmission(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission) {
        if (tblAlgBuildCarbonEmission == null){
            throw new IllegalArgumentException("参数为空");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getElectricityConsume())){
            throw new IllegalArgumentException("请输入建筑电能年消耗量");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getGreenReduceCarbon())){
            throw new IllegalArgumentException("请输入建筑绿地碳汇系统年减碳量");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getBuildDesignLife())){
            throw new IllegalArgumentException("请输入建筑设计寿命");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getBuildArea())){
            throw new IllegalArgumentException("请输入建筑面积");
        }
        tblAlgBuildCarbonEmission.setId(remoteIdProducerService.nextId());
        // 查询建筑碳排放系数
        TblAlgBaseParam baseParam = new TblAlgBaseParam();
        baseParam.setParamType(5);
        List<TblAlgBaseParam> baseParamList = baseParamMapper.select(baseParam);
        if (baseParamList != null && baseParamList.size() > 0){
            baseParam = baseParamList.get(0);
        }
        // 建筑电能年消耗量
        BigDecimal electricityConsumeBdl = new BigDecimal(tblAlgBuildCarbonEmission.getElectricityConsume());
        // 建筑绿地碳汇系统年减碳量
        BigDecimal greenReduceCarbonBdl = new BigDecimal(tblAlgBuildCarbonEmission.getGreenReduceCarbon());
        // 建筑设计寿命
        BigDecimal buildDesignLifeBdl = new BigDecimal(tblAlgBuildCarbonEmission.getBuildDesignLife());
        // 建筑面积
        BigDecimal buildAreaBdl = new BigDecimal(tblAlgBuildCarbonEmission.getBuildArea());
        // 建筑碳排放系数
        BigDecimal buildParamBdl = new BigDecimal(baseParam.getParamValue());
        // 计算碳排放量
        BigDecimal carbonEmissionBdl = (buildParamBdl.multiply(electricityConsumeBdl).subtract(greenReduceCarbonBdl)).
                multiply(buildDesignLifeBdl).divide(buildAreaBdl, 2, RoundingMode.HALF_UP);
        tblAlgBuildCarbonEmission.setCarbonEmission(carbonEmissionBdl.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String currentYear = format.format(new Date());
        tblAlgBuildCarbonEmission.setCarbonEmissionYear(currentYear);
        buildCarbonEmissionMapper.insert(tblAlgBuildCarbonEmission);
        return true;
    }

    /**
     * 修改建筑碳排放记录
     *
     * @param tblAlgBuildCarbonEmission
     * @return Boolean
     */
    @Override
    public Boolean modifyAlgBuildCarbonEmission(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission){
        if (tblAlgBuildCarbonEmission == null){
            throw new IllegalArgumentException("参数为空");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getElectricityConsume())){
            throw new IllegalArgumentException("请输入建筑电能年消耗量");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getGreenReduceCarbon())){
            throw new IllegalArgumentException("请输入建筑绿地碳汇系统年减碳量");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getBuildDesignLife())){
            throw new IllegalArgumentException("请输入建筑设计寿命");
        }
        if (StringUtils.isEmpty(tblAlgBuildCarbonEmission.getBuildArea())){
            throw new IllegalArgumentException("请输入建筑面积");
        }
        // 查询建筑碳排放系数
        TblAlgBaseParam baseParam = new TblAlgBaseParam();
        baseParam.setParamType(5);
        List<TblAlgBaseParam> baseParamList = baseParamMapper.select(baseParam);
        if (baseParamList != null && baseParamList.size() > 0){
            baseParam = baseParamList.get(0);
        }
        // 建筑电能年消耗量
        BigDecimal electricityConsumeBdl = new BigDecimal(tblAlgBuildCarbonEmission.getElectricityConsume());
        // 建筑绿地碳汇系统年减碳量
        BigDecimal greenReduceCarbonBdl = new BigDecimal(tblAlgBuildCarbonEmission.getGreenReduceCarbon());
        // 建筑设计寿命
        BigDecimal buildDesignLifeBdl = new BigDecimal(tblAlgBuildCarbonEmission.getBuildDesignLife());
        // 建筑面积
        BigDecimal buildAreaBdl = new BigDecimal(tblAlgBuildCarbonEmission.getBuildArea());
        // 建筑碳排放系数
        BigDecimal buildParamBdl = new BigDecimal(baseParam.getParamValue());
        // 计算碳排放量
        BigDecimal carbonEmissionBdl = (buildParamBdl.multiply(electricityConsumeBdl).subtract(greenReduceCarbonBdl)).
                multiply(buildDesignLifeBdl).divide(buildAreaBdl, 2, RoundingMode.HALF_UP);
        tblAlgBuildCarbonEmission.setCarbonEmission(carbonEmissionBdl.toString());
        buildCarbonEmissionMapper.updateByPrimaryKey(tblAlgBuildCarbonEmission);

        return true;
    }
}
