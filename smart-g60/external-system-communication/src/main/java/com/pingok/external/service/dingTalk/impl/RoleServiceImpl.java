package com.pingok.external.service.dingTalk.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.DingTalkConfig;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.pingok.external.service.dingTalk.IRoleService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteRoleService;
import com.ruoyi.system.api.domain.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IDingTalkService iDingTalkService;
    @Autowired
    private RemoteRoleService remoteRoleService;

    @Override
    public void labelConfDel(JSONArray labelIds) {
        int size;
        R r;
        Long[] ids;
        if (labelIds != null && labelIds.size() > 0) {
            size = labelIds.size();
            ids = new Long[size];
            for (int i = 0; i < size; i++) {
                ids[i] = labelIds.getLong(i);
            }
            r = remoteRoleService.removeInner(ids, SecurityConstants.INNER);
            if (r != null) {
                if (r.getCode() == R.FAIL) {
                    log.error("删除角色信息失败，原因：" + r.getMsg());
                }
            } else {
                log.error("删除角色信息失败，原因：ruoyi-system服务无响应");
            }
        }
    }

    @Override
    public void labelConfModify(JSONArray labels) {
        int size;
        JSONObject object;
        SysRole sysRole;
        R<SysRole> sysRoleR;
        R r;
        if (labels != null && labels.size() > 0) {
            size = labels.size();
            for (int i = 0; i < size; i++) {
                sysRole = null;
                object = JSONObject.parseObject(labels.getString(i));
                sysRoleR = remoteRoleService.getInfoInner(object.getLong("id"), SecurityConstants.INNER);
                if (sysRoleR != null && sysRoleR.getCode() == R.SUCCESS) {
                    sysRole = sysRoleR.getData();
                }
                if (sysRole != null) {
                    sysRole.setRoleId(object.getLong("id"));
                    sysRole.setRoleName(object.getString("name"));
                    sysRole.setRemark(object.getString("name"));
                    sysRole.setRoleKey(PinYinUtil.getPingYin(object.getString("name")));
                    if(sysRole.getMenuIds()==null){
                        sysRole.setMenuIds(new Long[0]);
                    }
                    r = remoteRoleService.editInner(sysRole, SecurityConstants.INNER);
                    if (r != null) {
                        if (r.getCode() == R.FAIL) {
                            log.error("更新：" + object.getString("name") + "角色信息失败，原因：" + r.getMsg());
                        }
                    } else {
                        log.error("更新：" + object.getString("name") + "角色信息失败，原因：ruoyi-system服务无响应");
                    }
                }
            }
        }
    }

    @Override
    public void labelConfAdd(JSONArray labelIds) {
        int size;
        String res;
        JSONObject object;
        SysRole sysRole;
        R r;
        String token = iDingTalkService.gettoken();
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", token);
        if (labelIds != null && labelIds.size() > 0) {
            size = labelIds.size();
            for (int i = 0; i < size; i++) {
                param.put("roleId", labelIds.getLong(i));
                res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/role/getrole", param);
                if (!StringUtils.isEmpty(res)) {
                    object = JSONObject.parseObject(res);
                    if (object.getInteger("errcode") == 0) {
                        object = object.getJSONObject("role");
                        if (object.getInteger("groupId") != -1) {
                            sysRole = new SysRole();
                            sysRole.setRoleId(labelIds.getLong(i));
                            sysRole.setRoleName(object.getString("name"));
                            sysRole.setRemark(object.getString("name"));
                            sysRole.setRoleKey(PinYinUtil.getPingYin(object.getString("name")));
                            sysRole.setStatus("0");
                            sysRole.setDataScope("1");
                            sysRole.setDeptCheckStrictly(true);
                            sysRole.setMenuCheckStrictly(true);
                            sysRole.setRoleSort(labelIds.getString(i));
                            sysRole.setMenuIds(new Long[0]);
                            r = remoteRoleService.addInner(sysRole, SecurityConstants.INNER);
                            if (r != null) {
                                if (r.getCode() == R.FAIL) {
                                    log.error("新增：" + object.getString("name") + "角色信息失败，原因：" + r.getMsg());
                                }
                            } else {
                                log.error("新增：" + object.getString("name") + "角色信息失败，原因：ruoyi-system服务无响应");
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void updateRoleList() {
        JSONArray roles;
        JSONObject role;
        int rolesSize;
        int listSize;
        SysRole sysRole;
        R<SysRole> sysRoleR;
        R r;
        String token = iDingTalkService.gettoken();
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", token);
        String res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/role/list", param);
        if (!StringUtils.isEmpty(res)) {
            JSONObject object = JSONObject.parseObject(res);
            if (object.getInteger("errcode") == 0) {
                JSONArray list = object.getJSONObject("result").getJSONArray("list");
                if (list != null && list.size() > 0) {
                    listSize = list.size();
                    for (int i = 0; i < listSize; i++) {
                        roles = list.getJSONObject(i).getJSONArray("roles");
                        if (roles != null && roles.size() > 0) {
                            rolesSize = roles.size();
                            for (int j = 0; j < rolesSize; j++) {
                                role = roles.getJSONObject(j);
                                sysRole = null;
                                sysRoleR = remoteRoleService.getInfoInner(role.getLong("id"), SecurityConstants.INNER);
                                if (sysRoleR != null && sysRoleR.getCode() == R.SUCCESS) {
                                    sysRole = sysRoleR.getData();
                                }
                                if (sysRole == null) {
                                    sysRole = new SysRole();
                                    sysRole.setRoleId(role.getLong("id"));
                                    sysRole.setRoleName(role.getString("name"));
                                    sysRole.setRemark(role.getString("name"));
                                    sysRole.setRoleKey(PinYinUtil.getPingYin(role.getString("name")));
                                    sysRole.setStatus("0");
                                    sysRole.setDataScope("1");
                                    sysRole.setDeptCheckStrictly(true);
                                    sysRole.setMenuCheckStrictly(true);
                                    sysRole.setRoleSort(role.getString("id"));
                                    sysRole.setMenuIds(new Long[0]);
                                    r = remoteRoleService.addInner(sysRole, SecurityConstants.INNER);
                                    if (r != null) {
                                        if (r.getCode() == R.FAIL) {
                                            log.error("新增：" + role.getString("name") + "角色信息失败，原因：" + r.getMsg());
                                        }
                                    } else {
                                        log.error("新增：" + role.getString("name") + "角色信息失败，原因：ruoyi-system服务无响应");
                                    }
                                } else {
                                    sysRole.setRoleId(role.getLong("id"));
                                    sysRole.setRoleName(role.getString("name"));
                                    sysRole.setRemark(role.getString("name"));
                                    sysRole.setRoleKey(PinYinUtil.getPingYin(role.getString("name")));
                                    r = remoteRoleService.editInner(sysRole, SecurityConstants.INNER);
                                    if (r != null) {
                                        if (r.getCode() == R.FAIL) {
                                            log.error("更新：" + role.getString("name") + "角色信息失败，原因：" + r.getMsg());
                                        }
                                    } else {
                                        log.error("更新：" + role.getString("name") + "角色信息失败，原因：ruoyi-system服务无响应");
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
