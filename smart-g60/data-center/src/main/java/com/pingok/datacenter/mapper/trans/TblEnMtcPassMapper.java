package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnMtcPass;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EN_MTC_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblEnMtcPassMapper {
    /**
     * 入口MTC通行信息表入库
     *
     * @param tblEnMtcPass 接收入口MTC通行信息表信息
     */
    public int insertEnMtcPass(TblEnMtcPass tblEnMtcPass);
}
