package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnEtcPass;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EN_ETC_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblEnEtcPassMapper {
    /**
     * 入口ETC通行信息表入库
     *
     * @param tblEnEtcPass 接收入口ETC通行信息表信息
     */
    public int insertEnEtcPass(TblEnEtcPass tblEnEtcPass);
}
