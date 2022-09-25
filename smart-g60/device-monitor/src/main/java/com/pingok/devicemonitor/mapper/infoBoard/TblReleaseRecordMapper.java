package com.pingok.devicemonitor.mapper.infoBoard;

import com.pingok.devicemonitor.domain.infoBoard.TblReleaseRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblReleaseRecordMapper extends CommonRepository<TblReleaseRecord> {
    @Select("SELECT ID as \"id\", DEVICE_ID as \"deviceId\", PRESET_USER_ID as \"presetUserId\"," +
            "STATUS as \"status\", PUBLISH_CONTENT as \"publishContent\" " +
            " FROM TBL_RELEASE_RECORD where 1=1 and DEVICE_ID = #{deviceId} ")
    TblReleaseRecord findByDeviceId(@Param("deviceId") String deviceId);
}
