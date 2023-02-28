package com.pingok.datacenter.service.provincialCenters.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.domain.provincialCenters.TblSharSvidResSender;
import com.pingok.datacenter.mapper.provincialCenters.TblSharSvidResSenderMapper;
import com.pingok.datacenter.service.provincialCenters.ISharSvidResSenderService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层处理
 *
 * @author qiumin
 */
@Service
@Slf4j
public class SharSvidResSenderServiceImpl implements ISharSvidResSenderService {

    @Autowired
    private TblSharSvidResSenderMapper tblSharSvidResSenderMapper;

    @Override
    public void add(JSONArray jsonArray) {
        String year = DateUtils.dateYear();
        List<TblSharSvidResSender> list = JSON.parseArray(jsonArray.toJSONString(), TblSharSvidResSender.class);
        if (list != null && list.size() > 0) {
            TblSharSvidResSender t;
            for (TblSharSvidResSender gtd : list) {
                try {
                    gtd.setYear(year);
                    t = tblSharSvidResSenderMapper.findById(gtd);
                    if (t == null) {
                        tblSharSvidResSenderMapper.add(gtd);
                    } else {
                        tblSharSvidResSenderMapper.update(gtd);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
