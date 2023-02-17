package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblLargeTransportVehinfo;
import com.pingok.external.mapper.primary.TblLargeTransportMapper;
import com.pingok.external.service.primary.ITblLargeTransportService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class TblLargeTransportServiceImpl implements ITblLargeTransportService {

    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblLargeTransportMapper tblLargeTransportMapper;

    @Override
    public void collect() {
        //发起kafka
        int ret = 200;
        try {
            JSONObject params = new JSONObject();
            params.put("pageNum", 1);
            params.put("pageSize", 1000);
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_LARGE_TRANSPORT);
            kafkaEnum.setData(JSON.toJSONString(params));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("超限流量、超限率时间对比数据获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getLargeTransport(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblLargeTransportVehinfo LargeTransportInfo = new TblLargeTransportVehinfo();

            LargeTransportInfo.setId(obj.getString("Id"));
            LargeTransportInfo.setMainId(obj.getString("mainId"));
            LargeTransportInfo.setRecordNo(obj.getString("recordNo"));
            LargeTransportInfo.setCertNo(obj.getString("certNo"));
            LargeTransportInfo.setTransportUnit(obj.getString("transportUnit"));
            LargeTransportInfo.setVehicleLicense(obj.getString("vehicleLicense"));
            LargeTransportInfo.setVehicleType(obj.getString("vehicleType"));
            LargeTransportInfo.setVehicleWeight(obj.getString("vehicleWeight"));
            LargeTransportInfo.setGoodsName(obj.getString("goodsName"));
            LargeTransportInfo.setGoodsWeight(obj.getString("goodsWeight"));
            LargeTransportInfo.setBodyLenght(obj.getString("bodyLenght"));
            LargeTransportInfo.setBodyWidth(obj.getString("bodyWidth"));
            LargeTransportInfo.setBodyHeight(obj.getString("bodyHeight"));
            LargeTransportInfo.setAxleLoadDist(obj.getString("axleLoadDist"));
            LargeTransportInfo.setDateEnd(obj.getString("dateEnd"));
            LargeTransportInfo.setRoute(obj.getString("route"));
            LargeTransportInfo.setCertificatUnit(obj.getString("certificatUnit"));
            LargeTransportInfo.setApplicationDate(obj.getString("applicationDate"));
            LargeTransportInfo.setOperator(obj.getString("operator"));
            LargeTransportInfo.setFzDate(obj.getString("fzDate"));
            LargeTransportInfo.setCreateTime(obj.getDate("createTime"));
            LargeTransportInfo.setUpdateTime(obj.getDate("updateTime"));
            LargeTransportInfo.setIsValid(obj.getLong("isValid"));
            LargeTransportInfo.setLoadWeight(obj.getString("loadWeight"));
            LargeTransportInfo.setUnionKey(obj.getString("unionKey"));

            tblLargeTransportMapper.insert(LargeTransportInfo);
        }
    }
}
