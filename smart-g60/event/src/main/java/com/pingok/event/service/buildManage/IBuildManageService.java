package com.pingok.event.service.buildManage;

import com.pingok.event.domain.buildManage.TblBuildManage;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IBuildManageService {

    /**
     * 查询施工管理信息
     * @param content
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> getBuilManaInfo(String content, Date startTime,Date endTime);

    /**
     * 新增施工管理
     * @param tblBuildManage
     * @return
     */
    int add(TblBuildManage tblBuildManage);

    /**
     * 编辑施工管理
     * @param tblBuildManage
     * @return
     */
    int edit(TblBuildManage tblBuildManage);
}
