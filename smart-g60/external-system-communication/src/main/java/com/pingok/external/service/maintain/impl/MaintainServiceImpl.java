package com.pingok.external.service.maintain.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.MaintainConfig;
import com.pingok.external.config.RoadDoctorConfig;
import com.pingok.external.domain.maintain.TblDiseaseData;
import com.pingok.external.domain.maintain.vo.*;
import com.pingok.external.domain.roadDoctor.TblRoadDisease;
import com.pingok.external.domain.roadDoctor.TblRoadDiseaseReport;
import com.pingok.external.domain.roadDoctor.TblRoadDiseaseType;
import com.pingok.external.domain.roadDoctor.TblRoadPatrolInspection;
import com.pingok.external.mapper.maintain.TblDiseaseDataMapper;
import com.pingok.external.mapper.roadDoctor.*;
import com.pingok.external.service.maintain.IMaintainService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MaintainServiceImpl implements IMaintainService {
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
    @Autowired
    private TblDiseaseDataMapper tblDiseaseDataMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public void push(DiseaseDataVo diseaseDataVo) {
        diseaseDataVo.getDiseaseData().setId(remoteIdProducerService.nextId());
        diseaseDataVo.getDiseaseData().setDiseaseId(diseaseDataVo.getId());
        diseaseDataVo.getDiseaseData().setToken(getToken());
//        diseaseDataVo.getDiseaseData().setDistrict("市属");
//        diseaseDataVo.getDiseaseData().setBelongcom("上海路桥");
        TblRoadDisease roadDisease=tblRoadDiseaseMapper.selectByPrimaryKey(diseaseDataVo.getId());

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

        diseaseDataVo.getDiseaseData().setReportby(SecurityUtils.getUsername());
        diseaseDataVo.getDiseaseData().setFacilitytype("公路桥梁");
        diseaseDataVo.getDiseaseData().setSid("G0060310010");
        diseaseDataVo.getDiseaseData().setFacilityname("G60沪昆上海段");
        diseaseDataVo.getDiseaseData().setStartpoint(roadPatrolInspection.getStartkmm());
        diseaseDataVo.getDiseaseData().setEndpoint(roadPatrolInspection.getBEndkmm());
//        diseaseDataVo.getDiseaseData().setProblemparts();
        diseaseDataVo.getDiseaseData().setProblemtype(roadDiseaseType.getQuestName());
        diseaseDataVo.getDiseaseData().setPlevel(roadDisease.getMQuestionDegree());
//        diseaseDataVo.getDiseaseData().setLng();
//        diseaseDataVo.getDiseaseData().setLat();
//        diseaseDataVo.getDiseaseData().setStatus("未处理");
        switch(roadPatrolInspection.getBBatchType())
        {
            case 1 : diseaseDataVo.getDiseaseData().setChecktype("病害巡查");break;
            case 2 : diseaseDataVo.getDiseaseData().setChecktype("设施巡查");break;
            case 3 : diseaseDataVo.getDiseaseData().setChecktype("量化");break;
        }
        diseaseDataVo.getDiseaseData().setPicurl(roadDisease.getPPicurl());
        diseaseDataVo.getDiseaseData().setUploadTime(new Date());

        DataVo dataVo=new DataVo();
        BeanUtils.copyNotNullProperties(diseaseDataVo.getDiseaseData(), dataVo);

        String res = HttpUtil.post(MaintainConfig.HOST +"/maintenance/addDisease", JSON.toJSONString(dataVo));

        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") == 200)
            {
                roadDisease.setOrderNum(object.getString("data"));
                roadDisease.setUploadStatus(1);
                tblRoadDiseaseMapper.updateByPrimaryKey(roadDisease);
                tblDiseaseDataMapper.insert(diseaseDataVo.getDiseaseData());
            }else
            {
                throw new SecurityException("病害数据上传失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("病害数据上传接口返回空");
        }
    }

    private String getToken() {
        String token = null;
        if(redisService.hasKey("yhyzwToken")){
            token = redisService.getCacheObject("yhyzwToken");
            return token;
        }else {
            InterfaceVo interfaceVo =new InterfaceVo();
            interfaceVo.setInterfaceUser(MaintainConfig.USER);
            interfaceVo.setInterfacePwd(MaintainConfig.PASSWORD);
            String res = HttpUtil.post(MaintainConfig.HOST +"/json/getAccessToken", JSON.toJSONString(interfaceVo));
            if (!StringUtils.isEmpty(res)) {
                JSONObject object = JSONObject.parseObject(res);
                if (object.getInteger("code") == 200)
                {
                    JSONObject data = object.getJSONObject("data");
                    token = data.getString("token");
                    redisService.setCacheObject("yhyzwToken",token,120L, TimeUnit.MINUTES);
                    return token;
                }else
                {
                    throw new SecurityException("获取访问令牌失败：" + object.getString("message"));
                }
            }else{
                throw new SecurityException("获取访问令牌接口返回空");
            }
        }
    }

    @Override
    public void addDiseaseProc(Long id,String status,String note) {
        TblRoadDisease roadDisease=tblRoadDiseaseMapper.selectByPrimaryKey(id);

        Example example = new Example(TblDiseaseData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("diseaseId", id);
        TblDiseaseData diseaseData=tblDiseaseDataMapper.selectOneByExample(example);

        StatusVo statusVo=new StatusVo();
        statusVo.setToken(getToken());
        statusVo.setOrdernum(roadDisease.getOrderNum());
        statusVo.setStatus(status);
        statusVo.setUsername(SecurityUtils.getUsername());
        statusVo.setNote(note);
        statusVo.setPdate(new Date().getTime());

        String res = HttpUtil.post(MaintainConfig.HOST +"/maintenance/addDiseaseProc", JSON.toJSONString(statusVo));
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("code") == 200)
            {
                diseaseData.setStatus(status);
                diseaseData.setUpdateTime(new Date());
            }else
            {
                throw new SecurityException("病害状态上传失败：" + object.getString("message"));
            }
        }else{
            throw new SecurityException("病害状态上传接口返回空");
        }
    }

    @Override
    public void addDiseasePic(Long id,String picType,String fileType) {
        TblRoadDisease roadDisease=tblRoadDiseaseMapper.selectByPrimaryKey(id);
        PictureVo pictureVo =new PictureVo();
        pictureVo.setToken(getToken());
        pictureVo.setOrdernum(roadDisease.getOrderNum());
        pictureVo.setPictype(picType);
        pictureVo.setFiletype(fileType);
        pictureVo.setHttpurl(roadDisease.getPPicurl());
        String res = HttpUtil.post(MaintainConfig.HOST +"/maintenance/addDiseasePic", JSON.toJSONString(pictureVo));
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
    public List<String> getBackOrderNums() {
        String token=getToken();
        JSONObject param = new JSONObject();
        param.put("token", token);
        String res = HttpUtil.post(MaintainConfig.HOST +"/maintenance/addDiseasePic", param);
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
