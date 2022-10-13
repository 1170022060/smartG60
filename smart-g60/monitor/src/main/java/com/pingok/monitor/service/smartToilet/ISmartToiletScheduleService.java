package com.pingok.monitor.service.smartToilet;

import com.pingok.monitor.domain.smartToilet.TblSmartToiletSchedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISmartToiletScheduleService {
    /**
     * 分页查询厕所排班记录
     */
    List<Map> findToiletScheduleList(Long fieldId,Long toiletId, Date workDate);

    /**
     * 新增厕所排班
     * @param tblSmartToiletSchedule
     * @return
     */
    int insert(TblSmartToiletSchedule tblSmartToiletSchedule);

    /**
     * 编辑厕所排班
     * @param tblSmartToiletSchedule
     * @return
     */
    int update(TblSmartToiletSchedule tblSmartToiletSchedule);

    /**
     * 获取厕所类型下拉列表
     * @return
     */
    List<Map> getToiletType();

    /**
     * 根据id查询排班信息
     * @param id
     * @return
     */
    TblSmartToiletSchedule findById(Long id);
}
