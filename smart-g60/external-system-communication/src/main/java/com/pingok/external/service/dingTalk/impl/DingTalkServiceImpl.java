package com.pingok.external.service.dingTalk.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.DingTalkConfig;
import com.pingok.external.service.dingTalk.IDeptService;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteDeptService;
import com.ruoyi.system.api.domain.SysDept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DingTalkServiceImpl implements IDingTalkService {

    @Autowired
    private IDeptService iDeptService;

    @Autowired
    private RemoteDeptService remoteDeptService;

    @Override
    public void orgDeptRemove(JSONArray deptIds) {
        R r;
        int deptIdSize = deptIds.size();
        for (int i = 0; i < deptIdSize; i++) {
            r = remoteDeptService.removeInner(deptIds.getLong(i),SecurityConstants.INNER);
            if (r != null) {
                if (r.getCode() == R.FAIL) {
                    log.error("删除：" + deptIds.getLong(i) + "部门信息失败，原因：" + r.getMsg());
                }
            } else {
                log.error("删除：" + deptIds.getLong(i) + "部门信息失败，原因：ruoyi-system服务无响应");
            }
        }
    }

    @Override
    public void orgDeptModify(JSONArray deptIds) {
        String token = iDeptService.gettoken();
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", token);
        param.put("language", "zh_CN");
        String res;
        R<SysDept> sysDeptR;
        SysDept sysDept;
        R r;
        JSONObject object;
        int deptIdSize = deptIds.size();
        for (int i = 0; i < deptIdSize; i++) {
            param.put("dept_id", deptIds.getLong(i));
            res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/v2/department/get", param);
            if (!StringUtils.isEmpty(res)) {
                object = JSONObject.parseObject(res);
                if (object.getInteger("errcode") == 0) {
                    object = object.getJSONObject("result");
                    sysDeptR = remoteDeptService.getInfoInner(object.getLong("dept_id"), SecurityConstants.INNER);
                    if (sysDeptR != null && sysDeptR.getCode() == R.SUCCESS) {
                        sysDept = sysDeptR.getData();
                        sysDept.setDeptId(object.getLong("dept_id"));
                        sysDept.setDeptName(object.getString("name"));
                        sysDept.setParentId(object.getLong("parent_id"));
                        r = remoteDeptService.editInner(sysDept, SecurityConstants.INNER);
                        if (r != null) {
                            if (r.getCode() == R.FAIL) {
                                log.error("更新：" + object.getString("name") + "部门信息失败，原因：" + r.getMsg());
                            }
                        } else {
                            log.error("更新：" + object.getString("name") + "部门信息失败，原因：ruoyi-system服务无响应");
                        }
                    }else {
                        sysDept = new SysDept();
                        sysDept.setStatus("0");
                        sysDept.setDelFlag("0");
                        sysDept.setDeptId(object.getLong("dept_id"));
                        sysDept.setDeptName(object.getString("name"));
                        sysDept.setParentId(object.getLong("parent_id"));
                        r = remoteDeptService.addInner(sysDept, SecurityConstants.INNER);
                        if (r != null) {
                            if (r.getCode() == R.FAIL) {
                                log.error("新增：" + object.getString("name") + "部门信息失败，原因：" + r.getMsg());
                            }
                        } else {
                            log.error("新增：" + object.getString("name") + "部门信息失败，原因：ruoyi-system服务无响应");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void orgDeptCreate(JSONArray deptIds) {
        String token = iDeptService.gettoken();
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", token);
        param.put("language", "zh_CN");
        String res;
        SysDept sysDept;
        R r;
        JSONObject object;
        int deptIdSize = deptIds.size();
        for (int i = 0; i < deptIdSize; i++) {
            param.put("dept_id", deptIds.getLong(i));
            res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/v2/department/get", param);
            if (!StringUtils.isEmpty(res)) {
                object = JSONObject.parseObject(res);
                if (object.getInteger("errcode") == 0) {
                    object = object.getJSONObject("result");
                    sysDept = new SysDept();
                    sysDept.setStatus("0");
                    sysDept.setDelFlag("0");
                    sysDept.setDeptId(object.getLong("dept_id"));
                    sysDept.setDeptName(object.getString("name"));
                    sysDept.setParentId(object.getLong("parent_id"));
                    r = remoteDeptService.addInner(sysDept, SecurityConstants.INNER);
                    if (r != null) {
                        if (r.getCode() == R.FAIL) {
                            log.error("新增：" + object.getString("name") + "部门信息失败，原因：" + r.getMsg());
                        }
                    } else {
                        log.error("新增：" + object.getString("name") + "部门信息失败，原因：ruoyi-system服务无响应");
                    }

                }
            }
        }
    }


    @Override
    public void updateDepartmentList(Long deptId) {
        String token = iDeptService.gettoken();
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", token);
        param.put("dept_id", deptId);
        param.put("language", "zh_CN");
        String res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/v2/department/listsub", param);
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("errcode") == 0) {
                JSONArray array = object.getJSONArray("result");
                if (!array.isEmpty() && array.size() > 0) {
                    int size = array.size();
                    SysDept sysDept;
                    R r;
                    R<SysDept> sysDeptR;
                    for (int i = 0; i < size; i++) {
                        object = array.getJSONObject(i);
                        sysDeptR = remoteDeptService.getInfoInner(object.getLong("dept_id"), SecurityConstants.INNER);
                        if (sysDeptR != null && sysDeptR.getCode() == R.SUCCESS) {
                            sysDept = sysDeptR.getData();
                            sysDept.setDeptId(object.getLong("dept_id"));
                            sysDept.setDeptName(object.getString("name"));
                            sysDept.setParentId(object.getLong("parent_id"));
                            r = remoteDeptService.editInner(sysDept, SecurityConstants.INNER);
                            if (r != null) {
                                if (r.getCode() == R.SUCCESS) {
                                    updateDepartmentList(sysDept.getDeptId());
                                } else {
                                    log.error("更新：" + object.getString("name") + "部门信息失败，原因：" + r.getMsg());
                                }
                            } else {
                                log.error("更新：" + object.getString("name") + "部门信息失败，原因：ruoyi-system服务无响应");
                            }
                        } else {
                            sysDept = new SysDept();
                            sysDept.setStatus("0");
                            sysDept.setDelFlag("0");
                            sysDept.setOrderNum(String.valueOf(i));
                            sysDept.setDeptId(object.getLong("dept_id"));
                            sysDept.setDeptName(object.getString("name"));
                            sysDept.setParentId(object.getLong("parent_id"));
                            r = remoteDeptService.addInner(sysDept, SecurityConstants.INNER);
                            if (r != null) {
                                if (r.getCode() == R.SUCCESS) {
                                    updateDepartmentList(sysDept.getDeptId());
                                } else {
                                    log.error("新增：" + object.getString("name") + "部门信息失败，原因：" + r.getMsg());
                                }
                            } else {
                                log.error("新增：" + object.getString("name") + "部门信息失败，原因：ruoyi-system服务无响应");
                            }
                        }
                    }
                }
            } else {
                throw new SecurityException("获取钉钉部门列表失败：" + object.getString("errmsg"));

            }
        }
    }

}
