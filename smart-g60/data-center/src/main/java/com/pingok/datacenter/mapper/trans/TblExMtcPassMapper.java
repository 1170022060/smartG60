package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExMtcPass;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EX_MTC_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExMtcPassMapper {
    /**
     * MTC出口通行信息表入库
     *
     * @param tblExMtcPass 接收MTC出口通行信息表信息
     */
    public int insertExMtcPass(TblExMtcPass tblExMtcPass);
}
