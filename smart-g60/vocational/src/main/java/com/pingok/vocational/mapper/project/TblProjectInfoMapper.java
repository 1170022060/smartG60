package com.pingok.vocational.mapper.project;

import com.pingok.vocational.domain.project.TblProjectInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_PROJECT_INFO 数据层
 *
 * @author xia
 */
public interface TblProjectInfoMapper extends CommonRepository<TblProjectInfo> {

    @Select("select ID as \"id\",PROJECT_NAME as \"projectName\" from TBL_PROJECT_INFO ")
    List<Map> selectProjectName();
}
