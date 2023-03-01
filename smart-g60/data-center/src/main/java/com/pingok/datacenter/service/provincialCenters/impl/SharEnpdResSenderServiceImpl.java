package com.pingok.datacenter.service.provincialCenters.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.domain.provincialCenters.TblSharEnpdResSender;
import com.pingok.datacenter.domain.provincialCenters.TblSharEtctdResSender;
import com.pingok.datacenter.domain.provincialCenters.TblSharGtdResSender;
import com.pingok.datacenter.domain.provincialCenters.vo.ProvincialCentersVo;
import com.pingok.datacenter.mapper.provincialCenters.TblSharEtctdResSenderMapper;
import com.pingok.datacenter.service.provincialCenters.ISharEtctdResSenderService;
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
public class SharEnpdResSenderServiceImpl implements ISharEtctdResSenderService {

    @Autowired
    private TblSharEtctdResSenderMapper tblSharEtctdResSenderMapper;

    @Override
    public void add(JSONArray jsonArray) {
        String year = DateUtils.dateYear();
        List<TblSharEtctdResSender> list = JSON.parseArray(jsonArray.toJSONString(), TblSharEtctdResSender.class);
        if (list != null && list.size() > 0) {
            TblSharEtctdResSender t;
            for (TblSharEtctdResSender gtd : list) {
                try {
                    gtd.setYear(year);
                    t = tblSharEtctdResSenderMapper.findById(gtd);
                    if(t==null){
                        tblSharEtctdResSenderMapper.add(gtd);
                    }else {
                        tblSharEtctdResSenderMapper.update(gtd);
                    }
                } catch (Exception e) {
                    log.error(JSON.toJSONString(gtd));
                    log.error(e.getMessage());
                }
            }
        }
    }
}
