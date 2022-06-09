package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExEtcPass;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EX_ETC_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExEtcPassMapper {
    /**
     * ETC出口通行信息表入库
     *
     * @param tblExEtcPass 接收ETC出口通行信息表信息
     */
    public int insertExEtcPass(TblExEtcPass tblExEtcPass);
}
