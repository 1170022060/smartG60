package com.pingok.datacenter.service.trans.impl;

import com.pingok.datacenter.domain.trans.vo.*;
import com.pingok.datacenter.domain.trans.*;
import com.pingok.datacenter.mapper.trans.*;
import com.pingok.datacenter.service.trans.ITransService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 流水入库 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TransServiceImpl implements ITransService {

    @Autowired
    private TblEnTransMapper tblEnTransMapper;

    @Autowired
    private TblEnTransPassMapper tblEnTransPassMapper;

    @Autowired
    private TblExTransMapper tblExTransMapper;

    @Autowired
    private TblExTransPassMapper tblExTransPassMapper;

    @Autowired
    private TblExTransSplitMapper tblExTransSplitMapper;

    @Autowired
    private TblTransSummaryMapper tblTransSummaryMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public int insertEnTrans(TblEnTrans tblEnTrans) {
        tblEnTrans.setId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblEnTrans.getTransTime());
        tblEnTrans.setTableName("TBL_EN_TRANS_"+ year);
        return tblEnTransMapper.insertEnTrans(tblEnTrans);
    }

    @Override
    public int insertEnTransPass(TblEnTransPass tblEnTransPass) {
        tblEnTransPass.setId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblEnTransPass.getTransTime());
        tblEnTransPass.setTableName("TBL_EN_TRANS_PASS_"+ year);
        return tblEnTransPassMapper.insertEnTransPass(tblEnTransPass);
    }

    @Override
    public String insertExTrans(TblExTrans tblExTrans) {
        tblExTrans.setId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExTrans.getTransTime());
        tblExTrans.setTableName("TBL_EX_TRANS_"+ year);
        tblExTransMapper.insertExTrans(tblExTrans);
        return year;
    }

    @Override
    public int insertExTransPass(TblExTransPass tblExTransPass) {
        tblExTransPass.setId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExTransPass.getTransTime());
        tblExTransPass.setTableName("TBL_EX_TRANS_PASS_"+ year);
        return tblExTransPassMapper.insertExTransPass(tblExTransPass);
    }

    @Override
    public int insertExTransSplit(String year, List<TblExTransSplit> tblExTransSplit) {
        int result=0;
        for(TblExTransSplit list :tblExTransSplit)
        {
            list.setId(remoteIdProducerService.nextId());
            list.setTableName("TBL_EX_TRANS_SPLIT_"+ year);
            tblExTransSplitMapper.insertExTransSplit(list);
            result++;
        }
        return result;
    }

    @Override
    public Long selectSamePassId(String passId) {
        return tblTransSummaryMapper.selectSamePassId(passId);
    }

    @Override
    public int insertEnTransSummary(EnTransEnum enTransEnum) {
        Long id = StringUtils.isNull(tblTransSummaryMapper.selectSamePassId(enTransEnum.getTblEnTrans().getPassId())) ? 0L : tblTransSummaryMapper.selectSamePassId(enTransEnum.getTblEnTrans().getPassId());
        TblTransSummary tblTransSummary=new TblTransSummary();
        tblTransSummary.setEnGid(enTransEnum.getTblEnTrans().getGid());
        tblTransSummary.setEnTransTime(enTransEnum.getTblEnTrans().getTransTime());
        tblTransSummary.setEnWorkDate(enTransEnum.getTblEnTrans().getWorkDate());
        tblTransSummary.setEnTransType(enTransEnum.getTblEnTrans().getTransType());
        tblTransSummary.setEnPassType(enTransEnum.getTblEnTrans().getPassType());
        tblTransSummary.setEnLaneHex(enTransEnum.getTblEnTrans().getLaneHex());
        tblTransSummary.setEnTransNumber(enTransEnum.getTblEnTrans().getTransNumber());
        tblTransSummary.setEnOptId(enTransEnum.getTblEnTrans().getOptId());
        tblTransSummary.setEnShift(enTransEnum.getTblEnTrans().getShift());
        tblTransSummary.setEnVehClass(enTransEnum.getTblEnTrans().getVehClass());
        tblTransSummary.setEnVehStatus(enTransEnum.getTblEnTrans().getVehStatus());
        tblTransSummary.setEnVehPlate(enTransEnum.getTblEnTrans().getVehPlate());
        tblTransSummary.setEnVehColor(enTransEnum.getTblEnTrans().getVehColor());
        if(enTransEnum.getTblEnTrans().getPassType()==5)
        {
            tblTransSummary.setEnCardId(enTransEnum.getTblEnTransPass().getEtcCardId());
        }
        if(enTransEnum.getTblEnTrans().getPassType()==6)
        {
            tblTransSummary.setEnCardId(enTransEnum.getTblEnTransPass().getCpcCardId());
        }
        if(id==0L)
        {
            tblTransSummary.setId(remoteIdProducerService.nextId());
            tblTransSummary.setPassId(enTransEnum.getTblEnTrans().getPassId());
            return tblTransSummaryMapper.insert(tblTransSummary);
        }
        else
        {
            tblTransSummary.setId(id);
            return tblTransSummaryMapper.updateByPrimaryKeySelective(tblTransSummary);
        }
    }

    @Override
    public int insertExTransSummary(ExTransEnum exTransEnum) {
        Long id = StringUtils.isNull(tblTransSummaryMapper.selectSamePassId(exTransEnum.getTblExTrans().getPassId())) ? 0L : tblTransSummaryMapper.selectSamePassId(exTransEnum.getTblExTrans().getPassId());
        TblTransSummary tblTransSummary=new TblTransSummary();
        tblTransSummary.setExGid(exTransEnum.getTblExTrans().getGid());
        tblTransSummary.setExTransTime(exTransEnum.getTblExTrans().getTransTime());
        tblTransSummary.setExWorkDate(exTransEnum.getTblExTrans().getWorkDate());
        tblTransSummary.setExTransType(exTransEnum.getTblExTrans().getTransType());
        tblTransSummary.setExLaneHex(exTransEnum.getTblExTrans().getLaneHex());
        tblTransSummary.setExTransNumber(exTransEnum.getTblExTrans().getTransNumber());
        tblTransSummary.setExOptId(exTransEnum.getTblExTrans().getOptId());
        tblTransSummary.setExShift(exTransEnum.getTblExTrans().getShift());
        tblTransSummary.setFeeType(exTransEnum.getTblExTrans().getFeeType());
        tblTransSummary.setTollFee(exTransEnum.getTblExTrans().getTollFee());
        tblTransSummary.setTollFee95(exTransEnum.getTblExTrans().getTollFee95());
        tblTransSummary.setLocalFee(exTransEnum.getTblExTrans().getLocalFee());
        tblTransSummary.setLocalFee95(exTransEnum.getTblExTrans().getLocalFee95());
        tblTransSummary.setPayWay(exTransEnum.getTblExTrans().getPayWay());
        tblTransSummary.setExPassType(exTransEnum.getTblExTrans().getPassType());
        tblTransSummary.setExVehClass(exTransEnum.getTblExTrans().getVehClass());
        tblTransSummary.setExVehStatus(exTransEnum.getTblExTrans().getVehStatus());
        tblTransSummary.setExVehPlate(exTransEnum.getTblExTrans().getVehPlate());
        tblTransSummary.setExVehColor(exTransEnum.getTblExTrans().getVehColor());
        tblTransSummary.setAmount(exTransEnum.getTblExTrans().getAmount());
        if(exTransEnum.getTblExTrans().getPassType()==5)
        {
            tblTransSummary.setExCardId(exTransEnum.getTblExTransPass().getEtcCardId());
        }
        if(exTransEnum.getTblExTrans().getPassType()==6)
        {
            tblTransSummary.setExCardId(exTransEnum.getTblExTransPass().getCpcCardId());
        }
        if(id==0L)
        {
            tblTransSummary.setId(remoteIdProducerService.nextId());
            tblTransSummary.setPassId(exTransEnum.getTblExTrans().getPassId());
            return tblTransSummaryMapper.insert(tblTransSummary);
        }
        else
        {
            tblTransSummary.setId(id);
            return tblTransSummaryMapper.updateByPrimaryKeySelective(tblTransSummary);
        }
    }


}
