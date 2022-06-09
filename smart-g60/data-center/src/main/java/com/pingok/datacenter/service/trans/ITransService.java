package com.pingok.datacenter.service.trans;

import com.pingok.datacenter.domain.trans.*;
import com.pingok.datacenter.domain.trans.vo.EnTransEnum;
import com.pingok.datacenter.domain.trans.vo.ExTransEnum;
import com.pingok.datacenter.mapper.trans.TblExTransSplitMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出入口流水数据入库 业务层
 *
 * @author ruoyi
 */
public interface ITransService {

    /**
     * 入口流水表入库
     *
     * @param tblEnTrans 接收入口流水表信息
     */
    public int insertEnTrans(TblEnTrans tblEnTrans);

    /**
     * 入口通行信息表入库
     *
     * @param tblEnTransPass 接收入口通行信息表信息
     */
    public int insertEnTransPass(TblEnTransPass tblEnTransPass);

    /**
     * 出口流水表入库
     *
     * @param tblExTrans 接收出口流水表信息
     */
    public String insertExTrans(TblExTrans tblExTrans);

    /**
     * 出口通行信息表入库
     *
     * @param tblExTransPass 接收出口通行信息表信息
     */
    public int insertExTransPass(TblExTransPass tblExTransPass);

    /**
     * 出口通行分省信息表入库
     *
     * @param year 出口主表年份
     * @param tblExTransSplit 接收出口通行分省信息表信息
     */
    public int insertExTransSplit(String year, List<TblExTransSplit> tblExTransSplit);


    /**
     * 入口流水表入汇总表
     *
     * @param enTransEnum 接收入口流水表信息
     */
    public int insertEnTransSummary(EnTransEnum enTransEnum);

    /**
     * 出口流水表入汇总表
     *
     * @param exTransEnum 接收出口流水表信息
     */
    public int insertExTransSummary(ExTransEnum exTransEnum);

    /**
     * 获取该PassID的主键
     *
     * @param passId
     */
    Long selectSamePassId(String passId);
}
