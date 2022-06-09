package com.pingok.external.service.baidu.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
public class BaiDuMapServiceImpl implements IBaiDuMapService {

    @Value("${baidu.host}")
    private String host;

    @Value("${baidu.ak}")
    private String ak;

    @Value("${baidu.nodeId}")
    private Integer nodeId;

    @Value("${baidu.nodeName}")
    private String nodeName;

    @Value("${baidu.provinceCode}")
    private Integer provinceCode;

    @Value("${baidu.cityCode}")
    private Integer cityCode;

    @Value("${baidu.dataType}")
    private String dataType;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblBaiDuMapRecordMapper tblBaiDuMapRecordMapper;

    @Override
    public void eventPublish(TblBaiDuMapRecord baiDuMapRecord) {
        Example example = new Example(TblBaiDuMapRecord.class);
        example.createCriteria()
                .andEqualTo("eventId", baiDuMapRecord.getEventId());
        TblBaiDuMapRecord tblBaiDuMapRecord = tblBaiDuMapRecordMapper.selectOneByExample(example);
        if (tblBaiDuMapRecord == null) {
            tblBaiDuMapRecord = new TblBaiDuMapRecord();
            BeanUtils.copyNotNullProperties(baiDuMapRecord, tblBaiDuMapRecord);
            baiDuMapRecord.setAk(ak);
            tblBaiDuMapRecord.setNodeId(nodeId);
            tblBaiDuMapRecord.setNodeName(nodeName);
            tblBaiDuMapRecord.setProvinceCode(provinceCode);
            tblBaiDuMapRecord.setCityCode(cityCode);
            tblBaiDuMapRecord.setDataType(dataType);
            tblBaiDuMapRecord.setOnlineFlag(1);
            tblBaiDuMapRecord.setRoadName("G60沪杭高速");
            tblBaiDuMapRecord.setId(remoteIdProducerService.nextId());
            tblBaiDuMapRecord.setCreateTime(DateUtils.getNowDate());
            tblBaiDuMapRecord.setCreateUserId(SecurityUtils.getUserId());
            tblBaiDuMapRecordMapper.insert(tblBaiDuMapRecord);
        }
        String r = HttpUtil.post(host, JSON.toJSONString(tblBaiDuMapRecord));
        if (!StringUtils.isEmpty(r)) {
            if (r.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.containsKey("status") && ret.getString("status").equals("0")) {
                    if (ret.containsKey("result") && ret.getJSONObject("result") != null) {
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
                        tblBaiDuMapRecord.setOnlineFlag(1);
                        tblBaiDuMapRecord.setUpdateTime(DateUtils.getNowDate());
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

    @Override
    public void eventRelieve(Long id) {
        Example example = new Example(TblBaiDuMapRecord.class);
        example.createCriteria()
                .andEqualTo("eventId", id);
        TblBaiDuMapRecord tblBaiDuMapRecord = tblBaiDuMapRecordMapper.selectOneByExample(example);
        if (tblBaiDuMapRecord != null) {
            tblBaiDuMapRecord.setOnlineFlag(2);
            String r = HttpUtil.post(host, JSON.toJSONString(tblBaiDuMapRecord));
            if (!StringUtils.isEmpty(r)) {
                if (r.startsWith("{")) {
                    JSONObject ret = JSONObject.parseObject(r);
                    if (ret.containsKey("status") && ret.getString("status").equals("0")) {
                        tblBaiDuMapRecord.setUpdateTime(DateUtils.getNowDate());
                        tblBaiDuMapRecordMapper.updateByPrimaryKey(tblBaiDuMapRecord);
                    } else {
                        log.error("百度地图推送事件失败，错误信息：" + ret.getString("message"));
                        throw new ServiceException("百度地图推送事件失败，错误信息：" + ret.getString("message"));
                    }
                }
            }
        }
    }
}
