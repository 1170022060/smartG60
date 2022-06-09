package com.pingok.vocational.service.project;

import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.domain.project.TblProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目信息 业务层
 *
 * @author ruoyi
 */
public interface IProjectInfoService {

    /**
     * 查询项目信息ID、名称
     *
     * @return 项目信息ID、名称
     */
    List<Map> selectProjectName();

    /**
     * 根据Id查询项目信息
     *
     * @param Id Id
     * @return 项目信息
     */
    public TblProjectInfo selectProjectInfoById(Long Id);
}
