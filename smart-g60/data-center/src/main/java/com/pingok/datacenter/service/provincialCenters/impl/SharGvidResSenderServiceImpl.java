package com.pingok.datacenter.service.provincialCenters.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.domain.provincialCenters.TblSharGtdResSender;
import com.pingok.datacenter.domain.provincialCenters.TblSharGvidResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import com.pingok.datacenter.mapper.provincialCenters.TblSharGtdResSenderMapper;
import com.pingok.datacenter.mapper.provincialCenters.TblSharGvidResSenderMapper;
import com.pingok.datacenter.service.provincialCenters.ISharGtdResSenderService;
import com.pingok.datacenter.service.provincialCenters.ISharGvidResSenderService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务层处理
 *
 * @author qiumin
 */
@Service
@Slf4j
public class SharGvidResSenderServiceImpl implements ISharGvidResSenderService {

    @Autowired
    private TblSharGvidResSenderMapper tblSharGvidResSenderMapper;

    @Override
    public void add(JSONArray jsonArray) {
        String year = DateUtils.dateYear();
        List<TblSharGvidResSender> list = JSON.parseArray(jsonArray.toJSONString(), TblSharGvidResSender.class);
        if (list != null && list.size() > 0) {
            TblSharGvidResSender t;
            for (TblSharGvidResSender gtd : list) {
                try {
                    gtd.setYear(year);
                    t = tblSharGvidResSenderMapper.findById(gtd);
                    if(t==null){
                        tblSharGvidResSenderMapper.add(gtd);
                    }else {
                        tblSharGvidResSenderMapper.update(gtd);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
