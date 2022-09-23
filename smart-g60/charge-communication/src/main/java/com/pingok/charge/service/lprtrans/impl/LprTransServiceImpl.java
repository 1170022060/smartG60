package com.pingok.charge.service.lprtrans.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.domain.lprtrans.TblEnLprTrans;
import com.pingok.charge.domain.lprtrans.TblExLprTrans;
import com.pingok.charge.mapper.lprtrans.TblEnLprTransMapper;
import com.pingok.charge.mapper.lprtrans.TblExLprTransMapper;
import com.pingok.charge.service.lprtrans.ILprTransService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 牌识 服务层处理
 *
 * @author xia
 */
@Slf4j
@Service
public class LprTransServiceImpl implements ILprTransService {

    @Value("${daas.host}")
    private String daasHost;

    @Autowired
    private TblEnLprTransMapper tblEnLprTransMapper;

    @Autowired
    private TblExLprTransMapper tblExLprTransMapper;

    @Override
    public void addEn(TblEnLprTrans tblEnLprTrans) {
        Example example = new Example(TblEnLprTrans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("transId", tblEnLprTrans.getTransId());
        TblEnLprTrans enLprTrans = tblEnLprTransMapper.selectOneByExample(example);
        if (enLprTrans == null) {
            enLprTrans = new TblEnLprTrans();
            BeanUtils.copyNotNullProperties(tblEnLprTrans, enLprTrans);
            tblEnLprTransMapper.insert(tblEnLprTrans);
        } else {
            BeanUtils.copyNotNullProperties(tblEnLprTrans, enLprTrans);
            tblEnLprTransMapper.updateByPrimaryKey(enLprTrans);
        }
    }

    @Override
    public void updateEn(TblEnLprTrans tblEnLprTrans) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(daasHost + "/data-center/lprTrans/en", JSON.toJSONString(tblEnLprTrans));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(tblEnLprTrans) + "入口牌识上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(tblEnLprTrans) + "入口牌识上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(tblEnLprTrans) + "入口牌识上传失败：" + e.getMessage());
            }
            time += 2;
        }
        Example example = new Example(TblEnLprTrans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("transId", tblEnLprTrans.getTransId());
        tblEnLprTransMapper.deleteByExample(example);
    }

    @Override
    public void addEx(TblExLprTrans tblExLprTrans) {
        Example example = new Example(TblExLprTrans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("transId", tblExLprTrans.getTransId());
        TblExLprTrans exLprTrans = tblExLprTransMapper.selectOneByExample(example);
        if (exLprTrans == null) {
            exLprTrans = new TblExLprTrans();
            BeanUtils.copyNotNullProperties(tblExLprTrans, exLprTrans);
            tblExLprTransMapper.insert(tblExLprTrans);
        } else {
            BeanUtils.copyNotNullProperties(tblExLprTrans, exLprTrans);
            tblExLprTransMapper.updateByPrimaryKey(exLprTrans);
        }
    }

    @Override
    public void updateEx(TblExLprTrans tblExLprTrans) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(daasHost + "/data-center/lprTrans/ex", JSON.toJSONString(tblExLprTrans));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(tblExLprTrans) + "出口牌识上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(tblExLprTrans) + "出口牌识上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(tblExLprTrans) + "出口牌识上传失败：" + e.getMessage());
            }
            time += 2;
        }
        Example example = new Example(TblExLprTrans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("transId", tblExLprTrans.getTransId());
        tblExLprTransMapper.deleteByExample(example);
    }
}
