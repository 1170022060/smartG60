package com.pingok.algorithm.carbonEmission.service.impl;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBaseParam;
import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgBaseParamMapper;
import com.pingok.algorithm.carbonEmission.mapper.TblAlgPlantCarbonEmissionMapper;
import com.pingok.algorithm.carbonEmission.service.TblAlgPlantCarbonEmissionService;
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
 * 植物碳排放业务实现类
 */
@Service
@Slf4j
public class TblAlgPlantCarbonEmissionServiceImpl implements TblAlgPlantCarbonEmissionService {

    @Resource
    private TblAlgPlantCarbonEmissionMapper plantCarbonEmissionMapper;

    @Resource
    private TblAlgBaseParamMapper baseParamMapper;

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    /**
     * 查询植物碳排放列表
     * @param tblAlgPlantCarbonEmission
     * @return
     */
    @Override
    public List<TblAlgPlantCarbonEmission> listByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission){
        return plantCarbonEmissionMapper.select(tblAlgPlantCarbonEmission);
    }

    /**
     * 查询植物碳排放详情
     * @param tblAlgPlantCarbonEmission
     * @return
     */
    @Override
    public TblAlgPlantCarbonEmission selectByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission){
        return plantCarbonEmissionMapper.selectOne(tblAlgPlantCarbonEmission);
    }

    /**
     * 保存植物碳排放记录
     *
     * @param tblAlgPlantCarbonEmission
     * @return Boolean
     */
    @Override
    public Boolean saveAlgPlantCarbonEmission(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission) {
        if (tblAlgPlantCarbonEmission == null){
            throw new IllegalArgumentException("参数为空");
        }
        if (StringUtils.isEmpty(tblAlgPlantCarbonEmission.getPlantArea())){
            throw new IllegalArgumentException("请输入植物覆盖面积");
        }
        tblAlgPlantCarbonEmission.setId(remoteIdProducerService.nextId());
        // 查询植物碳排放系数
        TblAlgBaseParam baseParam = new TblAlgBaseParam();
        baseParam.setParamType(6);
        List<TblAlgBaseParam> baseParamList = baseParamMapper.select(baseParam);
        if (baseParamList != null && baseParamList.size() > 0){
            baseParam = baseParamList.get(0);
        }
        // 植物覆盖面积
        BigDecimal plantAreaBdl = new BigDecimal(tblAlgPlantCarbonEmission.getPlantArea());
        // 植物碳排放系数
        BigDecimal plantParamBdl = new BigDecimal(baseParam.getParamValue());
        // 计算碳排放量
        BigDecimal carbonEmissionBdl = plantParamBdl.multiply(plantAreaBdl).setScale(2, RoundingMode.HALF_UP);
        tblAlgPlantCarbonEmission.setCarbonEmission(carbonEmissionBdl.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String currentYear = format.format(new Date());
        tblAlgPlantCarbonEmission.setCarbonEmissionYear(currentYear);
        plantCarbonEmissionMapper.insert(tblAlgPlantCarbonEmission);

        return true;
    }

    /**
     * 修改植物碳排放记录
     *
     * @param tblAlgPlantCarbonEmission
     * @return Boolean
     */
    @Override
    public Boolean modifyAlgPlantCarbonEmission(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission){
        if (tblAlgPlantCarbonEmission == null){
            throw new IllegalArgumentException("参数为空");
        }
        if (StringUtils.isEmpty(tblAlgPlantCarbonEmission.getPlantArea())){
            throw new IllegalArgumentException("请输入植物覆盖面积");
        }
        // 查询植物碳排放系数
        TblAlgBaseParam baseParam = new TblAlgBaseParam();
        baseParam.setParamType(6);
        List<TblAlgBaseParam> baseParamList = baseParamMapper.select(baseParam);
        if (baseParamList != null && baseParamList.size() > 0){
            baseParam = baseParamList.get(0);
        }
        // 植物覆盖面积
        BigDecimal plantAreaBdl = new BigDecimal(tblAlgPlantCarbonEmission.getPlantArea());
        // 植物碳排放系数
        BigDecimal plantParamBdl = new BigDecimal(baseParam.getParamValue());
        // 计算碳排放量
        BigDecimal carbonEmissionBdl = plantParamBdl.multiply(plantAreaBdl).setScale(2, RoundingMode.HALF_UP);
        tblAlgPlantCarbonEmission.setCarbonEmission(carbonEmissionBdl.toString());
        plantCarbonEmissionMapper.updateByPrimaryKey(tblAlgPlantCarbonEmission);

        return true;
    }
}
