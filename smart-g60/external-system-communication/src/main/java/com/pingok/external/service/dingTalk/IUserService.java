package com.pingok.external.service.dingTalk;

import com.alibaba.fastjson.JSONArray;

public interface IUserService {


    /**
     * 用户离职删除用户
     * @param userIds
     */
    void delete(JSONArray userIds);
    /**
     * 新增或更新用户
     */
    void saveOrUpdate(JSONArray userIds);
    /**
     * 更新用户列表
     */
    void updateUserList();
}
