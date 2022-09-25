package com.pingok.vocational.service.infoboard.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.infoboard.VmsInfoByType;
import com.pingok.vocational.domain.infoboard.VmsInfoByTypeList;
import com.pingok.vocational.domain.infoboard.VmsPresetList;
import com.pingok.vocational.domain.release.TblReleasePreset;
import com.pingok.vocational.mapper.device.TblDeviceInfoMapper;
import com.pingok.vocational.mapper.release.TblReleasePresetMapper;
import com.pingok.vocational.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InfoBoardServiceImpl implements IInfoBoardService {

    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;

    @Autowired
    private TblReleasePresetMapper tblReleasePresetMapper;

    @Override
    public List<VmsInfoByTypeList> getListByType(String type, String protocol) {

        log.info("情报板发布：获取列表（按类型分组）...");
        List<VmsInfoByTypeList> ret = new ArrayList<>();
        List<VmsInfoByType> list = tblDeviceInfoMapper.getVmsListByType(type, protocol);
        VmsInfoByTypeList A = new VmsInfoByTypeList();
        VmsInfoByTypeList F = new VmsInfoByTypeList();
        VmsInfoByTypeList S = new VmsInfoByTypeList();
        VmsInfoByTypeList X = new VmsInfoByTypeList();

        for (VmsInfoByType v : list) {
            if(StringUtils.isEmpty(v.getDeviceModel())) continue;
            switch (v.getDeviceModel()) {
                case "A板": A.getVmsList().add(v); break;
                case "F板": F.getVmsList().add(v); break;
                case "小板": S.getVmsList().add(v); break;
                case "限速板": X.getVmsList().add(v); break;
            }
        }

        A.setDeviceName("A板");
        A.setDeviceCnt(A.getVmsList().size());
        A.setFaultCnt(A.getVmsList().stream().filter(x -> x.getDeviceStatus() == 0).count());
        ret.add(A);

        F.setDeviceName("F板");
        F.setDeviceCnt(F.getVmsList().size());
        F.setFaultCnt(F.getVmsList().stream().filter(x -> x.getDeviceStatus() == 0).count());
        ret.add(F);

        S.setDeviceName("小板");
        S.setDeviceCnt(S.getVmsList().size());
        S.setFaultCnt(S.getVmsList().stream().filter(x -> x.getDeviceStatus() == 0).count());
        ret.add(S);

        X.setDeviceName("限速板");
        X.setDeviceCnt(X.getVmsList().size());
        X.setFaultCnt(X.getVmsList().stream().filter(x -> x.getDeviceStatus() == 0).count());
        ret.add(X);

//        Map group = list.stream().collect(Collectors.groupingBy(t -> t.getDeviceModel()));
//        Iterator iter = group.keySet().iterator();
//        while (iter.hasNext()) {
//            String key = (String) iter.next();
//            VmsInfoByTypeList vmsList = new VmsInfoByTypeList();
//            switch (key) {
//                case "A板": {
//                    vmsList.setId(1);
//                    vmsList.setLabel(key);
//                    vmsList.setLevel(1);
//                    break;
//                }
//                case "F板": F = (List<VmsInfoByType>) group.get(key); break;
//                case "限速板": S = (List<VmsInfoByType>) group.get(key); break;
//                case "小板": X = (List<VmsInfoByType>) group.get(key); break;
//            }
//        }

        return ret;
    }

    @Override
    public List<VmsPresetList> getPreset() {

        log.info("情报板发布：获取预设分组列表...");
        List<VmsPresetList> ret = new ArrayList<>();
        List<TblReleasePreset> list = tblReleasePresetMapper.selectAll();

        Map group = list.stream().collect(Collectors.groupingBy(t -> t.getPresetName()));
        List<String> keyList = new ArrayList<>(group.keySet());
        for (String key: keyList) {
            VmsPresetList li = new VmsPresetList();
            li.setPresetInfo(key);
            li.setPresetList((List<TblReleasePreset>) group.get(key));
            ret.add(li);
        }

        return ret;
    }

    @Override
    public boolean publish(JSONObject content) {
        return false;
    }
}
