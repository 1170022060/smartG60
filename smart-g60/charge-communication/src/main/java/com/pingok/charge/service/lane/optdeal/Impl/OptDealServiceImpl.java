package com.pingok.charge.service.lane.optdeal.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.domain.lane.optdeal.TblOptInfo;
import com.pingok.charge.service.lane.optdeal.IOptDealService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @time 2022/5/30 18:10
 */
@Slf4j
@Service
public class OptDealServiceImpl implements IOptDealService {

    @Value("${daas.host}")
    private String host;

    @Override
    public List<TblOptInfo> query(String startTime, String endTime, String belongStation) {
        Map<String, Object> param = new HashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("belongStation", belongStation);
        String r = HttpUtil.get(host + "/optdeal/queryBy", param);
        List<TblOptInfo> list = null;
        R<List<TblOptInfo>> ret;
        if(!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, R.class);
            list = ret.getData();
        }
        return list;
    }
}
