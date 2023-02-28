package com.pingok.datacenter.service.provincialCenters.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.domain.provincialCenters.TblSharGvidResSender;
import com.pingok.datacenter.domain.provincialCenters.TblSharOtdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import com.pingok.datacenter.mapper.provincialCenters.TblSharGvidResSenderMapper;
import com.pingok.datacenter.mapper.provincialCenters.TblSharOtdResSenderMapper;
import com.pingok.datacenter.service.provincialCenters.ISharGvidResSenderService;
import com.pingok.datacenter.service.provincialCenters.ISharOtdResSenderService;
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
public class SharOtdResSenderServiceImpl implements ISharOtdResSenderService {

    @Autowired
    private TblSharOtdResSenderMapper tblSharOtdResSenderMapper;

    @Override
    public void add(JSONArray jsonArray) {
        String year = DateUtils.dateYear();
        List<TblSharOtdResSender> list = JSON.parseArray(jsonArray.toJSONString(), TblSharOtdResSender.class);
        if (list != null && list.size() > 0) {
            TblSharOtdResSender t;
            for (TblSharOtdResSender gtd : list) {
                try {
                    gtd.setYear(year);
                    t = tblSharOtdResSenderMapper.findById(gtd);
                    if(t==null){
                        tblSharOtdResSenderMapper.add(gtd);
                    }else {
                        tblSharOtdResSenderMapper.update(gtd);
                    }
                } catch (Exception e) {
                    log.error(JSON.toJSONString(gtd));
                    log.error(e.getMessage());
                }
            }
        }
    }
}
