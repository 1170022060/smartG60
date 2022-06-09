package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblEnTransPass;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EN_TRANS_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblEnTransPassMapper {
    /**
     * 入口通行信息表入库
     *
     * @param tblEnTransPass 接收入口通行信息表信息
     */
    public int insertEnTransPass(TblEnTransPass tblEnTransPass);
}
