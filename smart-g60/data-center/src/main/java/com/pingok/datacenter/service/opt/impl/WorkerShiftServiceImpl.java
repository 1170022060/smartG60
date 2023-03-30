package com.pingok.datacenter.service.opt.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.opt.TblWorkerShift;
import com.pingok.datacenter.mapper.opt.TblWorkerShiftMapper;
import com.pingok.datacenter.service.opt.IWorkerShiftService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 工班 服务层处理
 *
 * @author ruoyi
 */
@Service
public class WorkerShiftServiceImpl implements IWorkerShiftService {

    @Autowired
    private TblWorkerShiftMapper tblWorkerShiftMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void workerShift(JSONObject obj) {
        Example example = new Example(TblWorkerShift.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("laneHex", obj.getString("laneHex"));
        criteria.andEqualTo("onTime", obj.getDate("onTime"));
        TblWorkerShift workerShift = tblWorkerShiftMapper.selectOneByExample(example);
        if (StringUtils.isNull(workerShift)) {
            workerShift = new TblWorkerShift();
            workerShift.setId(remoteIdProducerService.nextId());
            workerShift.setLaneHex(obj.getString("laneHex"));
            workerShift.setOptId(obj.getInteger("optId"));
            workerShift.setShift(obj.getInteger("shift"));
            workerShift.setOnTime(obj.getDate("onTime"));
            workerShift.setOffTime(obj.getDate("offTime"));
            workerShift.setWorkDate(obj.getDate("workDate"));
            workerShift.setCash(obj.getInteger("cash"));
            workerShift.setChargeCard(obj.getInteger("chargeCard"));
            workerShift.setStoredCard(obj.getInteger("storedCard"));
            workerShift.setThirdPayment(obj.getInteger("thirdPayment"));
            workerShift.setUnionPayCard(obj.getInteger("unionPayCard"));
            workerShift.setAliPay(obj.getInteger("aliPay"));
            workerShift.setWeChat(obj.getInteger("weChat"));
            workerShift.setBusCard(obj.getInteger("busCard"));
            workerShift.setDigitalYuan(obj.getInteger("digitalYuan"));
            workerShift.setReceiptCnt(obj.getInteger("receiptCnt"));
            workerShift.setCardCnt(obj.getInteger("cardCnt"));
            tblWorkerShiftMapper.insert(workerShift);
        }
    }
}
