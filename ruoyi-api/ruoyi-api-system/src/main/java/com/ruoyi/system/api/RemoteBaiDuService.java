package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.baidu.TblBaiDuMapRecord;
import com.ruoyi.system.api.factory.RemoteBaiDuFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 百度服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteBaiDuService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteBaiDuFallbackFactory.class)
public interface RemoteBaiDuService {


    @PostMapping("/baidu")
    R eventPublish(@RequestBody TblBaiDuMapRecord tblBaiDuMapRecord);

    @PutMapping("/baidu")
    R eventRelieve(@RequestParam(value = "id") Long id);
}
