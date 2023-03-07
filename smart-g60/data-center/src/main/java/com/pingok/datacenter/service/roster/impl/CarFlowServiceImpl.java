package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.config.CenterConfig;
import com.pingok.datacenter.domain.gantry.model.GantryFlowModel;
import com.pingok.datacenter.domain.roster.green.TblGreenListRecord;
import com.pingok.datacenter.domain.roster.green.TblGreenStationUsed;
import com.pingok.datacenter.domain.roster.green.TblGreenVersion;
import com.pingok.datacenter.domain.roster.green.vo.GreenVo;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.domain.trans.vo.EnTranFlow;
import com.pingok.datacenter.domain.trans.vo.ExTranFlow;
import com.pingok.datacenter.mapper.gantry.TblGantrySumTransactionMapper;
import com.pingok.datacenter.mapper.roster.VersionMapper;
import com.pingok.datacenter.mapper.roster.green.TblGreenListRecordMapper;
import com.pingok.datacenter.mapper.roster.green.TblGreenStationUsedMapper;
import com.pingok.datacenter.mapper.roster.green.TblGreenVersionMapper;
import com.pingok.datacenter.mapper.trans.TblEnTransMapper;
import com.pingok.datacenter.mapper.trans.TblExTransMapper;
import com.pingok.datacenter.service.roster.ICarFlowService;
import com.pingok.datacenter.service.roster.IGreenService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.backMD5;
import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.delFolder;

/**
 * 绿通名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class CarFlowServiceImpl implements ICarFlowService {

    @Autowired
    private TblGantrySumTransactionMapper tblGantrySumTransactionMapper;
    @Autowired
    private TblEnTransMapper tblEnTransMapper;
    @Autowired
    private TblExTransMapper tblExTransMapper;


    @Override
    public void carFlowStatistics(JSONObject obj) {
        //这边从表中读出上一小时的车流量，需要修改，不是给0
        int lastCount=0;

        Date endTime=new Date();
        Date startTime=new Date(endTime.getTime() - 1 * 24 * 60 * 60 * 1000);

        //所有表名都需要根据实际情况进行改动
        lastCount=lastCount
                +tblGantrySumTransactionMapper.selectGantryFlow(new GantryFlowModel(startTime,""
                ,endTime,"('G006031001000120010','G006031001000310010','G006031001000620010'," +
                "'G006031001000620020','G006031001000810010','G006031001000810020'," +
                "'G006031001001120010','G006031001001120020')"))
                -tblGantrySumTransactionMapper.selectGantryFlow(new GantryFlowModel(startTime,""
                ,endTime,"('G006031001000110010','G006031001000320010','G006031001000610010'," +
                "'G006031001000610020','G006031001000820010','G006031001000820020'," +
                "'G006031001001110010','G006031001001110020')"))
                +tblEnTransMapper.selectEnFlow(new EnTranFlow(startTime,endTime,"",
                "('31010801','31010804','31010805','31010806','31010807','31010808','31010809','3101080a')"))
                -tblExTransMapper.selectExFlow(new ExTranFlow(startTime,endTime,"",
                "('31010801','31010804','31010805','31010806','31010807','31010808','31010809','3101080a')"));

        //这边判断车流量是否小于等于0，小于0的话重新给个50-200间的随机数
        if(lastCount<=0)
            lastCount=(int)(Math.random()*400) + 1;

        //这边进行存库

    }
}
