package com.pingok.gis.service.impl;

import com.pingok.gis.domain.Camera;
import com.pingok.gis.domain.Vms;
import com.pingok.gis.mapper.CameraMapper;
import com.pingok.gis.mapper.VmsMapper;
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
        }
    }
}
