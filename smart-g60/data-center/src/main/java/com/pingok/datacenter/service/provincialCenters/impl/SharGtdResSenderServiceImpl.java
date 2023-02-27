package com.pingok.datacenter.service.provincialCenters.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.domain.provincialCenters.TblSharGtdResSender;
import com.pingok.datacenter.mapper.provincialCenters.TblSharGtdResSenderMapper;
import com.pingok.datacenter.service.provincialCenters.ISharGtdResSenderService;
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
@Slf4j
@Service
public class SharGtdResSenderServiceImpl implements ISharGtdResSenderService {

    @Autowired
    private TblSharGtdResSenderMapper tblSharGtdResSenderMapper;

    @Override
    public void add(JSONArray jsonArray) {
        String year = DateUtils.dateYear();
        List<TblSharGtdResSender> list = JSON.parseArray(jsonArray.toJSONString(), TblSharGtdResSender.class);

        if (list != null && list.size() > 0) {
            for (TblSharGtdResSender gtd : list) {
                try {
                    gtd.setYear(year);
                    tblSharGtdResSenderMapper.add(gtd);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
