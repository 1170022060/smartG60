package com.pingok.vocational.service.infoboard;


import com.pingok.vocational.domain.infoboard.VmsInfoByTypeList;

import java.util.List;

public interface IInfoBoardService {

    // 获取设备列表（按类型分组）
    List<VmsInfoByTypeList> getListByType(String type);
}
