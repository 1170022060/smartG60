package com.pingok.external.service.dingTalk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface IDingTalkService {

    /**
     * 更新部门信息
     */
    void orgDeptRemove(JSONArray deptIds);

    /**
     * 更新部门信息
     */
    void orgDeptModify(JSONArray deptIds);

    /**
     * 新增部门信息
     */
    void orgDeptCreate(JSONArray deptIds);
    /**
     * 获取token

    /**
     * 更新部门列表
     */
    void updateDepartmentList(Long deptId);

}
