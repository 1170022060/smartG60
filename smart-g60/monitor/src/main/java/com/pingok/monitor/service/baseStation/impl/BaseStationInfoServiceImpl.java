package com.pingok.monitor.service.baseStation.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pingok.monitor.domain.baseStation.TblBaseStationInfo;
import com.pingok.monitor.domain.vo.TblSoftwareDownloadStatus;
import com.pingok.monitor.domain.vo.TblSoftwareUploadStatus;
import com.pingok.monitor.mapper.baseStation.BaseStationInfoMapper;
import com.pingok.monitor.service.baseStation.IBaseStationInfoService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 收费站信息 服务层处理
 *
 * @author qiumin
 */
@Service
public class BaseStationInfoServiceImpl implements IBaseStationInfoService {

    @Autowired
    private BaseStationInfoMapper baseStationInfoMapper;
    @Autowired
    private RemoteUserService remoteUserService;


    @Override
    public List<TblBaseStationInfo> findByUser() {
        R<LoginUser> userResult = remoteUserService.getUserInfo(SecurityUtils.getUsername(), SecurityConstants.INNER);
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new ServiceException("登录用户：" + SecurityUtils.getUsername() + " 不存在");
        }
        SysUser user = userResult.getData().getSysUser();

        List<Long> deptIds = new ArrayList<>();
        deptIds.add(user.getDeptId());
        deptIds.addAll(baseStationInfoMapper.findDeptIdByParentId(user.getDeptId()));
        Example example = new Example(TblBaseStationInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("deptId", deptIds);
        List<TblBaseStationInfo> baseStationInfos = new ArrayList<>();
        List<TblBaseStationInfo> bsi = baseStationInfoMapper.selectByExample(example);
        baseStationInfos.addAll(bsi);
        for(TblBaseStationInfo b:bsi){
            example = new Example(TblBaseStationInfo.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("adminStationId", b.getId());
            bsi = baseStationInfoMapper.selectByExample(example);
            baseStationInfos.addAll(bsi);
        }
        if (baseStationInfos != null && baseStationInfos.size() > 0) {
            List<Map> baseStationStatus;
            List<Map> baseStationSoftwareStatus;
            int provinceStatusEn;
            int provinceStatusEx;
            int commandStatusEn;
            int commandStatusEx;
            int commandFailedEn;
            int commandFailedEx;
            int provinceFailedEn;
            int provinceFailedEx;
            int stationDownloadStatus;
            int stationSoftwareStatus;
            String stationSoftwareVersion;
            String stationDownloadStatusCode;
            String stationSoftwareStatusCode;
            TblSoftwareUploadStatus uploadStatus;
            JSONArray uploadStatusJson;
            TblSoftwareDownloadStatus downloadStatus;
            JSONArray downloadStatusJson;
            for (TblBaseStationInfo s : baseStationInfos) {
                provinceStatusEn = 1;
                provinceStatusEx = 1;
                commandStatusEn = 1;
                commandStatusEx = 1;
                commandFailedEn = 0;
                commandFailedEx = 0;
                provinceFailedEn = 0;
                provinceFailedEx = 0;
                stationDownloadStatus = 0;
                stationSoftwareStatus = 0;
                stationSoftwareVersion = null;
                stationDownloadStatusCode = "未知";
                stationSoftwareStatusCode = "未知";
                baseStationSoftwareStatus = baseStationInfoMapper.getStatusByStationHex(s.getStationHex());
                for (Map m : baseStationSoftwareStatus) {
                    if (String.valueOf(m.get("name")).equals("站收费程序")) {
                        stationSoftwareVersion = String.valueOf(m.get("version"));
                        switch (String.valueOf(m.get("status"))) {
                            case "0":
                                stationSoftwareStatus = 0;
                                stationSoftwareStatusCode = String.valueOf(m.get("statusCode"));
                                break;
                            case "1":
                                stationSoftwareStatus = 1;
                                stationSoftwareStatusCode = "正常";
                                break;
                        }
                    }
                    if (String.valueOf(m.get("uploadFlag")).equals("1")) {
                        if (m.get("uploadStatus") != null) {
                            uploadStatusJson = JSON.parseArray(String.valueOf(m.get("uploadStatus")));
                            for (int i = 0; i < uploadStatusJson.size(); i++) {
                                uploadStatus = JSON.parseObject(uploadStatusJson.getString(i), TblSoftwareUploadStatus.class);
                                if (uploadStatus.getStatus() == 0) {
                                    switch (uploadStatus.getTarget()) {
                                        //上传部
                                        case 1:
                                            if (uploadStatus.getOrientation() == 1) {
                                                commandStatusEn = 0;
                                                commandFailedEn += uploadStatus.getFailed();
                                            } else {
                                                commandStatusEx = 0;
                                                commandFailedEx += uploadStatus.getFailed();
                                            }

                                            break;
                                        //上传省
                                        case 2:
                                            if (uploadStatus.getOrientation() == 1) {
                                                provinceStatusEn = 0;
                                                provinceFailedEn += uploadStatus.getFailed();
                                            } else {
                                                provinceStatusEx = 0;
                                                provinceFailedEx += uploadStatus.getFailed();
                                            }

                                            break;
                                    }
                                }
                            }

                        }
                    }
                    if (String.valueOf(m.get("downloadFlag")).equals("1")) {
                        if (m.get("downloadStatus") != null) {
                            downloadStatusJson = JSONArray.parseArray(String.valueOf(m.get("downloadStatus")));
                            for (int i = 0; i < downloadStatusJson.size(); i++) {
                                downloadStatus = JSON.parseObject(downloadStatusJson.getString(i), TblSoftwareDownloadStatus.class);
                                switch (downloadStatus.getStatus()) {
                                    case 0:
                                        stationDownloadStatus = 0;
                                        if (stationDownloadStatusCode.equals("未知")) {
                                            stationDownloadStatusCode = "";
                                        }
                                        stationDownloadStatusCode = stationDownloadStatusCode + " " + downloadStatus.getStatusCode();
                                        break;
                                    case 1:
                                        stationDownloadStatus = 1;
                                        stationDownloadStatusCode = "正常";
                                        break;
                                }
                            }
                        }
                    }
                }
                baseStationStatus = baseStationInfoMapper.getStatusByStationId(s.getStationId());
                for (Map m : baseStationStatus) {
                    switch (String.valueOf(m.get("orientation"))) {
                        case "1":
                            m.put("commandStatus", commandStatusEn);
                            m.put("commandFailed", commandFailedEn);
                            m.put("provinceStatus", provinceStatusEn);
                            m.put("provinceFailed", provinceFailedEn);
                            break;
                        case "2":
                            m.put("commandStatus", commandStatusEx);
                            m.put("commandfailed", commandFailedEx);
                            m.put("provinceStatus", provinceStatusEx);
                            m.put("provinceFailed", provinceFailedEx);
                            break;
                    }
                }
                s.setStationSoftwareVersion(stationSoftwareVersion);
                s.setBaseStationStatus(baseStationStatus);
                s.setStationSoftwareStatus(stationSoftwareStatus);
                s.setStationSoftwareStatusCode(stationSoftwareStatusCode);
                s.setStationDownloadStatus(stationDownloadStatus);
                s.setStationDownloadStatusCode(stationDownloadStatusCode);
            }
        }
        return baseStationInfos;
    }

    @Override
    public List<Map> getMonitorInfo() {
        return baseStationInfoMapper.getMonitorInfo();
    }
}
