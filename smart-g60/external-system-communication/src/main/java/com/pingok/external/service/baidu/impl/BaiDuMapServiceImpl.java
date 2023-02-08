package com.pingok.external.service.baidu.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.BaiDuMapConfig;
import com.pingok.external.domain.baidu.TblBaiDuMapRecord;
import com.pingok.external.mapper.baidu.TblBaiDuMapRecordMapper;
import com.pingok.external.service.baidu.IBaiDuMapService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Slf4j
@Service
public class BaiDuMapServiceImpl implements IBaiDuMapService {


    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblBaiDuMapRecordMapper tblBaiDuMapRecordMapper;

    @Override
    public void eventPublish(TblBaiDuMapRecord baiDuMapRecord)  {

        MultiValueMap map= new LinkedMultiValueMap<>();

        Example example = new Example(TblBaiDuMapRecord.class);

        example.createCriteria()
                .andEqualTo("eventId", baiDuMapRecord.getEventId());
        TblBaiDuMapRecord tblBaiDuMapRecord = tblBaiDuMapRecordMapper.selectOneByExample(example);
        if (tblBaiDuMapRecord == null) {
            tblBaiDuMapRecord = new TblBaiDuMapRecord();
            BeanUtils.copyNotNullProperties(baiDuMapRecord, tblBaiDuMapRecord);
            tblBaiDuMapRecord.setAk(BaiDuMapConfig.AK);
            tblBaiDuMapRecord.setNodeId(BaiDuMapConfig.NODEID);
            tblBaiDuMapRecord.setNodeName(BaiDuMapConfig.NODENAME);
            tblBaiDuMapRecord.setProvinceCode(BaiDuMapConfig.PROVINCECODE);
            tblBaiDuMapRecord.setCityCode(BaiDuMapConfig.CITYCODE);

            tblBaiDuMapRecord.setDataType(BaiDuMapConfig.DATATYPE);
            tblBaiDuMapRecord.setOnlineFlag(1);
            tblBaiDuMapRecord.setRoadName("G60沪杭高速");
            tblBaiDuMapRecord.setId(remoteIdProducerService.nextId());
            tblBaiDuMapRecord.setCreateTime(DateUtils.getNowDate());
            tblBaiDuMapRecord.setCreateUserId(SecurityUtils.getUserId());
            tblBaiDuMapRecordMapper.insert(tblBaiDuMapRecord);
        }
        map.add("ak",BaiDuMapConfig.AK);
        map.add("nodeId",BaiDuMapConfig.NODEID);
        map.add("nodeName",BaiDuMapConfig.NODENAME);
        map.add("provinceCode",BaiDuMapConfig.PROVINCECODE);
        map.add("cityCode",BaiDuMapConfig.CITYCODE);
        map.add("dataType",BaiDuMapConfig.DATATYPE);
        map.add("onlineFlag",1);
        map.add("roadName","G60沪杭高速");
        map.add("eventId",baiDuMapRecord.getEventId());
        map.add("eventType",baiDuMapRecord.getEventType());
        map.add("level",1);//事件级别1: 一般 2: 重要
        map.add("traffic",0);//对道路通行能力的影响：0: 影响未知1: 无影响2: 通行能力下降3: 完全阻断
        map.add("direction",baiDuMapRecord.getDirection());
        map.add("startTime",baiDuMapRecord.getStartTime());
        map.add("endTime",baiDuMapRecord.getEndTime());
        map.add("content",baiDuMapRecord.getContent());
        map.add("location",baiDuMapRecord.getLocation());
        map.add("locationType",1);
        String r = HttpUtil.post(BaiDuMapConfig.HOST, map);
        System.out.println(r);
        if (!StringUtils.isEmpty(r)) {
            if (r.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.containsKey("status") && ret.getString("status").equals("0")) {
                    if (ret.getJSONObject("result").getInteger("toBusStatus") == 0) {
                        tblBaiDuMapRecord.setUpdateTime(DateUtils.getNowDate());
                        tblBaiDuMapRecord.setBaiduEventId(ret.getJSONObject("result").getLong("baiduEventId"));
                    } else {
                        tblBaiDuMapRecord.setOnlineFlag(2);
                        tblBaiDuMapRecord.setUpdateTime(DateUtils.getNowDate());
                        log.error("百度地图推送事件失败，错误信息：" + ret.getString("message"));
                        throw new ServiceException("百度地图推送事件失败，错误信息：" + ret.getString("message"));
                    }
                } else {
                    tblBaiDuMapRecord.setOnlineFlag(2);
                    tblBaiDuMapRecord.setUpdateTime(DateUtils.getNowDate());
                    log.error("百度地图推送事件失败，错误信息：" + ret.getString("message"));
                    throw new ServiceException("百度地图推送事件失败，错误信息：" + ret.getString("message"));
                }
                tblBaiDuMapRecordMapper.updateByPrimaryKey(tblBaiDuMapRecord);
            }
        }
    }

//    @Override
//    public void eventRelieve(Long id) {
//        Example example = new Example(TblBaiDuMapRecord.class);
//        example.createCriteria()
//                .andEqualTo("eventId", id);
//        TblBaiDuMapRecord tblBaiDuMapRecord = tblBaiDuMapRecordMapper.selectOneByExample(example);
//        if (tblBaiDuMapRecord != null) {
//            tblBaiDuMapRecord.setOnlineFlag(2);
//            String r = HttpUtil.post(BaiDuMapConfig.HOST, JSON.toJSONString(tblBaiDuMapRecord));
//            if (!StringUtils.isEmpty(r)) {
//                if (r.startsWith("{")) {
//                    JSONObject ret = JSONObject.parseObject(r);
//                    if (ret.containsKey("status") && ret.getString("status").equals("0")) {
//                        tblBaiDuMapRecord.setUpdateTime(DateUtils.getNowDate());
//                        tblBaiDuMapRecordMapper.updateByPrimaryKey(tblBaiDuMapRecord);
//                    } else {
//                        log.error("百度地图推送事件失败，错误信息：" + ret.getString("message"));
//                        throw new ServiceException("百度地图推送事件失败，错误信息：" + ret.getString("message"));
//                    }
//                }
//            }
//        }
//    }
}
