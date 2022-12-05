package com.pingok.datacenter.service.specialRecord.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.baseinfo.TblBaseStationInfo;
import com.pingok.datacenter.domain.specialRecord.TblSpecialRecord;
import com.pingok.datacenter.mapper.baseinfo.TblBaseStationInfoMapper;
import com.pingok.datacenter.mapper.specialRecord.TblSpecialRecordMapper;
import com.pingok.datacenter.service.specialRecord.ISpecialRecordService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * 特情信息 服务层处理
 *
 * @author qiumin
 */
@Service
public class SpecialRecordServiceImpl implements ISpecialRecordService {

    @Autowired
    private TblSpecialRecordMapper tblSpecialRecordMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private TblBaseStationInfoMapper tblBaseStationInfoMapper;

    @Override
    @Transactional
    public void add(TblSpecialRecord tblSpecialRecord) {
        tblSpecialRecord.setId(remoteIdProducerService.nextId());
        tblSpecialRecord.setCreateTime(DateUtils.getNowDate());
        if(tblSpecialRecord.getRecordId()==null){
            // 查询用户信息
            R<LoginUser> userResult = remoteUserService.getUserInfo(SecurityUtils.getUsername(), SecurityConstants.INNER);
            if (R.FAIL == userResult.getCode()) {
                throw new ServiceException(userResult.getMsg());
            }
            if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
                throw new ServiceException("登录用户：" + SecurityUtils.getUsername() + " 不存在");
            }
            SysUser user = userResult.getData().getSysUser();
            tblSpecialRecord.setRecordId(tblSpecialRecord.getNetWork()+tblSpecialRecord.getStationId()+tblSpecialRecord.getLaneId()+DateUtils.dateTimeNowDtl());
            tblSpecialRecord.setCreateUserId(SecurityUtils.getUserId());
            tblSpecialRecord.setOptId(Long.valueOf(user.getUserName()));
            tblSpecialRecord.setOptName(user.getNickName());
            int hour = DateUtils.getNowDate().getHours();
            if (hour >= 0 && hour < 7) {
                tblSpecialRecord.setShift(1);
                tblSpecialRecord.setWorkDate(DateUtils.parseDate(DateUtils.getDate()));
            } else if (hour >= 7 && hour < 19) {
                tblSpecialRecord.setShift(2);
                tblSpecialRecord.setWorkDate(DateUtils.parseDate(DateUtils.getDate()));
            } else if (hour >= 19 && hour < 24) {
                tblSpecialRecord.setShift(1);
                tblSpecialRecord.setWorkDate(DateUtils.parseDate(DateUtils.getNextDay(DateUtils.getNowDate(), "1")));
            }
        }
        tblSpecialRecordMapper.insert(tblSpecialRecord);
        Example example = new Example(TblBaseStationInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("stationId", tblSpecialRecord.getStationId());
        criteria.andEqualTo("netWork", tblSpecialRecord.getNetWork());
        TblBaseStationInfo tblBaseStationInfo = tblBaseStationInfoMapper.selectOneByExample(example);
        JSONObject param = new JSONObject();
        if (tblBaseStationInfo != null) {
            param.put("stationName", tblBaseStationInfo.getStationName());
        }
        param.put("markName", tblSpecialRecord.getMarkName());
        JSONObject data = new JSONObject();
        data.put("type", "specialRecord");
        data.put("data", param);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
        kafkaEnum.setData(data.toJSONString());
        remoteKafkaService.send(kafkaEnum);
    }

    @Override
    public void handleSpecial(Long id, Integer optType, String message,String ip,String port) {
        TblSpecialRecord tblSpecialRecord = tblSpecialRecordMapper.selectByPrimaryKey(id);
        if (tblSpecialRecord == null) {
            throw new ServiceException("id错误");
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(SecurityUtils.getUsername(), SecurityConstants.INNER);
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new ServiceException("登录用户：" + SecurityUtils.getUsername() + " 不存在");
        }
        SysUser user = userResult.getData().getSysUser();

        JSONObject param = new JSONObject();
        param.put("ip", ip);
        param.put("port", port);
        param.put("recordID", tblSpecialRecord.getRecordId());
        param.put("OptID", user.getUserName());
        param.put("OptName", user.getNickName());

        int hour = DateUtils.getNowDate().getHours();
        if (hour >= 0 && hour < 7) {
            param.put("Shift", 1);
            param.put("WorkDate", DateUtils.getDate());
        } else if (hour >= 7 && hour < 19) {
            param.put("Shift", 2);
            param.put("WorkDate", DateUtils.getDate());
        } else if (hour >= 19 && hour < 24) {
            param.put("Shift", 1);
            param.put("WorkDate", DateUtils.getNextDay(DateUtils.getNowDate(), "1"));
        }
        param.put("Time", DateUtils.getNowDate());
        param.put("OptType", optType);
        param.put("Message", message);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.SPECIAL_RECORD);
        kafkaEnum.setData(param.toJSONString());
        R r = remoteKafkaService.send(kafkaEnum);
        if (r.getCode() == R.SUCCESS) {
            tblSpecialRecord.setOptType(optType);
            tblSpecialRecord.setHandleMessage(message);
            tblSpecialRecord.setUpdateTime(DateUtils.getNowDate());
            tblSpecialRecord.setUpdateUserId(SecurityUtils.getUserId());
            tblSpecialRecord.setStatus(1);
            tblSpecialRecordMapper.updateByPrimaryKey(tblSpecialRecord);
        }
    }
}
