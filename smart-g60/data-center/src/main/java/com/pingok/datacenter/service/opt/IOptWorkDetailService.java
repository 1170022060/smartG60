package com.pingok.datacenter.service.opt;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.opt.vo.ModelWorkTableVo;

import java.util.Date;
import java.util.List;

/**
 * 排班当岗 业务层
 *
 * @author ruoyi
 */
public interface IOptWorkDetailService {

    /**
     * 抢险救灾名单更新
     * @param obj
     */
    void optWorkDetail(JSONObject obj);

    List<ModelWorkTableVo> makeWorkTable(Date workDate);
}
