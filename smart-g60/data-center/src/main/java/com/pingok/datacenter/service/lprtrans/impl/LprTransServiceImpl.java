package com.pingok.datacenter.service.lprtrans.impl;

import com.pingok.datacenter.domain.lprtrans.TblEnLprTrans;
import com.pingok.datacenter.domain.lprtrans.TblExLprTrans;
import com.pingok.datacenter.domain.lprtrans.TblLprSummary;
import com.pingok.datacenter.mapper.lprtrans.TblEnLprTransMapper;
import com.pingok.datacenter.mapper.lprtrans.TblExLprTransMapper;
import com.pingok.datacenter.mapper.lprtrans.TblLprSummaryMapper;
import com.pingok.datacenter.service.lprtrans.ILprTransService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * 车牌识别流水入库 服务层处理
 *
 * @author ruoyi
 */
@Service
public class LprTransServiceImpl implements ILprTransService {

    @Autowired
    private TblEnLprTransMapper tblEnLprTransMapper;

    @Autowired
    private TblExLprTransMapper tblExLprTransMapper;

    @Autowired
    private TblLprSummaryMapper tblLprSummaryMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public int insertEnLprTrans(TblEnLprTrans tblEnLprTrans) {
        tblEnLprTrans.setId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblEnLprTrans.getTransTime());
        tblEnLprTrans.setTableName("TBL_EN_LPR_TRANS_"+ year);
        return tblEnLprTransMapper.insertEnLprTrans(tblEnLprTrans);
    }

    @Override
    public int insertExLprTrans(TblExLprTrans tblExLprTrans) {
        tblExLprTrans.setId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExLprTrans.getTransTime());
        tblExLprTrans.setTableName("TBL_EX_LPR_TRANS_"+ year);
        return tblExLprTransMapper.insertExLprTrans(tblExLprTrans);
    }

    @Override
    public int insertEnLprSummary(TblEnLprTrans tblEnLprTrans) {
        TblLprSummary tblLprSummary=new TblLprSummary();
        tblLprSummary.setId(remoteIdProducerService.nextId());
        tblLprSummary.setEnTransTime(tblEnLprTrans.getTransTime());
        tblLprSummary.setEnTransNumber(tblEnLprTrans.getTransNumber());
        tblLprSummary.setEnLaneGb(tblEnLprTrans.getLaneGb());
        tblLprSummary.setEnVehPlate(tblEnLprTrans.getVehPlate());
        tblLprSummary.setEnVehColor(tblEnLprTrans.getVehColor());
        return tblLprSummaryMapper.insert(tblLprSummary);
    }

    @Override
    public int insertExLprSummary(TblExLprTrans tblExLprTrans) {
        TblLprSummary tblLprSummary=new TblLprSummary();
        tblLprSummary.setId(remoteIdProducerService.nextId());
        tblLprSummary.setEnTransTime(tblExLprTrans.getTransTime());
        tblLprSummary.setEnTransNumber(tblExLprTrans.getTransNumber());
        tblLprSummary.setEnLaneGb(tblExLprTrans.getLaneGb());
        tblLprSummary.setEnVehPlate(tblExLprTrans.getVehPlate());
        tblLprSummary.setEnVehColor(tblExLprTrans.getVehColor());
        return tblLprSummaryMapper.insert(tblLprSummary);
    }
}
