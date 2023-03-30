package com.pingok.vocational.service.infoboard;


import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.infoboard.VmsInfoByTypeList;
import com.pingok.vocational.domain.infoboard.VmsPresetList;

import java.util.List;
import java.util.Map;

public interface IInfoBoardService {

    // 获取设备列表（按类型分组）
    List<VmsInfoByTypeList> getListByType(String type, String protocol);

    // 获取预设列表（分组）
    List<VmsPresetList> getPreset();

    // 情报板发布
    void publish(JSONObject content);
}
