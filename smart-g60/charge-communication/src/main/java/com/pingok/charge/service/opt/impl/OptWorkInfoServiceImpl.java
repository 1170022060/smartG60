package com.pingok.charge.service.opt.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.service.opt.IOptWorkInfoService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OptWorkInfoServiceImpl implements IOptWorkInfoService {

    @Value("${daas.host}")
    private String daasHost;

    @Override
    public void optWorkInfo(JSONObject jsonObject) {
        String get = HttpUtil.get(daasHost + "/vocational/optWork/issue");
        if (!StringUtils.isEmpty(get)) {
            JSONObject ret = JSONObject.parseObject(get);
            if (ret.getInteger("TransCode") != 0) {
                log.error(jsonObject.toJSONString() + "工班信息下发请求失败：" + ret.getString("ErrorMsg"));
                throw new ServiceException("工班信息下发请求失败：" + ret.getString("ErrorMsg"));
            }
        } else {
            log.error(jsonObject.toJSONString() + "工班信息下发请求失败,状态未知");
            throw new ServiceException("工班信息下发请求失败,状态未知");
        }
    }
}
