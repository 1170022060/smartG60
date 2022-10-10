package com.pingok.monitor.mapper.device;

import com.pingok.monitor.domain.device.vo.DeviceInfoVo;
import com.ruoyi.common.core.annotation.Excel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TblDeviceInfo 数据层
 *
 * @author xia
 */
public interface TblDeviceInfoMapper {

    @Select("SELECT " +
            "tdi.ID AS \"id\", " +
            "tdi.DEVICE_ID AS \"deviceId\", " +
            "tdi.DEVICE_NAME AS \"deviceName\", " +
            "tdi.DEVICE_BRAND AS \"deviceBrand\", " +
            "tdi.DEVICE_MODEL AS \"deviceModel\", " +
            "tdi.DEVICE_IP AS \"deviceIp\", " +
            "tdi.PORT AS \"port\", " +
            "tdi.DIRECTION AS \"direction\", " +
            "tdi.STATION_BELONG AS \"stationBelong\", " +
            "tdi.PILE_NO AS \"pileNo\", " +
            "tdi.GPS AS \"gps\", " +
            "tdi.CAMERA_ID AS \"cameraId\", " +
            "TO_CHAR(tds.TIME, 'yyyy-mm-dd hh:mm:ss') AS \"time\", " +
            "NVL( tds.STATUS, 0 ) AS \"status\", " +
            "CASE " +
            "WHEN tds.STATUS IS NULL THEN " +
            "'状态未知' ELSE tds.STATUS_DESC  " +
            "END AS \"statusDesc\" , " +
            "trr.ID AS \"tblReleaseRecord.id\"," +
            "trr.DEVICE_ID AS \"tblReleaseRecord.deviceId\"," +
            "trr.DEVICE_NAME AS \"tblReleaseRecord.deviceName\"," +
            "trr.PILE_NO AS \"tblReleaseRecord.pileNo\"," +
            "trr.INFO_TYPE AS \"tblReleaseRecord.infoType\"," +
            "trr.PRESET_NAME AS \"tblReleaseRecord.presetName\"," +
            "trr.PRESET_INFO AS \"tblReleaseRecord.presetInfo\"," +
            "trr.TYPEFACE AS \"tblReleaseRecord.typeface\"," +
            "trr.TYPEFACE_SIZE AS \"tblReleaseRecord.typefaceSize\"," +
            "trr.COLOR AS \"tblReleaseRecord.color\"," +
            "trr.PICTURE_TYPE AS \"tblReleaseRecord.pictureType\"," +
            "trr.PRESET_TIME AS \"tblReleaseRecord.presetTime\"," +
            "trr.REVOKE_TIME AS \"tblReleaseRecord.revokeTime\"," +
            "trr.PRESET_USER_ID AS \"tblReleaseRecord.presetUserId\"," +
            "trr.REVOKE_USER_ID AS \"tblReleaseRecord.revokeUserId\" " +
            "FROM " +
            "TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID  " +
            "left join TBL_RELEASE_RECORD trr on trr.DEVICE_ID=tdi.DEVICE_ID and trr.ID in (select ID " +
            "  from (select t.*,                " +
            "               row_number() over(partition by t.DEVICE_ID order by t.PRESET_TIME desc) rn " +
            "          from TBL_RELEASE_RECORD t) trr " +
            " where rn = 1) " +
            "WHERE " +
            "tdi.DEVICE_TYPE =#{deviceType}")
    public List<DeviceInfoVo> selectDeviceInfo(@Param("deviceType") Integer deviceType);

}
