package com.pingok.vocational.service.pursues.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.vocational.domain.pursues.TblLprSummary;
import com.pingok.vocational.domain.pursues.TblPursuesGantry;
import com.pingok.vocational.domain.pursues.TblPursuesLpr;
import com.pingok.vocational.domain.pursues.TblPursuesTrans;
import com.pingok.vocational.domain.roster.TblPursuesListRecord;
import com.pingok.vocational.mapper.pursues.TblPursuesGantryMapper;
import com.pingok.vocational.mapper.pursues.TblPursuesLprMapper;
import com.pingok.vocational.mapper.pursues.TblPursuesTransMapper;
import com.pingok.vocational.mapper.pursues.TblPursuesListRecordMapper;
import com.pingok.vocational.service.pursues.IPursuesService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanValidators;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 追讨功能 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PursuesServiceImpl implements IPursuesService {

    private static final Logger log = LoggerFactory.getLogger(PursuesServiceImpl.class);

    @Autowired
    private TblPursuesListRecordMapper tblPursuesListRecordMapper;
    @Autowired
    private TblPursuesTransMapper tblPursuesTransMapper;
    @Autowired
    private TblPursuesGantryMapper tblPursuesGantryMapper;
    @Autowired
    private TblPursuesLprMapper tblPursuesLprMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    protected Validator validator;

    @Override
    public List<Map> selectPursuesList(Date startTime, Date endTime,String vehPlate) {
        return tblPursuesListRecordMapper.selectPursuesList(startTime, endTime,vehPlate);
    }

    @Override
    public String importPursues(List<TblPursuesListRecord> pursuesList) {
        if (StringUtils.isNull(pursuesList) || pursuesList.size() == 0)
        {
            throw new ServiceException("导入追讨名单不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TblPursuesListRecord pursues : pursuesList)
        {
            try
            {
                // 验证是否存在这个协调ID
                if (tblPursuesListRecordMapper.existsWithPrimaryKey(pursues.getConcertId()))
                {
                    BeanValidators.validateWithException(validator, pursues);
                    this.tblPursuesListRecordMapper.updateByPrimaryKey(pursues);
                    successNum++;
                }
                else
                {
                    BeanValidators.validateWithException(validator, pursues);
                    this.tblPursuesListRecordMapper.insert(pursues);
                    successNum++;
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = failureNum + "、协调ID " + pursues.getConcertId() + " 导入失败： ";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }

    @Override
    public void Pursues(Date startTime,Date endTime) {
        Example example = new Example(TblPursuesListRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (startTime !=null) {
            criteria.andGreaterThanOrEqualTo("endTime", startTime);
        }
        if (endTime !=null) {
            criteria.andLessThanOrEqualTo("endTime", endTime);
        }
        int[] freeStatus= new int[]{8,10};
        List<TblPursuesListRecord> pursuesList =tblPursuesListRecordMapper.selectByExample(example);
        for(TblPursuesListRecord pursuesRecord:pursuesList)
        {
            TblPursuesTrans pursuesTrans=tblPursuesTransMapper.selectTransByPassId(pursuesRecord.getPassId());
            List<TblPursuesGantry> pursuesGantryList=tblPursuesGantryMapper.selectGantryByPassId(pursuesRecord.getPassId());

            if(pursuesTrans!=null)
            {
                pursuesTrans.setId(remoteIdProducerService.nextId());
                pursuesTrans.setConcertId(pursuesRecord.getConcertId());
                List<String> enImageListStr=tblPursuesTransMapper.selectTransImageByGid(pursuesTrans.getEnGid());
                if(enImageListStr.size()!=0)
                {
                    pursuesTrans.setEnImage(JSON.toJSONString(enImageListStr));
                }
                List<String> exImageListStr=tblPursuesTransMapper.selectTransImageByGid(pursuesTrans.getExGid());
                if(exImageListStr.size()!=0)
                {
                    pursuesTrans.setExImage(JSON.toJSONString(exImageListStr));
                }
                tblPursuesTransMapper.insert(pursuesTrans);
                if(pursuesGantryList.size()!=0)
                {
                    for(TblPursuesGantry pursuesGantry :pursuesGantryList)
                    {
                        pursuesGantry.setId(remoteIdProducerService.nextId());
                        pursuesGantry.setLicenseImage(tblPursuesGantryMapper.selectGantryImageByVeh(pursuesGantry.getVehiclePlate(),pursuesGantry.getTransTime()));
                        tblPursuesGantryMapper.insert(pursuesGantry);
                    }
                }
                for(int status :freeStatus)
                {
                    if(pursuesTrans.getExVehStatus()==status)
                    {
                        pursuesRecord.setRemark("该车车种为免费车");
                        break;
                    }
                }
                pursuesRecord.setRemark("");
                tblPursuesListRecordMapper.updateByPrimaryKey(pursuesRecord);
                break;
            }
            if(pursuesGantryList.size()!=0)
            {
                TblPursuesGantry pursuesGantry=pursuesGantryList.get(0);
                pursuesTrans=tblPursuesTransMapper.selectTransByPlate(pursuesRecord.getVehPlate(),pursuesGantry.getTransTime());

            }else
            {
                pursuesTrans=tblPursuesTransMapper.selectTransByPlate(pursuesRecord.getVehPlate(),pursuesRecord.getEndTime());
            }
            if(pursuesTrans!=null)
            {
                pursuesTrans.setId(remoteIdProducerService.nextId());
                pursuesTrans.setConcertId(pursuesRecord.getConcertId());
                List<String> enImageListStr=tblPursuesTransMapper.selectTransImageByGid(pursuesTrans.getEnGid());
                if(enImageListStr.size()!=0)
                {
                    pursuesTrans.setEnImage(JSON.toJSONString(enImageListStr));
                }
                List<String> exImageListStr=tblPursuesTransMapper.selectTransImageByGid(pursuesTrans.getExGid());
                if(exImageListStr.size()!=0)
                {
                    pursuesTrans.setExImage(JSON.toJSONString(exImageListStr));
                }
                tblPursuesTransMapper.insert(pursuesTrans);
                if(pursuesTrans.getExPassType()==5)
                {
                    pursuesRecord.setRemark("入口其他介质进入后由ETC卡出");
                }
                if(pursuesTrans.getExPassType()==6)
                {
                    pursuesRecord.setRemark("入口其他介质进入后由CPC卡出");
                }
                if(pursuesTrans.getExPassType()==9)
                {
                    pursuesRecord.setRemark("入口其他介质进入后由纸券出");
                }
                tblPursuesListRecordMapper.updateByPrimaryKey(pursuesRecord);
                break;
            }
            TblLprSummary lprPlate=tblPursuesLprMapper.selectLprByPlate(pursuesRecord.getVehPlate(),pursuesRecord.getEndTime());
            if(lprPlate!=null)
            {
                TblPursuesLpr pursuesLpr=new TblPursuesLpr();
                List<TblLprSummary> lprQueue=tblPursuesLprMapper.selectLprByLaneGb(lprPlate.getExLaneGb(),lprPlate.getExTransTime());
                for(TblLprSummary lprSummary: lprQueue) {
                    pursuesLpr.setId(remoteIdProducerService.nextId());
                    pursuesLpr.setConcertId(pursuesRecord.getConcertId());
                    pursuesLpr.setConcertId(lprSummary.getExVehPlate());
                    pursuesLpr.setTransTime(lprSummary.getExTransTime());
                    tblPursuesLprMapper.insert(pursuesLpr);
                }
                List<TblPursuesTrans> pursuesTransList=tblPursuesTransMapper.selectTransByLaneGb(lprPlate.getExLaneGb(),lprPlate.getExTransTime());
                for(TblPursuesTrans pursuesGb: pursuesTransList) {
                    pursuesGb.setId(remoteIdProducerService.nextId());
                    pursuesGb.setConcertId(pursuesRecord.getConcertId());
                    List<String> enImageListStr=tblPursuesTransMapper.selectTransImageByGid(pursuesTrans.getEnGid());
                    if(enImageListStr.size()!=0)
                    {
                        pursuesGb.setEnImage(JSON.toJSONString(enImageListStr));
                    }
                    List<String> exImageListStr=tblPursuesTransMapper.selectTransImageByGid(pursuesTrans.getExGid());
                    if(exImageListStr.size()!=0)
                    {
                        pursuesGb.setExImage(JSON.toJSONString(exImageListStr));
                    }
                    tblPursuesTransMapper.insert(pursuesGb);
                }
                break;
            }
            if(pursuesGantryList.size()!=0)
            {
                TblPursuesGantry pursuesGantry=pursuesGantryList.get(0);
                String deviceName = tblPursuesGantryMapper.selectGantryName(pursuesGantry.getGantryId());
                for(TblPursuesGantry pursuesGantryIns :pursuesGantryList)
                {
                    pursuesGantryIns.setId(remoteIdProducerService.nextId());
                    pursuesGantryIns.setLicenseImage(tblPursuesGantryMapper.selectGantryImageByVeh(pursuesGantry.getVehiclePlate(),pursuesGantry.getTransTime()));
                    tblPursuesGantryMapper.insert(pursuesGantryIns);
                }
                if(deviceName.contains("立交"))
                {
                    pursuesRecord.setRemark("该车从立交驶出");
                    tblPursuesListRecordMapper.updateByPrimaryKey(pursuesRecord);
                }
                if(deviceName.contains("省界"))
                {
                    pursuesRecord.setRemark("该车从省界门架出省");
                    tblPursuesListRecordMapper.updateByPrimaryKey(pursuesRecord);
                }
            }
        }
    }

    @Override
    public List<Map> selectPursuesTrans(String concertId) {
        return tblPursuesTransMapper.selectPursuesTrans(concertId);
    }

    @Override
    public List<Map> selectPursuesGantry(String concertId) {
        return tblPursuesGantryMapper.selectPursuesGantry(concertId);
    }

    @Override
    public List<Map> selectPursuesLpr(String concertId) {
        return tblPursuesLprMapper.selectPursuesLpr(concertId);
    }

    @Override
    public TblPursuesTrans test(String vehPlate, Date time) {
        return tblPursuesTransMapper.selectTransByPlate(vehPlate,time);
    }
}
