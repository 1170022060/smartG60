package com.pingok.external.service.dingTalk;

import com.alibaba.fastjson.JSONArray;

public interface IRoleService {

    /**
     * 删除角色
     * @param labelIds
     */
    void labelConfDel(JSONArray labelIds);

    /**
     * 编辑角色
     * @param labels
     */
    void labelConfModify(JSONArray labels);

    /**
     * 增加角色
     * @param labelIds
     */
    void labelConfAdd(JSONArray labelIds);

    /**
     *更新角色列表
     */
    void updateRoleList();
}
