package com.pingok.external.service.dingTalk.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.DingTalkConfig;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.pingok.external.service.dingTalk.IUserService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteDeptService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemotePostService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysPost;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IDingTalkService iDingTalkService;

    @Autowired
    private RemoteDeptService remoteDeptService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemotePostService remotePostService;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;


    @Override
    public void delete(JSONArray userIds) {
        R<LoginUser> userR;
        LoginUser loginUser;
        R r;
        int size = userIds.size();
        Long[] ids = new Long[size];
        for (int i = 0; i < size; i++) {
            userR = remoteUserService.getUserInfoByDingTalkId(userIds.getString(i), SecurityConstants.INNER);
            if (userR != null) {
                loginUser = userR.getData();
                if (loginUser != null) {
                    ids[i] = loginUser.getUserid();
                }
            }
        }
        r = remoteUserService.removeInner(ids, SecurityConstants.INNER);
        if (r != null) {
            if (r.getCode() == R.FAIL) {
                log.error("id为" + ids.toString() + "的用户删除保存失败，原因：" + r.getMsg());
            }
        } else {
            log.error("id为" + ids.toString() + "的用户删除失败，原因：ruoyi-system服务无响应");
        }
    }

    @Override
    public void saveOrUpdate(JSONArray userIds) {
        String res;
        Map<String, Object> param;
        JSONArray roles;
        JSONObject user;
        R<LoginUser> userR;
        LoginUser loginUser;
        Long[] postIds;
        R<SysPost> sysPostR;
        SysPost sysPost;
        R r;
        SysUser sysUser;
        Long[] roleIds;
        String userName;
        String postName;
        Long postId;
        int roleSize;
        int size = userIds.size();
        String token = iDingTalkService.gettoken();
        for (int i = 0; i < size; i++) {
            param = new HashMap<>();
            param.put("access_token", token);
            param.put("userid", userIds.get(i));
            res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/v2/user/get", param);
            if (!StringUtils.isEmpty(res)) {
                user = JSONObject.parseObject(res);
                if (user.getInteger("errcode") == 0) {
                    user = user.getJSONObject("result");
                    userName = user.getString("mobile");
                    if (userName != null && !userName.equals("")) {
                        userR = remoteUserService.getUserInfo(userName, SecurityConstants.INNER);
                        if (userR != null) {
                            loginUser = userR.getData();
                            if (loginUser == null) {
                                sysUser = new SysUser();
                                sysUser.setUserId(remoteIdProducerService.nextId());
                                sysUser.setDingTalkId(user.getString("userid"));
                                sysUser.setDeptId(user.getJSONArray("dept_id_list").getLong(0));
                                sysUser.setUserName(userName);
                                sysUser.setNickName(user.getString("name"));
                                sysUser.setEmail(user.getString("email"));
                                sysUser.setPhonenumber(user.getString("mobile"));
                                sysUser.setSex("2");
                                sysUser.setPassword(userName);
                                sysUser.setJobNumber(user.getString("job_number"));
                                sysUser.setStatus("0");
                                sysUser.setDelFlag("0");
                                roles = user.getJSONArray("role_list");
                                if (roles != null && roles.size() > 0) {
                                    roleSize = roles.size();
                                    roleIds = new Long[roleSize];
                                    for (int j = 0; j < roleSize; j++) {
                                        roleIds[j] = roles.getJSONObject(j).getLong("id");
                                    }
                                    sysUser.setRoleIds(roleIds);
                                }
                                if (user.containsKey("title") && user.getString("title") != null && !user.getString("title").equals("")) {
                                    postIds = new Long[1];
                                    postName = user.getString("title").replaceAll("/", " ");
                                    sysPostR = remotePostService.getInfoByNameInner(postName, SecurityConstants.INNER);
                                    if (sysPostR != null && sysPostR.getCode() == R.SUCCESS && sysPostR.getData() != null) {
                                        postId = sysPostR.getData().getPostId();
                                    } else {
                                        postId = remoteIdProducerService.nextId();
                                        sysPost = new SysPost();
                                        sysPost.setPostId(postId);
                                        sysPost.setPostCode(PinYinUtil.getPingYin(postName));
                                        sysPost.setPostName(postName);
                                        sysPost.setPostSort("0");
                                        sysPost.setStatus("0");
                                        r = remotePostService.addInner(sysPost, SecurityConstants.INNER);
                                        if (r == null || r.getCode() == R.FAIL) {
                                            log.error(sysPost.getPostName() + "岗位息保存失败，原因：" + r.getMsg());
                                        }
                                    }
                                    postIds[0] = postId;
                                    sysUser.setPostIds(postIds);
                                }
                                r = remoteUserService.addInner(sysUser, SecurityConstants.INNER);
                                if (r != null) {
                                    if (r.getCode() == R.FAIL) {
                                        log.error("手机号为" + sysUser.getUserName() + "的用户信息保存失败，原因：" + r.getMsg());
                                    }
                                } else {
                                    log.error("手机号为" + sysUser.getUserName() + "的用户信息保存失败，原因：ruoyi-system服务无响应");
                                }
                            } else {
                                sysUser = loginUser.getSysUser();
                                sysUser.setDingTalkId(user.getString("userid"));
                                sysUser.setDeptId(user.getJSONArray("dept_id_list").getLong(0));
                                sysUser.setUserName(userName);
                                sysUser.setNickName(user.getString("name"));
                                sysUser.setEmail(user.getString("email"));
                                sysUser.setPhonenumber(user.getString("mobile"));
                                sysUser.setPassword(userName);
                                sysUser.setRoles(null);
                                roles = user.getJSONArray("role_list");
                                if (roles != null && roles.size() > 0) {
                                    roleSize = roles.size();
                                    roleIds = new Long[roleSize];
                                    for (int j = 0; j < roleSize; j++) {
                                        roleIds[j] = roles.getJSONObject(j).getLong("id");
                                    }
                                    sysUser.setRoleIds(roleIds);
                                }

                                if (user.containsKey("title") && user.getString("title") != null && !user.getString("title").equals("")) {
                                    postIds = new Long[1];
                                    postName = user.getString("title").replaceAll("/", " ");
                                    sysPostR = remotePostService.getInfoByNameInner(postName, SecurityConstants.INNER);
                                    if (sysPostR != null && sysPostR.getCode() == R.SUCCESS && sysPostR.getData() != null) {
                                        postId = sysPostR.getData().getPostId();
                                    } else {
                                        postId = remoteIdProducerService.nextId();
                                        sysPost = new SysPost();
                                        sysPost.setPostId(postId);
                                        sysPost.setPostCode(PinYinUtil.getPingYin(postName));
                                        sysPost.setPostName(postName);
                                        sysPost.setPostSort("0");
                                        sysPost.setStatus("0");
                                        r = remotePostService.addInner(sysPost, SecurityConstants.INNER);
                                        if (r == null || r.getCode() == R.FAIL) {
                                            log.error(sysPost.getPostName() + "岗位息保存失败，原因：" + r.getMsg());
                                        }
                                    }
                                    postIds[0] = postId;
                                    sysUser.setPostIds(postIds);
                                }
                                r = remoteUserService.editInner(sysUser, SecurityConstants.INNER);
                                if (r != null) {
                                    if (r.getCode() == R.FAIL) {
                                        log.error("手机号为" + sysUser.getUserName() + "的用户信息更新失败，原因：" + r.getMsg());
                                    }
                                } else {
                                    log.error("手机号为" + sysUser.getUserName() + "的用户信息更新失败，原因：ruoyi-system服务无响应");
                                }
                            }
                        } else {
                            log.error("手机号为" + user.getString("mobile") + "的用户信息失败，原因：ruoyi-system服务无响应");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateUserList() {
        List<SysDept> deptList;
        String token;
        String res;
        Map<String, Object> param;
        JSONObject deptUserIds;
        JSONArray userIds;
        JSONArray roles;
        int roleSize;
        JSONObject user;
        int size;
        R<LoginUser> userR;
        LoginUser loginUser;
        Long[] postIds;
        R<SysPost> sysPostR;
        SysPost sysPost;
        R r;
        SysUser sysUser;
        Long[] roleIds;
        String userName;
        String postName;
        Long postId;
        R<List<SysDept>> listR = remoteDeptService.listInner(new SysDept(), SecurityConstants.INNER);
        if (listR != null) {
            if (listR.getCode() == R.SUCCESS) {
                token = iDingTalkService.gettoken();
                deptList = listR.getData();
                for (SysDept t : deptList) {
                    param = new HashMap<>();
                    param.put("access_token", token);
                    param.put("dept_id", t.getDeptId());
                    res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/user/listid", param);
                    if (!StringUtils.isEmpty(res)) {
                        deptUserIds = JSONObject.parseObject(res);
                        if (deptUserIds.getInteger("errcode") == 0) {
                            userIds = deptUserIds.getJSONObject("result").getJSONArray("userid_list");
                            if (!userIds.isEmpty() && userIds.size() > 0) {
                                size = userIds.size();
                                for (int i = 0; i < size; i++) {
                                    param = new HashMap<>();
                                    param.put("access_token", token);
                                    param.put("userid", userIds.get(i));
                                    res = HttpUtil.post(DingTalkConfig.HOST + "/topapi/v2/user/get", param);
                                    if (!StringUtils.isEmpty(res)) {
                                        user = JSONObject.parseObject(res);
                                        if (user.getInteger("errcode") == 0) {
                                            user = user.getJSONObject("result");
                                            userName = user.getString("mobile");
                                            if (userName != null && !userName.equals("")) {
                                                userR = remoteUserService.getUserInfo(userName, SecurityConstants.INNER);
                                                if (userR != null) {
                                                    loginUser = userR.getData();
                                                    if (loginUser == null) {
                                                        sysUser = new SysUser();
                                                        sysUser.setUserId(remoteIdProducerService.nextId());
                                                        sysUser.setDingTalkId(user.getString("userid"));
                                                        sysUser.setDeptId(user.getJSONArray("dept_id_list").getLong(0));
                                                        sysUser.setUserName(userName);
                                                        sysUser.setNickName(user.getString("name"));
                                                        sysUser.setEmail(user.getString("email"));
                                                        sysUser.setPhonenumber(user.getString("mobile"));
                                                        sysUser.setSex("2");
//                                                        sysUser.setAvatar(user.getString("avatar"));
                                                        sysUser.setPassword(userName);
                                                        sysUser.setJobNumber(user.getString("job_number"));
                                                        sysUser.setStatus("0");
                                                        sysUser.setDelFlag("0");
                                                        roles = user.getJSONArray("role_list");
                                                        if (roles != null && roles.size() > 0) {
                                                            roleSize = roles.size();
                                                            roleIds = new Long[roleSize];
                                                            for (int j = 0; j < roleSize; j++) {
                                                                roleIds[j] = roles.getJSONObject(j).getLong("id");
                                                            }
                                                            sysUser.setRoleIds(roleIds);
                                                        }
                                                        if (user.containsKey("title") && user.getString("title") != null && !user.getString("title").equals("")) {
                                                            postIds = new Long[1];
                                                            postName = user.getString("title").replaceAll("/", " ");
                                                            sysPostR = remotePostService.getInfoByNameInner(postName, SecurityConstants.INNER);
                                                            if (sysPostR != null && sysPostR.getCode() == R.SUCCESS && sysPostR.getData() != null) {
                                                                postId = sysPostR.getData().getPostId();
                                                            } else {
                                                                postId = remoteIdProducerService.nextId();
                                                                sysPost = new SysPost();
                                                                sysPost.setPostId(postId);
                                                                sysPost.setPostCode(PinYinUtil.getPingYin(postName));
                                                                sysPost.setPostName(postName);
                                                                sysPost.setPostSort("0");
                                                                sysPost.setStatus("0");
                                                                r = remotePostService.addInner(sysPost, SecurityConstants.INNER);
                                                                if (r == null || r.getCode() == R.FAIL) {
                                                                    log.error(sysPost.getPostName() + "岗位息保存失败，原因：" + r.getMsg());
                                                                }
                                                            }
                                                            postIds[0] = postId;
                                                            sysUser.setPostIds(postIds);
                                                        }
                                                        r = remoteUserService.addInner(sysUser, SecurityConstants.INNER);
                                                        if (r != null) {
                                                            if (r.getCode() == R.FAIL) {
                                                                log.error("手机号为" + sysUser.getUserName() + "的用户信息保存失败，原因：" + r.getMsg());
                                                            }
                                                        } else {
                                                            log.error("手机号为" + sysUser.getUserName() + "的用户信息保存失败，原因：ruoyi-system服务无响应");
                                                        }
                                                    } else {
                                                        sysUser = loginUser.getSysUser();
                                                        sysUser.setDingTalkId(user.getString("userid"));
                                                        sysUser.setDeptId(user.getJSONArray("dept_id_list").getLong(0));
                                                        sysUser.setUserName(userName);
                                                        sysUser.setNickName(user.getString("name"));
                                                        sysUser.setEmail(user.getString("email"));
                                                        sysUser.setPhonenumber(user.getString("mobile"));
//                                                        sysUser.setAvatar(user.getString("avatar"));
                                                        sysUser.setPassword(userName);
                                                        sysUser.setJobNumber(user.getString("job_number"));
                                                        sysUser.setRoles(null);
                                                        roles = user.getJSONArray("role_list");
                                                        if (roles != null && roles.size() > 0) {
                                                            roleSize = roles.size();
                                                            roleIds = new Long[roleSize];
                                                            for (int j = 0; j < roleSize; j++) {
                                                                roleIds[j] = roles.getJSONObject(j).getLong("id");
                                                            }
                                                            sysUser.setRoleIds(roleIds);
                                                        }

                                                        if (user.containsKey("title") && user.getString("title") != null && !user.getString("title").equals("")) {
                                                            postIds = new Long[1];
                                                            postName = user.getString("title").replaceAll("/", " ");
                                                            sysPostR = remotePostService.getInfoByNameInner(postName, SecurityConstants.INNER);
                                                            if (sysPostR != null && sysPostR.getCode() == R.SUCCESS && sysPostR.getData() != null) {
                                                                postId = sysPostR.getData().getPostId();
                                                            } else {
                                                                postId = remoteIdProducerService.nextId();
                                                                sysPost = new SysPost();
                                                                sysPost.setPostId(postId);
                                                                sysPost.setPostCode(PinYinUtil.getPingYin(postName));
                                                                sysPost.setPostName(postName);
                                                                sysPost.setPostSort("0");
                                                                sysPost.setStatus("0");
                                                                r = remotePostService.addInner(sysPost, SecurityConstants.INNER);
                                                                if (r == null || r.getCode() == R.FAIL) {
                                                                    log.error(sysPost.getPostName() + "岗位息保存失败，原因：" + r.getMsg());
                                                                }
                                                            }
                                                            postIds[0] = postId;
                                                            sysUser.setPostIds(postIds);
                                                        }
                                                        r = remoteUserService.editInner(sysUser, SecurityConstants.INNER);
                                                        if (r != null) {
                                                            if (r.getCode() == R.FAIL) {
                                                                log.error("手机号为" + sysUser.getUserName() + "的用户信息更新失败，原因：" + r.getMsg());
                                                            }
                                                        } else {
                                                            log.error("手机号为" + sysUser.getUserName() + "的用户信息更新失败，原因：ruoyi-system服务无响应");
                                                        }
                                                    }
                                                } else {
                                                    log.error("手机号为" + user.getString("mobile") + "的用户信息失败，原因：ruoyi-system服务无响应");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                log.error("批量更新用户信息失败，原因：" + listR.getMsg());
            }
        } else {
            log.error("批量更新用户信息失败，原因：ruoyi-system服务无响应");
        }
    }
}
