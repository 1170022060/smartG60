package com.pingok.vocational.service.blackCard.impl;

import com.pingok.vocational.mapper.blackCard.TblBlackCardLogMapper;
import com.pingok.vocational.mapper.blackCard.TblBlackCardMapper;
import com.pingok.vocational.mapper.blackCard.TblBlackCardStationUsedMapper;
import com.pingok.vocational.service.blackCard.IBlackCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BlackCardServiceImpl implements IBlackCardService {

    @Autowired
    private TblBlackCardMapper tblBlackCardMapper;
    @Autowired
    private TblBlackCardLogMapper tblBlackCardLogMapper;
    @Autowired
    private TblBlackCardStationUsedMapper tblBlackCardStationUsedMapper;

    @Override
    public List<Map> getNowList(String mediaId, Integer mediaType, Date startDate, Date endDate) {
        return tblBlackCardMapper.selectList(mediaId, mediaType, startDate, endDate);
    }

    @Override
    public List<Map> getStationUsedList() {
        return tblBlackCardStationUsedMapper.selectList();
    }

    @Override
    public List<Map> getRecordList(String mediaId, Integer mediaType) {
        return tblBlackCardLogMapper.selectByMedia(mediaId, mediaType);
    }
}
