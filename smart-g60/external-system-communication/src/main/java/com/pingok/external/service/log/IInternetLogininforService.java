package com.pingok.external.service.log;

import com.ruoyi.system.api.domain.internet.InternetLogininfor;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author ruoyi
 */
public interface IInternetLogininforService {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public int insertLogininfor(InternetLogininfor logininfor);

}
