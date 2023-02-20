package com.pingok.external.service.log.impl;

import com.pingok.external.mapper.log.InternetLogininforMapper;
import com.pingok.external.service.log.IInternetLogininforService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class InternetLogininforServiceImpl implements IInternetLogininforService {

    @Autowired
    private InternetLogininforMapper logininforMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public int insertLogininfor(InternetLogininfor logininfor) {
        logininfor.setInfoId(remoteIdProducerService.nextId());
        logininfor.setCreateTime(DateUtils.getNowDate());
        logininfor.setAccessTime(DateUtils.getNowDate());
        logininfor.setCreateBy(SecurityUtils.getUsername());
        return logininforMapper.insert(logininfor);
    }

}
