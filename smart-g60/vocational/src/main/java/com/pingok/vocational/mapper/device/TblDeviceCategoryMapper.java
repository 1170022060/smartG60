package com.pingok.vocational.mapper.device;

import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.domain.emergency.TblEmergencyGroup;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_DEVICE_CATEGORY 数据层
 *
 * @author xia
 */
@Mapper
public interface TblDeviceCategoryMapper extends CommonRepository<TblDeviceCategory> {

    @Select("SELECT ID as \"id\",CATEGORY_NAME as \"categoryName\", PARENT_CATEGORY as \"parentCategory\" FROM TBL_DEVICE_CATEGORY CONNECT BY PRIOR ID = PARENT_CATEGORY START WITH CATEGORY_NUM = #{categoryNum}")
    List<TblDeviceCategory> findByNum(@Param("categoryNum")String categoryNum);

    @Select({"<script>" +
            "select a.ID as \"id\"," +
            "a.CATEGORY_NAME as \"categoryName\"," +
            "b.CATEGORY_NAME as \"parentCategory\"," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || c.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || d.USER_NAME end as \"updateUserName\" from TBL_DEVICE_CATEGORY a " +
            "left join TBL_DEVICE_CATEGORY b on a.PARENT_CATEGORY=b.ID " +
            "left join  SYS_USER c on a.CREATE_USER_ID=c.USER_ID " +
            "left join  SYS_USER d on a.UPDATE_USER_ID=d.USER_ID " +
            "where 1=1" +
            "<when test='categoryName != null'> " +
            "and a.CATEGORY_NAME like CONCAT(CONCAT('%',#{categoryName}),'%') " +
            "</when>"+
            "<when test='parentCategory != null'> " +
            "and a.PARENT_CATEGORY= #{parentCategory} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "</script>"})
    List<Map> selectDeviceCategory(@Param("categoryName") String categoryName, @Param("parentCategory") Long parentCategory, @Param("status") Integer status);

    @Select("select * from TBL_DEVICE_CATEGORY where CATEGORY_NAME= #{categoryName} and PARENT_CATEGORY= #{parentCategory} and rownum = 1")
    TblDeviceCategory checkCategoryNameUnique(@Param("categoryName") String categoryName,@Param("parentCategory") Long parentCategory);
}
