package com.pingok.devicemonitor.mapper.smartToilet;

import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletInfo;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;

@Mapper
public interface TblSmartToiletInfoMapper extends CommonRepository<TblSmartToiletInfo> {


//    @Select("SELECT " +
//            "ID AS \"id\", " +
//            "'当班厕长：' || TOI_CHIEF || '，当班保洁（上午）：' || WORK_CLEANER_AM || '，当班保洁（下午）：' || WORK_CLEANER_PM AS \"content\"  " +
//            "FROM " +
//            "TBL_SMART_TOILET_SCHEDULE  " +
//            "WHERE " +
//            "ROWNUM = 1  " +
//            "AND TOILET_ID = #{toiletId} " +
//            "ORDER BY " +
//            "NVL( UPDATE_TIME, CREATE_TIME ) DESC ")
// TODO: 2023/4/7 0007 临时删除保洁
    @Select("SELECT " +
            "ID AS \"id\", " +
            "'当班厕长：' || TOI_CHIEF  AS \"content\"  " +
            "FROM " +
            "TBL_SMART_TOILET_SCHEDULE  " +
            "WHERE " +
            "ROWNUM = 1  " +
            "AND TOILET_ID = #{toiletId} " +
            "ORDER BY " +
            "NVL( UPDATE_TIME, CREATE_TIME ) DESC ")
    Map schedule(@Param("toiletId") Long toiletId);
}