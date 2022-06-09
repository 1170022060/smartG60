package com.pingok.external.service.dingTalk;

public interface IDingTalkService {
    /**
     * 获取token
     */
    void gettoken();

    /**
     * 更新部门列表
     */
    void updateDepartmentList(Long deptId);
}
