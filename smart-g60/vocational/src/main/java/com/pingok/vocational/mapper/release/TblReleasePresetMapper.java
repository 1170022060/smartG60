package com.pingok.vocational.mapper.release;

import com.pingok.vocational.domain.release.TblReleasePreset;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_RELEASE_PRESET 数据层
 *
 * @author xia
 */
public interface TblReleasePresetMapper extends CommonRepository<TblReleasePreset> {

    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "b.DICT_LABEL as \"infoType\" , " +
            "a.PRESET_NAME as \"presetName\" , " +
            "a.PRESET_INFO as \"presetInfo\" , " +
            "c.DICT_LABEL as \"typeface\" , " +
            "d.DICT_LABEL as \"typefaceSize\" , " +
            "e.DICT_LABEL as \"color\" , " +
            "f.DICT_LABEL as \"pictureType\", " +
            "a.STATUS  as \"status\" " +
            "from TBL_RELEASE_PRESET a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.INFO_TYPE) and b.DICT_TYPE='release_info_type' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.TYPEFACE) and c.DICT_TYPE='release_typeface' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=to_char(a.TYPEFACE_SIZE) and d.DICT_TYPE='release_size' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.COLOR) and e.DICT_TYPE='release_color' " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=to_char(a.PICTURE_TYPE) and f.DICT_TYPE='release_picture_type' " +
            "where 1=1 " +
            "<when test='infoType != null'> " +
            "and a.INFO_TYPE= #{infoType} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "<when test='presetName != null'> " +
            "and a.PRESET_NAME like CONCAT(CONCAT('%',#{presetName}),'%') " +
            "</when>"+
            "order by a.INFO_TYPE, a.CREATE_TIME" +
            "</script>"})
    List<Map> selectReleasePreset(@Param("infoType") Integer infoType, @Param("status") Integer status ,@Param("presetName") String presetName);

    @Select("select " +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME like CONCAT(CONCAT('%','情报板'),'%') " +
            "union all  " +
            "select " +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO_LANE a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME like CONCAT(CONCAT('%','情报板'),'%') " +
            "union all " +
            "select " +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO_GANTRY a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME like CONCAT(CONCAT('%','情报板'),'%') " )
    List<Map> selectDeviceInfo();

    @Select("select " +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME like CONCAT(CONCAT('%','限速板'),'%') " +
            "union all  " +
            "select " +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO_LANE a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME like CONCAT(CONCAT('%','限速板'),'%') " +
            "union all " +
            "select " +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO_GANTRY a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME like CONCAT(CONCAT('%','限速板'),'%') " )
    List<Map> selectDeviceSpeed();

    @Select({"<script>" +
            "select " +
            "a.ID as \"id\" ," +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME || '_' ||PILE_NO as \"device\" " +
            "from TBL_DEVICE_INFO a " +
            "left join TBL_DEVICE_CATEGORY b on a.DEVICE_CATEGORY=b.ID " +
            "where b.CATEGORY_NAME " +
            "<when test='type == 1'> " +
            "like CONCAT(CONCAT('%','情报板'),'%') " +
            "</when>"+
            "<when test='type == 2'> " +
            "like CONCAT(CONCAT('%','限速板'),'%') " +
            "</when>"+
            "<when test='type != 1 and type!=2 '> " +
            "is null " +
            "</when>"+
            "</script>"})
    List<Map> selectDeviceBoard(@Param("type") Integer type);

    @Select("select * from TBL_RELEASE_PRESET where PRESET_INFO= #{presetInfo} and rownum = 1")
    TblReleasePreset checkPresetInfoUnique(@Param("presetInfo") String presetInfo);

    @Select("select ID as \"id\" ," +
            "INFO_TYPE as \"infoType\" , " +
            "PRESET_INFO as \"presetInfo\" , " +
            "TYPEFACE as \"typeface\" , " +
            "TYPEFACE_SIZE as \"typefaceSize\" , " +
            "COLOR as \"color\" , " +
            "PICTURE_TYPE as \"pictureType\" " +
            "from TBL_RELEASE_PRESET where INFO_TYPE= #{infoType} and STATUS=1 ")
    List<Map> selectReleaseInfo(@Param("infoType") Integer infoType);
}
