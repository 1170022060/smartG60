package com.pingok.external.service.beiDou.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.pingok.external.config.BeiDouConfig;
import com.pingok.external.domain.gps.TblMaintainCarGpsLog;
import com.pingok.external.mapper.gps.TblMaintainCarGpsLogMapper;
import com.pingok.external.mapper.gps.TblMaintainCarGpsMapper;
import com.pingok.external.service.beiDou.IGpsService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.gps.TblMaintainCarGps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service
public class GpsServiceImpl implements IGpsService {



    @Autowired
    private TblMaintainCarGpsMapper tblMaintainCarGpsMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblMaintainCarGpsLogMapper tblMaintainCarGpsLogMapper;

    @Override
    public List<TblMaintainCarGps> carGps() {
        Example example = new Example(TblMaintainCarGps.class);
        example.createCriteria().andGreaterThanOrEqualTo("time",DateUtils.parseDate(DateUtils.dateTime(DateUtils.getNowDate(),DateUtils.YYYY_MM_DD+" 00:00:00")));
        return tblMaintainCarGpsMapper.selectByExample(example);
    }

    @Override
    public void getCarsGps() {
        try {
            JSONObject param = new JSONObject();
            param.put("username", BeiDouConfig.USERNAME);
            param.put("password", MD5Utils.encodeHexString(BeiDouConfig.PASSWORD.getBytes("UTF-8")));
            param.put("md5", 1);
            param.put("regnum", "all");
            String r = HttpUtil.get(BeiDouConfig.HOST, param);
            if (!StringUtils.isEmpty(r)) {
                Example example = new Example(TblMaintainCarGps.class);
                JSONArray ret = JSONArray.parseArray(r);
                JSONObject object;
                TblMaintainCarGps maintainCarGps;
                TblMaintainCarGpsLog maintainCarGpsLog;
                int size = ret.size();
                for (int i = 0; i < size; i++) {
                    object = ret.getJSONObject(i);
                    example.clear();
                    example.createCriteria()
                            .andEqualTo("licensePlate", object.getString("LicensePlate"));
                    maintainCarGps = tblMaintainCarGpsMapper.selectOneByExample(example);
                    if (maintainCarGps == null) {
                        maintainCarGps = new TblMaintainCarGps();
                        maintainCarGps.setId(remoteIdProducerService.nextId());
                        maintainCarGps.setCreateTime(DateUtils.getNowDate());
                        maintainCarGps.setLicensePlate(object.getString("LicensePlate"));
                        maintainCarGps.setCoordinates(object.getJSONObject("Geometry").getJSONArray("Coordinates").toJSONString());
                        maintainCarGps.setReverseGeocode(object.getString("ReverseGeocode"));
                        maintainCarGps.setSpeed(object.getInteger("Speed"));
                        maintainCarGps.setTime(DateUtils.timestampToDate(object.getLong("TimeStamp") * 1000));
                        tblMaintainCarGpsMapper.insert(maintainCarGps);
                    } else {
                        maintainCarGps.setUpdateTime(DateUtils.getNowDate());
                        maintainCarGps.setCoordinates(object.getJSONObject("Geometry").getJSONArray("Coordinates").toJSONString());
                        maintainCarGps.setReverseGeocode(object.getString("ReverseGeocode"));
                        maintainCarGps.setSpeed(object.getInteger("Speed"));
                        maintainCarGps.setTime(DateUtils.timestampToDate(object.getLong("TimeStamp") * 1000));
                        tblMaintainCarGpsMapper.updateByPrimaryKey(maintainCarGps);
                    }
                    maintainCarGpsLog = new TblMaintainCarGpsLog();
                    BeanUtils.copyNotNullProperties(maintainCarGps,maintainCarGpsLog);
                    maintainCarGpsLog.setId(remoteIdProducerService.nextId());
                    maintainCarGpsLog.setCreateTime(DateUtils.getNowDate());
                    tblMaintainCarGpsLogMapper.insert(maintainCarGpsLog);
                }
            } else {
                throw new ServiceException("养护车辆GPS信息更新失败,接口无响应");
            }
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("养护车辆GPS信息更新失败：" + e.getMessage());
        }
    }
}
