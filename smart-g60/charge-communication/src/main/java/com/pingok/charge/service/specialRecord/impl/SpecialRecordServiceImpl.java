package com.pingok.charge.service.specialRecord.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.specialRecord.TblSpecialRecord;
import com.pingok.charge.mapper.specialRecord.TblSpecialRecordMapper;
import com.pingok.charge.service.specialRecord.ISpecialRecordService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 软件信息 服务层处理
 *
 * @author qiumin
 */
@Slf4j
@Service
public class SpecialRecordServiceImpl implements ISpecialRecordService {

    @Value("${daas.host}")
    private String daasHost;

    @Autowired
    private TblSpecialRecordMapper tblSpecialRecordMapper;

    @Override
    public void handleSpecial(JSONObject jsonObject) {
        String post = HttpUtil.post("http://" + jsonObject.getString("ip") + ":" + jsonObject.getString("port") + "/api/Center/ReceiveHandleSpecial", jsonObject.toJSONString());
        if (!StringUtils.isEmpty(post)) {
            JSONObject ret = JSONObject.parseObject(post);
            if (ret.getInteger("TransCode") != 0) {
                log.error(jsonObject.toJSONString() + "特情处置请求失败：" + ret.getString("ErrorMsg"));
                throw new ServiceException("特情处置请求失败：" + ret.getString("ErrorMsg"));
            }
        } else {
            log.error(jsonObject.toJSONString() + "特情处置请求失败,状态未知");
            throw new ServiceException("特情处置请求失败,状态未知");
        }
    }

    @Override
    public void add(TblSpecialRecord tblSpecialRecord) {
        Example example = new Example(TblSpecialRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("recordId", tblSpecialRecord.getRecordId());
        TblSpecialRecord specialRecord = tblSpecialRecordMapper.selectOneByExample(example);
        if (specialRecord == null) {
            specialRecord = new TblSpecialRecord();
            BeanUtils.copyNotNullProperties(tblSpecialRecord, specialRecord);
            specialRecord.setCreateTime(DateUtils.getNowDate());
            tblSpecialRecordMapper.insert(tblSpecialRecord);
        } else {
            BeanUtils.copyNotNullProperties(tblSpecialRecord, specialRecord);
            specialRecord.setUpdateTime(DateUtils.getNowDate());
            tblSpecialRecordMapper.updateByPrimaryKey(specialRecord);
        }
    }

    @Async
    @Override
    public void update(TblSpecialRecord tblSpecialRecord) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(daasHost + "/data-center/specialRecord", JSON.toJSONString(tblSpecialRecord));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(tblSpecialRecord) + "特情上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(tblSpecialRecord) + "特情上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(tblSpecialRecord) + "特情上传失败：" + e.getMessage());
            }
            time += 2;
        }
        Example example = new Example(TblSpecialRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("recordId", tblSpecialRecord.getRecordId());
        tblSpecialRecordMapper.deleteByExample(example);
    }
}
