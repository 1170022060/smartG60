package com.pingok.datacenter.service.provincialCenters.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.domain.provincialCenters.TblSharEnpdResSender;
import com.pingok.datacenter.domain.provincialCenters.TblSharEtctdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import com.pingok.datacenter.mapper.provincialCenters.TblSharEnpdResSenderMapper;
import com.pingok.datacenter.service.provincialCenters.ISharEnpdResSenderService;
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
public class SharEtctdResSenderServiceImpl implements ISharEnpdResSenderService {

    @Autowired
    private TblSharEnpdResSenderMapper tblSharEnpdResSenderMapper;

    @Override
    public void add(JSONArray jsonArray) {
        String year = DateUtils.dateYear();
        List<TblSharEnpdResSender> list = JSON.parseArray(jsonArray.toJSONString(), TblSharEnpdResSender.class);
        if (list != null && list.size() > 0) {
            TblSharEnpdResSender t;
            for (TblSharEnpdResSender gtd : list) {
                try {
                    gtd.setYear(year);
                    t = tblSharEnpdResSenderMapper.findById(gtd);
                    if(t==null){
                        tblSharEnpdResSenderMapper.add(gtd);
                    }else {
                        tblSharEnpdResSenderMapper.update(gtd);
                    }
                } catch (Exception e) {
                    log.error(JSON.toJSONString(gtd));
                    log.error(e.getMessage());
                }
            }
        }
    }
}
