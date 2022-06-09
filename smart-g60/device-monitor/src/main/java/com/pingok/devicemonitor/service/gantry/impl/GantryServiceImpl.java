package com.pingok.devicemonitor.service.gantry.impl;

import com.pingok.devicemonitor.domain.gantry.TblGantryStatus;
import com.pingok.devicemonitor.domain.gantry.TblGantryStatusDtl;
import com.pingok.devicemonitor.domain.gantry.TblGantryStatusDtlLog;
import com.pingok.devicemonitor.domain.gantry.TblGantryStatusLog;
import com.pingok.devicemonitor.mapper.gantry.TblGantryStatusDtlLogMapper;
import com.pingok.devicemonitor.mapper.gantry.TblGantryStatusDtlMapper;
import com.pingok.devicemonitor.mapper.gantry.TblGantryStatusLogMapper;
import com.pingok.devicemonitor.mapper.gantry.TblGantryStatusMapper;
import com.pingok.devicemonitor.service.gantry.IGantryService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Service
public class GantryServiceImpl implements IGantryService {
    @Autowired
    private TblGantryStatusMapper tblGantryStatusMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblGantryStatusDtlMapper tblGantryStatusDtlMapper;
    @Autowired
    private TblGantryStatusLogMapper tblGantryStatusLogMapper;
    @Autowired
    private TblGantryStatusDtlLogMapper tblGantryStatusDtlLogMapper;

    @Override
    @Transactional
    public void updateStatus(TblGantryStatus gantryStatus) {

        TblGantryStatusLog gantryStatusLog = new TblGantryStatusLog();
        BeanUtils.copyNotNullProperties(gantryStatus, gantryStatusLog);
        gantryStatusLog.setId(remoteIdProducerService.nextId());
        tblGantryStatusLogMapper.insert(gantryStatusLog);
        if (gantryStatus.getGantryStatusDtls() != null && gantryStatus.getGantryStatusDtls().size() > 0) {
            List<TblGantryStatusDtl> gantryStatusDtls = gantryStatus.getGantryStatusDtls();
            TblGantryStatusDtlLog gantryStatusDtlLog;
            for (TblGantryStatusDtl dtl : gantryStatusDtls) {
                gantryStatusDtlLog = new TblGantryStatusDtlLog();
                BeanUtils.copyNotNullProperties(dtl, gantryStatusDtlLog);
                gantryStatusDtlLog.setId(remoteIdProducerService.nextId());
                gantryStatusDtlLog.setStatusId(gantryStatusLog.getId());
                tblGantryStatusDtlLogMapper.insert(gantryStatusDtlLog);
            }
        }

        Example example = new Example(TblGantryStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", gantryStatus.getDeviceId());

        List<TblGantryStatusDtl> gantryStatusDtls;
        TblGantryStatus gs = tblGantryStatusMapper.selectOneByExample(example);
        if (gs == null) {
            gs = new TblGantryStatus();
            gs.setId(remoteIdProducerService.nextId());
            BeanUtils.copyNotNullProperties(gantryStatus, gs);
            tblGantryStatusMapper.insert(gs);

            if (gantryStatus.getGantryStatusDtls() != null && gantryStatus.getGantryStatusDtls().size() > 0) {
                gantryStatusDtls = gantryStatus.getGantryStatusDtls();
                for (TblGantryStatusDtl dtl : gantryStatusDtls) {
                    dtl.setId(remoteIdProducerService.nextId());
                    dtl.setStatusId(gs.getId());
                    tblGantryStatusDtlMapper.insert(dtl);
                }
            }
        }else {
            BeanUtils.copyNotNullProperties(gantryStatus, gs);
            tblGantryStatusMapper.updateByPrimaryKey(gs);

            example = new Example(TblGantryStatusDtl.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("statusId", gs.getId());
            tblGantryStatusDtlMapper.deleteByExample(example);

            if (gantryStatus.getGantryStatusDtls() != null && gantryStatus.getGantryStatusDtls().size() > 0) {
                gantryStatusDtls = gantryStatus.getGantryStatusDtls();
                for (TblGantryStatusDtl dtl : gantryStatusDtls) {
                    dtl.setId(remoteIdProducerService.nextId());
                    dtl.setStatusId(gs.getId());
                    tblGantryStatusDtlMapper.insert(dtl);
                }
            }
        }
    }
}
