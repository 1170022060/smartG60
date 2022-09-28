package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;
import com.pingok.vocational.mapper.parkingLot.TblOilPriceMapper;
import com.pingok.vocational.mapper.parkingLot.TblPersonnelHealthMapper;
import com.pingok.vocational.service.parkingLot.IOilPriceService;
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

    @Override
    public TblOilPrice selectOilPrice(Date date) {
        Example example = new Example(TblOilPrice.class);
        example.createCriteria().andEqualTo("transDate", date);
        return tblOilPriceMapper.selectOneByExample(example);
    }
}
