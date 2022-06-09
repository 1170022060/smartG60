package com.pingok.station.mapper.greenList;

import com.pingok.station.domain.greenList.GreenPassAppointment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GreenPassAppointmentMapper {
    /**
     * 绿通预约名单入库
     *
     * @param greenPassAppointment
     * @return 结果
     */
    public int insertGreenPass(GreenPassAppointment greenPassAppointment);

    /**
     * 清除绿通预约名单
     *
     * @return 结果
     */
    public void deleteAll();
}
