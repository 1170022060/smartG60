package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.domain.device.TblDeviceInfo;
import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.domain.parkingLot.TblOilPrice;
import com.pingok.vocational.mapper.parkingLot.TblOilPriceMapper;
import com.pingok.vocational.service.parkingLot.IOilPriceService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 今日油价 服务层实现层
 *
 * @author xia
 */
@Service
public class OilPriceServiceImpl implements IOilPriceService {

    @Autowired
    private TblOilPriceMapper tblOilPriceMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblOilPrice selectOilPrice(Date date) {
        Example example = new Example(TblOilPrice.class);
        example.createCriteria().andEqualTo("transDate", date);
        return tblOilPriceMapper.selectOneByExample(example);
    }

    @Override
    public TblOilPrice selectOilPriceById(Long Id) {
        return tblOilPriceMapper.selectByPrimaryKey(Id);
    }

    @Override
    public int insertOilPrice(TblOilPrice tblOilPrice) {
        tblOilPrice.setId(remoteIdProducerService.nextId());
        tblOilPrice.setCreateTime(new Date());
        tblOilPrice.setCreateUserId(SecurityUtils.getUserId());
        return tblOilPriceMapper.insert(tblOilPrice);
    }

    @Override
    public int updateOilPrice(TblOilPrice tblOilPrice) {
        tblOilPrice.setUpdateTime(new Date());
        tblOilPrice.setUpdateUserId(SecurityUtils.getUserId());
        return tblOilPriceMapper.updateByPrimaryKey(tblOilPrice);
    }

    @Override
    public String checkDateUnique(TblOilPrice tblOilPrice) {
        Long id = StringUtils.isNull(tblOilPrice.getId()) ? -1L : tblOilPrice.getId();
        TblOilPrice info = tblOilPriceMapper.checkDateUnique(tblOilPrice.getTransDate());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
