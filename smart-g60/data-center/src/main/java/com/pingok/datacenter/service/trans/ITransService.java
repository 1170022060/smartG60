package com.pingok.datacenter.service.trans;

import com.pingok.datacenter.domain.trans.*;
import com.pingok.datacenter.domain.trans.vo.EnTransEnum;
import com.pingok.datacenter.domain.trans.vo.ExInfoVo;
import com.pingok.datacenter.domain.trans.vo.ExTransEnum;

import java.util.Date;
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
    public Long insertEnTrans(TblEnTrans tblEnTrans);

    /**
     * ETC入口通行信息表入库
     *
     * @param tblEnEtcPass 接收ETC入口通行信息表信息
     * @param recordId 主表recordId
     */
    public int insertEnEtcPass(TblEnEtcPass tblEnEtcPass,Long recordId);

    /**
     * MTC入口通行信息表入库
     *
     * @param tblEnMtcPass 接收MTC入口通行信息表信息
     * @param recordId 主表recordId
     */
    public int insertEnMtcPass(TblEnMtcPass tblEnMtcPass,Long recordId);

    /**
     * 出口流水表入库
     *
     * @param tblExTrans 接收出口流水表信息
     */
    public ExInfoVo insertExTrans(TblExTrans tblExTrans);

    /**
     * ETC出口通行信息表入库
     *
     * @param tblExEtcPass 接收ETC出口通行信息表信息
     * @param recordId 主表recordId
     */
    public int insertExEtcPass(TblExEtcPass tblExEtcPass,Long recordId);

    /**
     * MTC出口通行信息表入库
     *
     * @param tblExMtcPass 接收MTC出口通行信息表信息
     * @param recordId 主表recordId
     */
    public int insertExMtcPass(TblExMtcPass tblExMtcPass,Long recordId);

    /**
     * 纸券出口通行信息表入库
     *
     * @param tblExPaperPass 接收纸券出口通行信息表信息
     * @param recordId 主表recordId
     */
    public int insertExPaperPass(TblExPaperPass tblExPaperPass,Long recordId);

    /**
     * 出口通行分省信息表入库
     *
     * @param exInfoVo 出口主表信息
     * @param tblExTransSplit 接收出口通行分省信息表信息
     */
    public int insertExTransSplit(ExInfoVo exInfoVo, List<TblExTransSplit> tblExTransSplit);

    public void insertSection(Date workDate,String stationId,Integer direction);

    public void updateSection(Date workDate,String stationId, Integer direction,Integer type);

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

    /**
     * 获取该车道编码的车道国标码
     *
     * @param laneHex
     */
    String selectLaneGB(String laneHex);
}
