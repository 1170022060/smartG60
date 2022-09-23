package com.pingok.datacenter.mapper.trans;

import com.pingok.datacenter.domain.trans.TblExPaperPass;
import org.apache.ibatis.annotations.Mapper;

/**
 * TBL_EX_PAPER_PASS_年份 数据层
 *
 * @author xia
 */
@Mapper
public interface TblExPaperPassMapper {
    /**
     * 纸券出口通行信息表入库
     *
     * @param tblExPaperPass 接收纸券出口通行信息表信息
     */
    public int insertExPaperPass(TblExPaperPass tblExPaperPass);
}
