package com.pingok.datacenter.service.opt.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.lane.TblLaneStatus;
import com.pingok.datacenter.domain.opt.TblOptId;
import com.pingok.datacenter.domain.opt.TblOptWorkDetail;
import com.pingok.datacenter.domain.opt.vo.ModelWorkTableVo;
import com.pingok.datacenter.domain.roster.blackcard.TblBlackCardStationUsed;
import com.pingok.datacenter.mapper.opt.TblOptIdMapper;
import com.pingok.datacenter.mapper.opt.TblOptWorkDetailMapper;
import com.pingok.datacenter.service.opt.IOptWorkDetailService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 排班当岗 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OptWorkDetailServiceImpl implements IOptWorkDetailService {

    @Autowired
    private TblOptWorkDetailMapper tblOptWorkDetailMapper;
    @Autowired
    private TblOptIdMapper tblOptIdMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void optWorkDetail(JSONObject obj) {
        TblOptWorkDetail optWorkDetail = new TblOptWorkDetail();
        optWorkDetail.setId(remoteIdProducerService.nextId());
        optWorkDetail.setOptId(obj.getInteger("optId"));
        optWorkDetail.setWorkId(obj.getInteger("workId"));
        optWorkDetail.setWorkStep(obj.getInteger("workStep"));
        optWorkDetail.setWorkMinutes(obj.getInteger("workMinutes"));
        optWorkDetail.setWorkDate(obj.getDate("workDate"));
        optWorkDetail.setLaneNum(obj.getInteger("laneNum"));
        optWorkDetail.setHisWorkId(obj.getInteger("hisWorkId"));
        tblOptWorkDetailMapper.insert(optWorkDetail);
    }

    @Override
    public List<ModelWorkTableVo> makeWorkTable(Date workDate)
    {
        Example example = new Example(TblOptWorkDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("workDate", workDate);
        List<TblOptWorkDetail> details= tblOptWorkDetailMapper.selectByExample(example);
        List<TblOptId> opts =tblOptIdMapper.selectAll();


        List<ModelWorkTableVo> modelWorkList = new ArrayList<>();
        List<String> timeTmp = Arrays.asList(
        "07:00-07:30","07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:45",
                "10:45-11:30","11:30-12:15","12:15-13:00","13:00-13:45","13:45-14:30","14:30-15:15","15:15-16:00",
                "16:00-16:30","16:30-17:00","17:00-17:30","17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30",
                "19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21:30-22:00","22:00-22:30","22:30-23:00",
                "23:00-00:00","00:00-01:00","01:00-02:00","02:00-03:00","03:00-04:00","04:00-05:00","05:00-05:30",
                "05:30-06:00","06:00-07:00");
        for (Integer i = 1; i < 38; i++) {
            ModelWorkTableVo modelWorkTableVo=new ModelWorkTableVo();
            modelWorkTableVo.setStepIdx(i);
            modelWorkTableVo.setName1("");
            modelWorkTableVo.setName2("");
            modelWorkTableVo.setName3("");
            modelWorkTableVo.setName4("");
            modelWorkTableVo.setName5("");
            modelWorkTableVo.setName6("");
            modelWorkTableVo.setName7("");
            modelWorkTableVo.setName8("");
            modelWorkTableVo.setName9("");
            modelWorkTableVo.setName10("");
            modelWorkTableVo.setName11("");
            modelWorkTableVo.setName12("");
            modelWorkTableVo.setName13("");
            modelWorkTableVo.setName14("");
            modelWorkTableVo.setName15("");
            modelWorkTableVo.setName16("");
            modelWorkTableVo.setName17("");
            modelWorkTableVo.setName18("");
            modelWorkTableVo.setName19("");
            modelWorkTableVo.setName20("");
            modelWorkTableVo.setName21("");
            modelWorkTableVo.setName22("");
            modelWorkTableVo.setName24("");
            modelWorkTableVo.setName26("");
            modelWorkTableVo.setName28("");
            modelWorkTableVo.setName30("");
            modelWorkTableVo.setName41("");
            modelWorkTableVo.setName42("");
            modelWorkTableVo.setName43("");
            modelWorkTableVo.setName44("");
            modelWorkTableVo.setStepTime(timeTmp.get(i-1));

            Integer idx=i;
            List<TblOptWorkDetail> tmp = details.stream().filter(o-> Objects.equals(o.getWorkStep(), idx)).sorted(Comparator.comparing(TblOptWorkDetail::getLaneNum)).collect(Collectors.toList());
            for (TblOptWorkDetail owp: tmp)
            {
                switch (owp.getLaneNum())
                {
                    case 1:
                        modelWorkTableVo.setName1(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 2:
                        modelWorkTableVo.setName2(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 3:
                        modelWorkTableVo.setName3(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 4:
                        modelWorkTableVo.setName4(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 5:
                        modelWorkTableVo.setName5(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 6:
                        modelWorkTableVo.setName6(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 7:
                        modelWorkTableVo.setName7(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 8:
                        modelWorkTableVo.setName8(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 9:
                        modelWorkTableVo.setName9(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 10:
                        modelWorkTableVo.setName10(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 11:
                        modelWorkTableVo.setName11(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 12:
                        modelWorkTableVo.setName12(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 13:
                        modelWorkTableVo.setName13(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 14:
                        modelWorkTableVo.setName14(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 15:
                        modelWorkTableVo.setName15(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 16:
                        modelWorkTableVo.setName16(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 17:
                        modelWorkTableVo.setName17(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 18:
                        modelWorkTableVo.setName18(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 19:
                        modelWorkTableVo.setName19(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 20:
                        modelWorkTableVo.setName20(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 21:
                        modelWorkTableVo.setName21(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 22:
                        modelWorkTableVo.setName22(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 24:
                        modelWorkTableVo.setName24(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 26:
                        modelWorkTableVo.setName26(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 28:
                        modelWorkTableVo.setName28(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 30:
                        modelWorkTableVo.setName30(getWorkIdColor(owp, i) + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName() + "</span>");
                        break;
                    case 41:
                        modelWorkTableVo.setName41(opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 42:
                        modelWorkTableVo.setName42(opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 43:
                        modelWorkTableVo.setName43(opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 44:
                        modelWorkTableVo.setName44(opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 51:
                        modelWorkTableVo.setName41(modelWorkTableVo.getName41() + "," + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 52:
                        modelWorkTableVo.setName42(modelWorkTableVo.getName42() + "," + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 53:
                        modelWorkTableVo.setName43(modelWorkTableVo.getName43() + "," + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                    case 54:
                        modelWorkTableVo.setName44(modelWorkTableVo.getName44() + "," + opts.stream().filter(o -> Objects.equals(o.getId(), owp.getOptId())).collect(Collectors.toList()).get(0).getOptName());
                        break;
                }
            }
            modelWorkList.add(modelWorkTableVo);
        }
        return modelWorkList;
    }

    public static String getWorkIdColor(TblOptWorkDetail owp, Integer workStep)
    {
        switch (owp.getWorkId())
        {
            case 1: return "<span style='color:#DDDDDD;' id='" + workStep + "'>";
            case 2: return "<span style='color:#FFB7DD;' id='" + workStep + "'>";
            case 3: return "<span style='color:#FF0088;' id='" + workStep + "'>";
            case 4: return "<span style='color:#FF8888;' id='" + workStep + "'>";
            case 5: return "<span style='color:#FFC8B4;' id='" + workStep + "'>";
            case 6: return "<span style='color:#FF7744;' id='" + workStep + "'>";
            case 7: return "<span style='color:#FFFF77;' id='" + workStep + "'>";
            case 8: return "<span style='color:#CCFF33;' id='" + workStep + "'>";
            case 9: return "<span style='color:#FFEE99;' id='" + workStep + "'>";
            case 10: return "<span style='color:#FFFF00;' id='" + workStep + "'>";
            case 11: return "<span style='color:#00DD00;' id='" + workStep + "'>";
            case 12: return "<span style='color:#00FF99;' id='" + workStep + "'>";
            case 13: return "<span style='color:#33FFFF;' id='" + workStep + "'>";
            case 14: return "<span style='color:#77DDFF;' id='" + workStep + "'>";
            case 15: return "<span style='color:#99BBFF;' id='" + workStep + "'>";
            case 16: return "<span style='color:#CCBBFF;' id='" + workStep + "'>";
            case 17: return "<span style='color:#B088FF;' id='" + workStep + "'>";
            case 18: return "<span style='color:#D1BBFF;' id='" + workStep + "'>";
            case 19: return "<span style='color:#E38EFF;' id='" + workStep + "'>";
            case 20: return "<span style='color:#FF00FF;' id='" + workStep + "'>";
            case 21: return "<span style='color:#DDDDDD;' id='" + workStep + "'>";
            case 22: return "<span style='color:#FFB7DD;' id='" + workStep + "'>";
            case 23: return "<span style='color:#FF0088;' id='" + workStep + "'>";
            case 24: return "<span style='color:#FF8888;' id='" + workStep + "'>";
            case 25: return "<span style='color:#FF0000;' id='" + workStep + "'>";
            case 26: return "<span style='color:#FFC8B4;' id='" + workStep + "'>";
            case 27: return "<span style='color:#FF7744;' id='" + workStep + "'>";
            case 28: return "<span style='color:#FFFF77;' id='" + workStep + "'>";
            case 29: return "<span style='color:#CCFF33;' id='" + workStep + "'>";
            case 30: return "<span style='color:#FFEE99;' id='" + workStep + "'>";
            case 31: return "<span style='color:#FFFF00;' id='" + workStep + "'>";
            case 32: return "<span style='color:#00DD00;' id='" + workStep + "'>";
            case 33: return "<span style='color:#00FF99;' id='" + workStep + "'>";
            case 34: return "<span style='color:#33FFFF;' id='" + workStep + "'>";
            case 35: return "<span style='color:#77DDFF;' id='" + workStep + "'>";
            case 36: return "<span style='color:#99BBFF;' id='" + workStep + "'>";
            case 37: return "<span style='color:#CCBBFF;' id='" + workStep + "'>";
        }
        return "";
    }
}
