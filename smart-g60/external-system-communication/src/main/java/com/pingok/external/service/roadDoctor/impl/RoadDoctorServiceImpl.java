package com.pingok.external.service.roadDoctor.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.BaiDuMapConfig;
import com.pingok.external.config.BeiDouConfig;
import com.pingok.external.config.MaintainConfig;
import com.pingok.external.config.RoadDoctorConfig;
import com.pingok.external.domain.roadDoctor.TblRoadDisease;
import com.pingok.external.domain.roadDoctor.TblRoadDiseaseReport;
import com.pingok.external.domain.roadDoctor.TblRoadDiseaseType;
import com.pingok.external.domain.roadDoctor.TblRoadPatrolInspection;
import com.pingok.external.domain.roadDoctor.vo.*;
import com.pingok.external.mapper.roadDoctor.*;
import com.pingok.external.service.roadDoctor.IRoadDoctorService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.WebServiceUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import tk.mybatis.mapper.entity.Example;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RoadDoctorServiceImpl implements IRoadDoctorService {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblRoadPatrolInspectionMapper tblRoadPatrolInspectionMapper;
    @Autowired
    private TblRoadDiseaseReportMapper tblRoadDiseaseReportMapper;
    @Autowired
    private TblRoadDiseaseTypeMapper tblRoadDiseaseTypeMapper;
    @Autowired
    private TblRoadDiseaseMapper tblRoadDiseaseMapper;
    @Autowired
    private TblRoadDoctorMapper tblRoadDoctorMapper;

    @Override
    public void updateDisease() {
        try {
            JSONObject ret;
            JSONArray shotBatchs;
            JSONObject shotBatch;
            JSONArray batchReports;
            JSONObject batchReport;
            JSONArray crContents;
            JSONObject crContent;
            JSONArray questList;
            JSONObject quest;
            Example example;
            TblRoadPatrolInspection tblRoadPatrolInspection;
            TblRoadDiseaseReport tblRoadDiseaseReport;
            TblRoadDiseaseType tblRoadDiseaseType;
            TblRoadDisease tblRoadDisease;
            String r = WebServiceUtils.get(RoadDoctorConfig.HOST + "/GetShotBatchByUser");
            if (!r.isEmpty()) {
                ret = JSONObject.parseObject(r);
                if (ret.getString("respResult").equals("1")) {
                    shotBatchs = ret.getJSONArray("Json");
                    if (!shotBatchs.isEmpty()) {
                        for (int i = 0; i < shotBatchs.size(); i++) {
                            shotBatch = shotBatchs.getJSONObject(i);
                            example = new Example(TblRoadPatrolInspection.class);
                            example.createCriteria().andEqualTo("bId", shotBatch.getLong("B_Id"));
                            if (tblRoadPatrolInspectionMapper.selectOneByExample(example) == null) {
                                tblRoadPatrolInspection = new TblRoadPatrolInspection();
                                tblRoadPatrolInspection.setId(remoteIdProducerService.nextId());
                                tblRoadPatrolInspection.setBId(shotBatch.getLong("B_Id"));
                                tblRoadPatrolInspection.setBTime(shotBatch.getDate("B_Time"));
                                tblRoadPatrolInspection.setBRoadId(shotBatch.getLong("B_RoadId"));
                                tblRoadPatrolInspection.setStartkmm(shotBatch.getString("StartKMM"));
                                tblRoadPatrolInspection.setBBatchlength(shotBatch.getBigDecimal("B_BatchLength"));
                                tblRoadPatrolInspection.setBImgsCount(shotBatch.getInteger("B_ImgsCount"));
                                tblRoadPatrolInspection.setBHeaveryquesCount(shotBatch.getInteger("B_HeaveryQuesCount"));
                                tblRoadPatrolInspection.setBRoadName(shotBatch.getString("B_RoadName"));
                                tblRoadPatrolInspection.setBBatchType(shotBatch.getInteger("B_BatchType"));
                                tblRoadPatrolInspection.setBRemark(shotBatch.getString("B_Remark"));
                                tblRoadPatrolInspection.setVideo(shotBatch.getString("video"));
                                tblRoadPatrolInspectionMapper.insert(tblRoadPatrolInspection);
                                r = WebServiceUtils.get(RoadDoctorConfig.HOST + "/GetBatchReport?B_Id=" + tblRoadPatrolInspection.getBId());
                                if (!r.isEmpty()) {
                                    ret = JSONObject.parseObject(r);
                                    if (ret.getString("respResult").equals("1")) {
                                        batchReports = ret.getJSONArray("Json");
                                        for (int j = 0; j < batchReports.size(); j++) {
                                            batchReport = batchReports.getJSONObject(j);
                                            example = new Example(TblRoadDiseaseReport.class);
                                            example.createCriteria().andEqualTo("crBid", batchReport.getLong("Cr_Bid"))
                                                    .andEqualTo("crId", batchReport.getLong("Cr_Id"));
                                            if (tblRoadDiseaseReportMapper.selectOneByExample(example) == null) {
                                                tblRoadDiseaseReport = new TblRoadDiseaseReport();
                                                tblRoadDiseaseReport.setId(remoteIdProducerService.nextId());
                                                tblRoadDiseaseReport.setCrBid(batchReport.getLong("Cr_Bid"));
                                                tblRoadDiseaseReport.setCrCount(batchReport.getInteger("Cr_Count"));
                                                tblRoadDiseaseReport.setCrId(batchReport.getLong("Cr_Id"));
                                                tblRoadDiseaseReport.setCrType(batchReport.getInteger("Cr_Type"));
                                                tblRoadDiseaseReportMapper.insert(tblRoadDiseaseReport);
                                            }
                                            crContents = batchReport.getJSONArray("Cr_Content");
                                            for (int k = 0; k < crContents.size(); k++) {
                                                crContent = crContents.getJSONObject(k);
                                                example = new Example(TblRoadDiseaseType.class);
                                                example.createCriteria().andEqualTo("crId", batchReport.getLong("Cr_Id"));
                                                if (tblRoadDiseaseTypeMapper.selectOneByExample(example) == null) {
                                                    tblRoadDiseaseType = new TblRoadDiseaseType();
                                                    tblRoadDiseaseType.setId(remoteIdProducerService.nextId());
                                                    tblRoadDiseaseType.setCrId(batchReport.getLong("Cr_Id"));
                                                    tblRoadDiseaseType.setQuestAccount(crContent.getInteger("QuestAccount"));
                                                    tblRoadDiseaseType.setQuestId(crContent.getLong("QuestId"));
                                                    tblRoadDiseaseType.setQuestName(crContent.getString("QuestName"));
                                                    tblRoadDiseaseTypeMapper.insert(tblRoadDiseaseType);
                                                }
                                                questList = crContent.getJSONArray("QuestList");
                                                for (int h = 0; h < questList.size(); h++) {
                                                    quest = questList.getJSONObject(h);
                                                    example = new Example(TblRoadDisease.class);
                                                    example.createCriteria().andEqualTo("questId", crContent.getLong("QuestId"));
                                                    if (tblRoadDiseaseMapper.selectOneByExample(example) == null) {
                                                        tblRoadDisease = new TblRoadDisease();
                                                        tblRoadDisease.setId(remoteIdProducerService.nextId());
                                                        tblRoadDisease.setQuestId(crContent.getLong("QuestId"));
                                                        tblRoadDisease.setMId(quest.getLong("M_Id"));
                                                        tblRoadDisease.setPPicurl(quest.getString("P_PicUrl"));
                                                        tblRoadDisease.setMXmin(quest.getString("M_XMin"));
                                                        tblRoadDisease.setMYmin(quest.getString("M_YMin"));
                                                        tblRoadDisease.setMXmax(quest.getString("M_XMax"));
                                                        tblRoadDisease.setMYmax(quest.getString("M_YMax"));
                                                        tblRoadDisease.setMQuestionDegree(quest.getString("M_QuestionDegree"));
                                                        tblRoadDisease.setPZhuangHao(quest.getString("P_ZhuangHao"));
                                                        tblRoadDiseaseMapper.insert(tblRoadDisease);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new ServiceException(e.getMessage());
        } catch (SAXException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Map> list(String questName, String pZhuangHao, Date startTime, Date endTime) {
        return tblRoadDoctorMapper.list(questName, pZhuangHao, startTime, endTime);
    }

    @Override
    public int push(Long id) {
        TblRoadDisease tblRoadDisease= tblRoadDiseaseMapper.selectByPrimaryKey(id);
        tblRoadDisease.setUploadStatus(1);
        return tblRoadDiseaseMapper.updateByPrimaryKeySelective(tblRoadDisease);
    }

    @Override
    public LoginVo login() {
        InterfaceVo interfaceVo =new InterfaceVo();
        interfaceVo.setInterfaceUser(MaintainConfig.USER);
        interfaceVo.setInterfacePwd(MaintainConfig.PASSWORD);
        String res = HttpUtil.post(RoadDoctorConfig.HOST +"/json/getAccessToken", JSON.toJSONString(interfaceVo));
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") == 200)
            {
                JSONObject data = object.getJSONObject("data");
                LoginVo loginVo=new LoginVo();
                loginVo.setToken(data.getString("token"));
                loginVo.setExiresInMinute(data.getInteger("ExiresInMinute"));
                loginVo.setBeginDate(data.getLong("BeginDate"));
                loginVo.setCompanyName(data.getString("CompanyName"));
                return loginVo;
            }else
            {
                throw new SecurityException("获取访问令牌失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("获取访问令牌接口返回空");
        }
    }

    @Override
    public String addDisease(Long id,String token) {
        DiseaseData diseaseData=new DiseaseData();
        diseaseData.setToken(token);
        diseaseData.setDistrict("市属");
        diseaseData.setBelongcom("上海路桥");
        TblRoadDisease roadDisease=tblRoadDiseaseMapper.selectByPrimaryKey(id);

        Example example = new Example(TblRoadDiseaseType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("questId", roadDisease.getQuestId());
        TblRoadDiseaseType roadDiseaseType=tblRoadDiseaseTypeMapper.selectOneByExample(example);

        example =new Example(TblRoadDiseaseType.class);
        criteria = example.createCriteria();
        criteria.andEqualTo("crId", roadDiseaseType.getCrId());
        TblRoadDiseaseReport roadDiseaseReport=tblRoadDiseaseReportMapper.selectOneByExample(example);

        example =new Example(TblRoadDiseaseType.class);
        criteria = example.createCriteria();
        criteria.andEqualTo("bId", roadDiseaseReport.getCrBid());
        TblRoadPatrolInspection roadPatrolInspection=tblRoadPatrolInspectionMapper.selectOneByExample(example);

        diseaseData.setReportby("沪杭");
        diseaseData.setFacilitytype("公路桥梁");
        //diseaseData.setSid(roadPatrolInspection.getBRoadId());

        String res = HttpUtil.post(RoadDoctorConfig.HOST +"/maintenance/addDisease", JSON.toJSONString(diseaseData));

        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") == 200)
            {
                return object.getString("data");
            }else
            {
                throw new SecurityException("病害数据上传失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("病害数据上传接口返回空");
        }
    }

    @Override
    public void addDiseaseProc(StatusVo statusVo) {
        String res = HttpUtil.post(RoadDoctorConfig.HOST +"/maintenance/addDiseaseProc", JSON.toJSONString(statusVo));
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") != 200)
            {
                throw new SecurityException("病害状态上传失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("病害状态上传接口返回空");
        }
    }

    @Override
    public void addDiseasePic(PictureVo pictureVo) {
        String res = HttpUtil.post(RoadDoctorConfig.HOST +"/maintenance/addDiseasePic", JSON.toJSONString(pictureVo));
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") != 200)
            {
                throw new SecurityException("病害图片上传失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("病害图片上传接口返回空");
        }
    }

    @Override
    public List<String> getBackOrderNums(String token) {
        JSONObject param = new JSONObject();
        param.put("token", token);
        String res = HttpUtil.post(RoadDoctorConfig.HOST +"/maintenance/addDiseasePic", param);
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") == 200)
            {
                String data= object.getString("data");
                return JSON.parseArray(data, String.class);
            }else
            {
                throw new SecurityException("病害数据上传失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("病害数据上传接口返回空");
        }
    }
}
