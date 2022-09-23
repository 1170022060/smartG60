package com.pingok.datacenter.service.blackcard.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.blackcard.TblBlackCard;
import com.pingok.datacenter.domain.blackcard.TblBlackCardLog;
import com.pingok.datacenter.domain.blackcard.TblBlackCardStationUsed;
import com.pingok.datacenter.mapper.blackcard.TblBlackCardLogMapper;
import com.pingok.datacenter.mapper.blackcard.TblBlackCardMapper;
import com.pingok.datacenter.mapper.blackcard.TblBlackCardStationUsedMapper;
import com.pingok.datacenter.service.blackcard.IBlackCardService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 模拟清分 服务层处理
 *
 * @author ruoyi
 */
@Service
public class BlackCardServiceImpl implements IBlackCardService {

    @Autowired
    private TblBlackCardStationUsedMapper tblBlackCardStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblBlackCardMapper tblBlackCardMapper;

    @Autowired
    private TblBlackCardLogMapper tblBlackCardLogMapper;

    @Override
    public void blackcard(JSONObject obj) {
        Example example = new Example(TblBlackCardStationUsed.class);
        example.createCriteria().andEqualTo("stationHex", obj.getString("stationHex"));
        TblBlackCardStationUsed stationUsed = tblBlackCardStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblBlackCardStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setObuVersion(obj.getString("version"));
            tblBlackCardStationUsedMapper.insert(stationUsed);
        } else {
            if (Long.parseLong(stationUsed.getObuVersion()) < obj.getLong("version")) {
                stationUsed.setApplyTime(obj.getDate("applyTime"));
                stationUsed.setCreateTime(DateUtils.getNowDate());
                stationUsed.setObuVersion(obj.getString("version"));
                tblBlackCardStationUsedMapper.updateByPrimaryKey(stationUsed);
            }
        }

        JSONArray detail = obj.getJSONArray("detail");
        if (StringUtils.isNotNull(detail)) {
            TblBlackCard blackCard;
            TblBlackCardLog blackCardLog;
            JSONObject card;
            for (int i = 0; i < detail.size(); i++) {
                card = detail.getJSONObject(i);
                example = new Example(TblBlackCard.class);
                example.createCriteria().andEqualTo("mediaId", card.getString("cardId"));
                blackCard = tblBlackCardMapper.selectOneByExample(example);
                if (StringUtils.isNull(blackCard)) {
                    blackCard = new TblBlackCard();
                    blackCard.setId(remoteIdProducerService.nextId());
                    blackCard.setApplyTime(obj.getDate("applyTime"));
                    blackCard.setCreationTime(card.getDate("creationTime"));
                    blackCard.setStatus(card.getInteger("status"));
                    blackCard.setInsertTime(card.getDate("insertTime"));
                    blackCard.setIssuerId(card.getString("issuerId"));
                    blackCard.setMediaId(card.getString("cardId"));
                    blackCard.setType(card.getInteger("type"));
                    blackCard.setVersion(obj.getString("version"));
                    blackCard.setUpdateTime(DateUtils.getNowDate());
                    tblBlackCardMapper.insert(blackCard);

                    blackCardLog = new TblBlackCardLog();
                    BeanUtils.copyNotNullProperties(blackCard, blackCardLog);
                    blackCardLog.setId(remoteIdProducerService.nextId());
                    tblBlackCardLogMapper.insert(blackCardLog);
                } else {
                    if (Long.parseLong(blackCard.getVersion()) < obj.getLong("version")) {
                        blackCard.setApplyTime(obj.getDate("applyTime"));
                        blackCard.setCreationTime(card.getDate("creationTime"));
                        blackCard.setStatus(card.getInteger("status"));
                        blackCard.setInsertTime(card.getDate("insertTime"));
                        blackCard.setIssuerId(card.getString("issuerId"));
                        blackCard.setMediaId(card.getString("cardId"));
                        blackCard.setType(card.getInteger("type"));
                        blackCard.setVersion(obj.getString("version"));
                        blackCard.setUpdateTime(DateUtils.getNowDate());
                        tblBlackCardMapper.updateByPrimaryKey(blackCard);

                        blackCardLog = new TblBlackCardLog();
                        BeanUtils.copyNotNullProperties(blackCard, blackCardLog);
                        blackCardLog.setId(remoteIdProducerService.nextId());
                        tblBlackCardLogMapper.insert(blackCardLog);
                    }
                }
            }
        }
    }
}
