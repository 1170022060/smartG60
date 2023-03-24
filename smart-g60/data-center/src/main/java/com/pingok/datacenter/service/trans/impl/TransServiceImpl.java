package com.pingok.datacenter.service.trans.impl;

import com.pingok.datacenter.domain.trans.vo.*;
import com.pingok.datacenter.domain.trans.*;
import com.pingok.datacenter.mapper.trans.*;
import com.pingok.datacenter.service.trans.ITransService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private TblEnEtcPassMapper tblEnEtcPassMapper;

    @Autowired
    private TblEnMtcPassMapper tblEnMtcPassMapper;

    @Autowired
    private TblExTransMapper tblExTransMapper;

    @Autowired
    private TblExEtcPassMapper tblExEtcPassMapper;

    @Autowired
    private TblExMtcPassMapper tblExMtcPassMapper;

    @Autowired
    private TblExPaperPassMapper tblExPaperPassMapper;

    @Autowired
    private TblExTransSplitMapper tblExTransSplitMapper;

    @Autowired
    private TblTransSummaryMapper tblTransSummaryMapper;

    @Autowired
    private TblSectionRecordMapper tblSectionRecordMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public EnInfoVo insertEnTrans(TblEnTrans tblEnTrans) {
        tblEnTrans.setRecordId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblEnTrans.getTransTime());
        tblEnTrans.setTableName("TBL_EN_TRANS_"+ year);
        tblEnTransMapper.insertEnTrans(tblEnTrans);
        EnInfoVo enInfoVo=new EnInfoVo();
        enInfoVo.setRecordId(tblEnTrans.getRecordId());
        enInfoVo.setTableName(tblEnTrans.getTableName());
        return enInfoVo;
    }

    @Override
    public int insertEnEtcPass(TblEnEtcPass tblEnEtcPass,Long recordId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblEnEtcPass.getTransTime());
        tblEnEtcPass.setRecordId(recordId);
        tblEnEtcPass.setTableName("TBL_EN_ETC_PASS_"+ year);
        return tblEnEtcPassMapper.insertEnEtcPass(tblEnEtcPass);
    }

    @Override
    public int insertEnMtcPass(TblEnMtcPass tblEnMtcPass,Long recordId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblEnMtcPass.getTransTime());
        tblEnMtcPass.setRecordId(recordId);
        tblEnMtcPass.setTableName("TBL_EN_MTC_PASS_"+ year);
        return tblEnMtcPassMapper.insertEnMtcPass(tblEnMtcPass);
    }

    @Override
    public ExInfoVo insertExTrans(TblExTrans tblExTrans) {
        tblExTrans.setRecordId(remoteIdProducerService.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExTrans.getTransTime());
        tblExTrans.setTableName("TBL_EX_TRANS_"+ year);
        tblExTransMapper.insertExTrans(tblExTrans);
        ExInfoVo exInfoVo=new ExInfoVo();
        exInfoVo.setRecordId(tblExTrans.getRecordId());
        exInfoVo.setYear(year);
        exInfoVo.setTableName(tblExTrans.getTableName());
        return exInfoVo;
    }

    @Override
    public int insertExEtcPass(TblExEtcPass tblExEtcPass,Long recordId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExEtcPass.getTransTime());
        tblExEtcPass.setRecordId(recordId);
        tblExEtcPass.setId(remoteIdProducerService.nextId());
        if (tblExEtcPass.getEtcCardId() == null){
            tblExEtcPass.setEtcCardId((long) 0);
        }
        if (tblExEtcPass.getEtcCardNet() == null){
            tblExEtcPass.setEtcCardNet("0");
        }
        tblExEtcPass.setTableName("TBL_EX_ETC_PASS_"+ year);
        return tblExEtcPassMapper.insertExEtcPass(tblExEtcPass);
    }

    @Override
    public int insertExMtcPass(TblExMtcPass tblExMtcPass,Long recordId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExMtcPass.getTransTime());
        tblExMtcPass.setRecordId(recordId);
        tblExMtcPass.setTableName("TBL_EX_MTC_PASS_"+ year);
        return tblExMtcPassMapper.insertExMtcPass(tblExMtcPass);
    }

    @Override
    public int insertExPaperPass(TblExPaperPass tblExPaperPass,Long recordId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(tblExPaperPass.getTransTime());
        tblExPaperPass.setRecordId(recordId);
        tblExPaperPass.setTableName("TBL_EX_PAPER_PASS_"+ year);
        return tblExPaperPassMapper.insertExPaperPass(tblExPaperPass);
    }

    @Override
    public int insertExTransSplit(ExInfoVo exInfoVo, List<TblExTransSplit> tblExTransSplit) {
        int result=0;
        for(TblExTransSplit list :tblExTransSplit)
        {
            try{
                list.setRecordId(exInfoVo.getRecordId());
                list.setTableName("TBL_EX_TRANS_SPLIT_"+ exInfoVo.getYear());
                tblExTransSplitMapper.insertExTransSplit(list);
                result++;
            }
            catch (Exception e)
            {
                if(!e.getMessage().contains("unique constraint"))
                {
                    throw e;
                }
            }
        }
        return result;
    }

    @Override
    public void updateSection(Date workDate,String stationId, Integer direction,Integer type,Integer amount) {
        Example example = new Example(TblSectionRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("workDate", workDate);
        criteria.andEqualTo("stationId", stationId);
        criteria.andEqualTo("direction", direction);
        TblSectionRecord tblSectionRecord=tblSectionRecordMapper.selectOneByExample(example);
        if(tblSectionRecord==null)
        {
            tblSectionRecord=new TblSectionRecord();
            tblSectionRecord.setId(remoteIdProducerService.nextId());
            tblSectionRecord.setWorkDate(workDate);
            tblSectionRecord.setDirection(direction);
            tblSectionRecord.setStationId(stationId);
            tblSectionRecord.setEtcLocal(0);
            tblSectionRecord.setEtcElse(0);
            tblSectionRecord.setMtcTrans(0);
            tblSectionRecord.setMtcSingle(0);
            tblSectionRecord.setLicense(0);
            tblSectionRecord.setAmount(0);
            tblSectionRecord.setReleaseTrans(0);
            tblSectionRecord.setFrontTrans(0);
            tblSectionRecord.setBarrierTrans(0);
            tblSectionRecordMapper.insert(tblSectionRecord);
        }
        if(type==1)
        {
            tblSectionRecord.setEtcLocal(tblSectionRecord.getEtcLocal()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==2)
        {
            tblSectionRecord.setEtcElse(tblSectionRecord.getEtcElse()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==3)
        {
            tblSectionRecord.setMtcSingle(tblSectionRecord.getMtcSingle()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==4)
        {
            tblSectionRecord.setMtcTrans(tblSectionRecord.getMtcTrans()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==5)
        {
            tblSectionRecord.setReleaseTrans(tblSectionRecord.getReleaseTrans()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==6)
        {
            tblSectionRecord.setBarrierTrans(tblSectionRecord.getBarrierTrans()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==7)
        {
            tblSectionRecord.setFrontTrans(tblSectionRecord.getFrontTrans()+1);
            tblSectionRecord.setAmount(tblSectionRecord.getAmount()+amount);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
        if(type==8)
        {
            tblSectionRecord.setLicense(tblSectionRecord.getLicense()+1);
            tblSectionRecordMapper.updateByPrimaryKeySelective(tblSectionRecord);
        }
    }

    @Override
    public Long selectSamePassId(String passId) {
        return tblTransSummaryMapper.selectSamePassId(passId);
    }

    @Override
    public String selectLaneGB(String laneHex) {
        return tblTransSummaryMapper.selectLaneGB(laneHex);
    }

    @Override
    public int insertEnTransSummary(EnTransEnum enTransEnum) {
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
        String passId=null;
        if(enTransEnum.getTblEnTrans().getPassType()==5 && enTransEnum.getTblEnEtcPass()!=null)
        {
            if(enTransEnum.getTblEnEtcPass().getEtcCardId()!=null && enTransEnum.getTblEnEtcPass().getEtcCardNet()!=null)
            {
                tblTransSummary.setEnCardId(zero(enTransEnum.getTblEnEtcPass().getEtcCardId()));
                SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=formatter.format(enTransEnum.getTblEnTrans().getTransTime());
                passId="01"+enTransEnum.getTblEnEtcPass().getEtcCardNet()+zero(enTransEnum.getTblEnEtcPass().getEtcCardId())+time;
            }
        }
        if(enTransEnum.getTblEnTrans().getPassType()==6 && enTransEnum.getTblEnMtcPass()!=null)
        {
            if(enTransEnum.getTblEnMtcPass().getCpcCardId()!=null)
            {
                tblTransSummary.setEnCardId(zero(enTransEnum.getTblEnMtcPass().getCpcCardId()));
                SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=formatter.format(enTransEnum.getTblEnTrans().getTransTime());
                passId="020000"+zero(enTransEnum.getTblEnMtcPass().getCpcCardId())+time;
            }
        }
        Long id=0L;
        if(passId!=null)
        {
            id=StringUtils.isNull(selectSamePassId(passId)) ? 0L : selectSamePassId(passId);
        }
        if(id==0L)
        {
            tblTransSummary.setId(remoteIdProducerService.nextId());
            tblTransSummary.setPassId(passId);
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
        String passId=null;
        if(exTransEnum.getTblExTrans().getPassType()==5 && exTransEnum.getTblExEtcPass()!=null)
        {
            if(exTransEnum.getTblExEtcPass().getEtcCardId()!=null)
            {
                tblTransSummary.setExCardId(zero(exTransEnum.getTblExEtcPass().getEtcCardId()));
                SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=formatter.format(exTransEnum.getTblExTrans().getEnTime());
                passId="01"+exTransEnum.getTblExEtcPass().getEtcCardNet()+zero(exTransEnum.getTblExEtcPass().getEtcCardId())+time;
            }

        }
        if(exTransEnum.getTblExTrans().getPassType()==6 && exTransEnum.getTblExMtcPass()!=null)
        {
            if(exTransEnum.getTblExMtcPass().getCpcCardId()!=null)
            {
                tblTransSummary.setExCardId(zero(exTransEnum.getTblExMtcPass().getCpcCardId()));
                SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=formatter.format(exTransEnum.getTblExTrans().getEnTime());
                passId="020000"+zero(exTransEnum.getTblExMtcPass().getCpcCardId())+time;
            }
        }
        if(exTransEnum.getTblExTrans().getPassType()==9 && exTransEnum.getTblExPaperPass()!=null)
        {
            if(exTransEnum.getTblExPaperPass().getLaneHex()!=null)
            {
                SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=formatter.format(exTransEnum.getTblExTrans().getTransTime());
                passId="030"+selectLaneGB(exTransEnum.getTblExPaperPass().getLaneHex())+time;
            }
        }
        Long id=0L;
        if(passId!=null)
        {
            id=StringUtils.isNull(selectSamePassId(passId)) ? 0L : selectSamePassId(passId);
        }
        if(id==0L)
        {
            tblTransSummary.setId(remoteIdProducerService.nextId());
            tblTransSummary.setPassId(passId);
            return tblTransSummaryMapper.insert(tblTransSummary);
        }
        else
        {
            tblTransSummary.setId(id);
            return tblTransSummaryMapper.updateByPrimaryKeySelective(tblTransSummary);
        }
    }

    public static String zero(Long number) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(16);
        formatter.setGroupingUsed(false);
        return formatter.format(number);
    }


}
