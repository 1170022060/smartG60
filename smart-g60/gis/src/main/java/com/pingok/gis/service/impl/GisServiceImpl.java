package com.pingok.gis.service.impl;

import com.pingok.gis.domain.*;
import com.pingok.gis.mapper.*;
import com.pingok.gis.service.IGisService;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * GIS 服务层处理
 *
 * @author qiumin
 */
@Service
public class GisServiceImpl implements IGisService {

    @Autowired
    private CameraMapper cameraMapper;

    @Autowired
    private VmsMapper vmsMapper;

    @Autowired
    private VdMapper vdMapper;

    @Autowired
    private LightMapper lightMapper;

    @Autowired
    private RoadMapper roadMapper;

    @Override
    public void updateStatus(String code, Integer status, String type) {
        Example example;
        switch (type) {
            case "camera":
                example = new Example(Camera.class);
                example.createCriteria().andEqualTo("code", code);
                Camera camera = cameraMapper.selectOneByExample(example);
                if (StringUtils.isNotNull(camera)) {
                    camera.setStatus(status);
                    cameraMapper.updateByPrimaryKey(camera);
                }
                break;
            case "vms":
                example = new Example(Vms.class);
                example.createCriteria().andEqualTo("code", code);
                Vms vms = vmsMapper.selectOneByExample(example);
                if (StringUtils.isNotNull(vms)) {
                    vms.setStatus(status);
                    vmsMapper.updateByPrimaryKey(vms);
                }
                break;
            case "vd":
                example = new Example(Vd.class);
                example.createCriteria().andEqualTo("code", code);
                Vd vd = vdMapper.selectOneByExample(example);
                if (StringUtils.isNotNull(vd)) {
                    vd.setStatus(status);
                    vdMapper.updateByPrimaryKey(vd);
                }
                break;
            case "light":
                example = new Example(Light.class);
                example.createCriteria().andEqualTo("code", code);
                Light light = lightMapper.selectOneByExample(example);
                if (StringUtils.isNotNull(light)) {
                    light.setStatus(status);
                    lightMapper.updateByPrimaryKey(light);
                }
                break;
        }
    }

    @Override
    public void UpdateRoadStatus(Long gisId,Integer status) {
        Example example;
        example = new Example(Road.class);
        example.createCriteria().andEqualTo("id", gisId);
        Road road = roadMapper.selectOneByExample(example);
        if (StringUtils.isNotNull(road)) {
            road.setStatus(status);
            roadMapper.updateByPrimaryKey(road);
        }
    }
}
