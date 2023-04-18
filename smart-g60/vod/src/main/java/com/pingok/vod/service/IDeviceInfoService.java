package com.pingok.vod.service;


import com.alibaba.fastjson.JSONArray;
import com.pingok.vod.domain.TblDeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备信息 服务层
 *
 * @author qiumin
 */
public interface IDeviceInfoService {

    TblDeviceInfo findById(Long id);

    List<TblDeviceInfo> findAllByType();

    void add(TblDeviceInfo tblDeviceInfo);

    void edit(TblDeviceInfo tblDeviceInfo);

    TblDeviceInfo findByDeviceId(String deviceId);

    void updateCameraList(JSONArray jsonArray);

    /**
     *
     * @param roadId
     * @return
     */
    List<Long> getDeviceByPileNo(Integer roadId);
}
