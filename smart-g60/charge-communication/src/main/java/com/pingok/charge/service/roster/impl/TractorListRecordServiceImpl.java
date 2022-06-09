package com.pingok.charge.service.roster.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.domain.roster.vo.TblTractorListRecord;
import com.pingok.charge.service.roster.ITractorRecordService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 牵引车信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TractorListRecordServiceImpl implements ITractorRecordService {

    @Value("${daas.host}")
    private String host;

    @Override
    public List<TblTractorListRecord> selectByTime(String startTime, String endTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String r = HttpUtil.get(host, param);
        List<TblTractorListRecord> list = null;
        R<List<TblTractorListRecord>> ret;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, R.class);
            list = ret.getData();
        }
        return list;
    }
}
