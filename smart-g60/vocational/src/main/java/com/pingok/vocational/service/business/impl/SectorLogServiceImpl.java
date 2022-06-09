package com.pingok.vocational.service.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.baseinfo.TblBaseStationInfo;
import com.pingok.vocational.domain.baseinfo.TblLaneInfo;
import com.pingok.vocational.domain.business.TblCpcEf02Log;
import com.pingok.vocational.domain.business.TblObuEf04Log;
import com.pingok.vocational.domain.business.TblSectorLog;
import com.pingok.vocational.domain.business.vo.SectorLogEnum;
import com.pingok.vocational.mapper.business.TblCpcEf02LogMapper;
import com.pingok.vocational.mapper.business.TblObuEf04LogMapper;
import com.pingok.vocational.mapper.business.TblSectorLogMapper;
import com.pingok.vocational.service.baseinfo.IBaseStationInfoService;
import com.pingok.vocational.service.baseinfo.ILaneInfoService;
import com.pingok.vocational.service.business.ISectorLogService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 扇区日志 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SectorLogServiceImpl implements ISectorLogService {
    @Autowired
    private TblSectorLogMapper tblSectorLogMapper;
    @Autowired
    private TblObuEf04LogMapper tblObuEf04LogMapper;
    @Autowired
    private TblCpcEf02LogMapper tblCpcEf02LogMapper;

    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Autowired
    private ILaneInfoService iLaneInfoService;
    @Autowired
    private IBaseStationInfoService iBaseStationInfoService;

    @Override
    public List<Map> selectSectorLog(Date startTime, Date endTime, String gid, String laneId, Integer passType) {
        return tblSectorLogMapper.selectSectorLog(startTime, endTime, gid, laneId, passType);
    }

    @Override
    public SectorLogEnum selectDetails(Long logId) {
        SectorLogEnum sectorLogEnum = new SectorLogEnum();
        sectorLogEnum.setObuEf01Log(tblSectorLogMapper.selectObuEf01Log(logId));
        sectorLogEnum.setCpu0015Log(tblSectorLogMapper.selectCpu0015Log(logId));
        sectorLogEnum.setCpu0019Log(tblSectorLogMapper.selectCpu0019Log(logId));
        Example example = new Example(TblObuEf04Log.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("logId", logId);
        sectorLogEnum.setObuEf04Log(tblObuEf04LogMapper.selectOneByExample(example));
        if (sectorLogEnum.getObuEf04Log() != null) {
            sectorLogEnum.getObuEf04Log().setProv(tblSectorLogMapper.selectObuEf04LogProv(sectorLogEnum.getObuEf04Log().getId()));
        }
        sectorLogEnum.setCpcEf04Log(tblSectorLogMapper.selectCpcEf04Log(logId));
        Example example2 = new Example(TblCpcEf02Log.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("logId", logId);
        sectorLogEnum.setCpcEf02Log(tblCpcEf02LogMapper.selectOneByExample(example2));
        return sectorLogEnum;
    }

    @Override
    public SectorLogEnum findByGidAndLaneHex(String gid,String laneHex) {
        Example example = new Example(TblSectorLog.class);
        example.createCriteria().andEqualTo("gid", gid);
        TblSectorLog tblSectorLog = tblSectorLogMapper.selectOneByExample(example);
        if (tblSectorLog == null) {
            TblLaneInfo laneInfo = iLaneInfoService.findByLaneHex(laneHex);
            if(laneInfo==null){
                throw new SecurityException("laneHex错误");
            }
            TblBaseStationInfo baseStationInfo = iBaseStationInfoService.findByNetWorkAndStationId(laneInfo.getNetWork(),laneInfo.getStationId());
            if(baseStationInfo==null){
                throw new SecurityException("未查询到对应收费站信息");
            }
            JSONObject data = new JSONObject();
            data.put("ip", baseStationInfo.getIp());
            data.put("port", baseStationInfo.getPort());
            data.put("laneHex", laneHex);
            data.put("gid", gid);
            TblKafkaFailInfo tblKafkaFailInfo = new TblKafkaFailInfo();
            tblKafkaFailInfo.setTopIc(KafkaTopIc.SECTOR_LOG);
            tblKafkaFailInfo.setData(data.toJSONString());
            remoteKafkaService.send(tblKafkaFailInfo);

            throw new SecurityException("正在下载扇区日志请稍后查看");
        }
        SectorLogEnum sectorLogEnum = new SectorLogEnum();
        sectorLogEnum.setObuEf01Log(tblSectorLogMapper.selectObuEf01Log(tblSectorLog.getId()));
        sectorLogEnum.setCpu0015Log(tblSectorLogMapper.selectCpu0015Log(tblSectorLog.getId()));
        sectorLogEnum.setCpu0019Log(tblSectorLogMapper.selectCpu0019Log(tblSectorLog.getId()));
        example = new Example(TblObuEf04Log.class);
        example.createCriteria().andEqualTo("logId", tblSectorLog.getId());
        sectorLogEnum.setObuEf04Log(tblObuEf04LogMapper.selectOneByExample(example));
        if (sectorLogEnum.getObuEf04Log() != null) {
            sectorLogEnum.getObuEf04Log().setProv(tblSectorLogMapper.selectObuEf04LogProv(sectorLogEnum.getObuEf04Log().getId()));
        }
        sectorLogEnum.setCpcEf04Log(tblSectorLogMapper.selectCpcEf04Log(tblSectorLog.getId()));
        Example example2 = new Example(TblCpcEf02Log.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("logId", tblSectorLog.getId());
        sectorLogEnum.setCpcEf02Log(tblCpcEf02LogMapper.selectOneByExample(example2));
        return sectorLogEnum;
    }
}
