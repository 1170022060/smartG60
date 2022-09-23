package com.pingok.charge.service.sectorlog.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.config.CenterConfig;
import com.pingok.charge.config.DaasConfig;
import com.pingok.charge.domain.sectorlog.vo.SectorLogVo;
import com.pingok.charge.service.sectorlog.ISectorLogService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 车道日志 服务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class SectorLogServiceImpl implements ISectorLogService {


    @Override
    public SectorLogVo getSectorLog(String laneHex, String gid) {
        Map<String, Object> body = new HashMap<>();
        body.put("LaneHex", laneHex);
        body.put("Gid", gid);
        String r = HttpUtil.post(CenterConfig.HOST + "/api/Lane/QueryReadRecord", body.toString());
        SectorLogVo ret = null;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, SectorLogVo.class);
        }
        return ret;
    }


    @Override
    public void updateSectorLog(SectorLogVo sectorLogVo) {
        String r = HttpUtil.post(DaasConfig.HOST + "/data-center/sectorLog", JSON.toJSONString(sectorLogVo));
        R ret;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, R.class);
            if (ret.getCode() == R.FAIL) {
                log.error("车道日志上传失败，错误" + ret.getMsg());
            }
        } else {
            log.error("车道日志上传状态未知");
        }
    }
}
