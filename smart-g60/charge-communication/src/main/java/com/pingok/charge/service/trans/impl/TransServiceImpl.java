package com.pingok.charge.service.trans.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.domain.trans.*;
import com.pingok.charge.domain.trans.vo.EnTransEnum;
import com.pingok.charge.domain.trans.vo.ExTransEnum;
import com.pingok.charge.mapper.trans.*;
import com.pingok.charge.service.trans.ITransService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 车道流水 服务层处理
 *
 * @author xia
 */
@Slf4j
@Service
public class TransServiceImpl implements ITransService {

    @Value("${daas.host}")
    private String daasHost;

    @Autowired
    private TblEnTransMapper tblEnTransMapper;

    @Autowired
    private TblEnMtcPassMapper tblEnMtcPassMapper;

    @Autowired
    private TblEnEtcPassMapper tblEnEtcPassMapper;

    @Autowired
    private TblExTransMapper tblExTransMapper;

    @Autowired
    private TblExMtcPassMapper tblExMtcPassMapper;

    @Autowired
    private TblExEtcPassMapper tblExEtcPassMapper;

    @Autowired
    private TblExPaperPassMapper tblExPaperPassMapper;

    @Autowired
    private TblExTransSplitMapper tblExTransSplitMapper;

    @Override
    public void addEn(EnTransEnum enTransEnum) {
        Example example = new Example(TblEnTrans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gid", enTransEnum.getTblEnTrans().getGid());
        TblEnTrans tblEnTrans = tblEnTransMapper.selectOneByExample(example);
        if (tblEnTrans == null) {
            tblEnTrans = new TblEnTrans();
            BeanUtils.copyNotNullProperties(enTransEnum.getTblEnTrans(), tblEnTrans);
            tblEnTransMapper.insert(tblEnTrans);
            tblEnTrans = tblEnTransMapper.selectOneByExample(example);
            if(enTransEnum.getTblEnEtcPass()!=null)
            {
                TblEnEtcPass tblEnEtcPass = new TblEnEtcPass();
                BeanUtils.copyNotNullProperties(enTransEnum.getTblEnEtcPass(), tblEnEtcPass);
                tblEnEtcPass.setRecordId(tblEnTrans.getRecordId());
                tblEnEtcPassMapper.insert(tblEnEtcPass);
            }
            if(enTransEnum.getTblEnMtcPass()!=null)
            {
                TblEnMtcPass tblEnMtcPass = new TblEnMtcPass();
                BeanUtils.copyNotNullProperties(enTransEnum.getTblEnMtcPass(), tblEnMtcPass);
                tblEnMtcPass.setRecordId(tblEnTrans.getRecordId());
                tblEnMtcPassMapper.insert(tblEnMtcPass);
            }
        }
    }

    @Async
    @Override
    public void updateEn(EnTransEnum enTransEnum) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(daasHost + "/data-center/trans/en", JSON.toJSONString(enTransEnum));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(enTransEnum) + "入口流水上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(enTransEnum) + "入口流水上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(enTransEnum) + "入口流水上传失败：" + e.getMessage());
            }
            time += 2;
        }
        Example example = new Example(TblEnTrans.class);
        example.createCriteria().andEqualTo("gid", enTransEnum.getTblEnTrans().getGid());
        tblEnMtcPassMapper.deleteByExample(example);

        example = new Example(TblEnEtcPass.class);
        example.createCriteria().andEqualTo("recordId", enTransEnum.getTblEnTrans().getRecordId());
        tblEnMtcPassMapper.deleteByExample(example);

        example = new Example(TblEnMtcPass.class);
        example.createCriteria().andEqualTo("recordId", enTransEnum.getTblEnTrans().getRecordId());
        tblEnMtcPassMapper.deleteByExample(example);
    }

    @Override
    public void addEx(ExTransEnum exTransEnum) {
        Example example = new Example(TblExTrans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gid", exTransEnum.getTblExTrans().getGid());
        TblExTrans tblExTrans = tblExTransMapper.selectOneByExample(example);
        if (tblExTrans == null) {
            tblExTrans = new TblExTrans();
            BeanUtils.copyNotNullProperties(exTransEnum.getTblExTrans(), tblExTrans);
            tblExTransMapper.insert(tblExTrans);
            tblExTrans = tblExTransMapper.selectOneByExample(example);
            if(exTransEnum.getTblExEtcPass()!=null)
            {
                TblExEtcPass tblExEtcPass = new TblExEtcPass();
                BeanUtils.copyNotNullProperties(exTransEnum.getTblExEtcPass(), tblExEtcPass);
                tblExEtcPass.setRecordId(tblExTrans.getRecordId());
                tblExEtcPassMapper.insert(tblExEtcPass);
            }
            if(exTransEnum.getTblExMtcPass()!=null)
            {
                TblExMtcPass tblExMtcPass = new TblExMtcPass();
                BeanUtils.copyNotNullProperties(exTransEnum.getTblExMtcPass(), tblExMtcPass);
                tblExMtcPass.setRecordId(tblExTrans.getRecordId());
                tblExMtcPassMapper.insert(tblExMtcPass);
            }
            if(exTransEnum.getTblExPaperPass()!=null)
            {
                TblExPaperPass tblExPaperPass = new TblExPaperPass();
                BeanUtils.copyNotNullProperties(exTransEnum.getTblExPaperPass(), tblExPaperPass);
                tblExPaperPass.setRecordId(tblExTrans.getRecordId());
                tblExPaperPassMapper.insert(tblExPaperPass);
            }
            if(exTransEnum.getTblExTransSplit()!=null)
            {
                for(TblExTransSplit list :exTransEnum.getTblExTransSplit())
                {
                    TblExTransSplit tblExTransSplit = new TblExTransSplit();
                    BeanUtils.copyNotNullProperties(list, tblExTransSplit);
                    tblExTransSplit.setRecordId(tblExTrans.getRecordId());
                    tblExTransSplitMapper.insert(tblExTransSplit);
                }
            }
        }
    }

    @Override
    public void updateEx(ExTransEnum exTransEnum) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(daasHost + "/data-center/trans/ex", JSON.toJSONString(exTransEnum));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(exTransEnum) + "出口流水上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(exTransEnum) + "出口流水上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(exTransEnum) + "出口流水上传失败：" + e.getMessage());
            }
            time += 2;
        }
        Example example = new Example(TblExTrans.class);
        example.createCriteria().andEqualTo("gid", exTransEnum.getTblExTrans().getGid());
        tblExMtcPassMapper.deleteByExample(example);

        example = new Example(TblExEtcPass.class);
        example.createCriteria().andEqualTo("recordId", exTransEnum.getTblExTrans().getRecordId());
        tblExMtcPassMapper.deleteByExample(example);

        example = new Example(TblExMtcPass.class);
        example.createCriteria().andEqualTo("recordId", exTransEnum.getTblExTrans().getRecordId());
        tblExMtcPassMapper.deleteByExample(example);

        example = new Example(TblExPaperPass.class);
        example.createCriteria().andEqualTo("recordId", exTransEnum.getTblExTrans().getRecordId());
        tblExPaperPassMapper.deleteByExample(example);

        example = new Example(TblExTransSplit.class);
        example.createCriteria().andEqualTo("recordId", exTransEnum.getTblExTrans().getRecordId());
        tblExTransSplitMapper.deleteByExample(example);
    }
}
