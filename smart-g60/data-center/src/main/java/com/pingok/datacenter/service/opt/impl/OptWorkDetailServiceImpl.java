package com.pingok.datacenter.service.opt.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.opt.TblOptWorkDetail;
import com.pingok.datacenter.mapper.opt.TblOptWorkDetailMapper;
import com.pingok.datacenter.service.opt.IOptWorkDetailService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 排班当岗 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OptWorkDetailServiceImpl implements IOptWorkDetailService {

    @Autowired
    private TblOptWorkDetailMapper tblOptWorkDetailMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void optWorkDetail(JSONObject obj) {
        TblOptWorkDetail optWorkDetail = new TblOptWorkDetail();
        optWorkDetail.setId(remoteIdProducerService.nextId());
        optWorkDetail.setOptId(obj.getInteger("optId"));
        optWorkDetail.setWorkId(obj.getInteger("workId"));
        optWorkDetail.setWorkStep(obj.getInteger("workStep"));
        optWorkDetail.setWorkMinutes(obj.getInteger("workMinutes"));
        optWorkDetail.setWorkDate(obj.getDate("workDate"));
        optWorkDetail.setLaneNum(obj.getInteger("laneNum"));
        optWorkDetail.setHisWorkId(obj.getInteger("hisWorkId"));
        tblOptWorkDetailMapper.insert(optWorkDetail);
    }

}
