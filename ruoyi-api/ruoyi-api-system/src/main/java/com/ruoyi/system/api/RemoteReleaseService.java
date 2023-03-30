package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.release.TblReleasePreset;
import com.ruoyi.system.api.domain.release.TblReleaseRecord;
import com.ruoyi.system.api.factory.RemoteReleaseFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 信息发布记录服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteReleaseService", value = ServiceNameConstants.VOCATIONAL, fallbackFactory = RemoteReleaseFallbackFactory.class)
public interface RemoteReleaseService {

    @PostMapping("/releaseRecord")
    R add(@RequestBody TblReleaseRecord tblReleaseRecord);

    @GetMapping("/releasePreset/idInfo")
    R<TblReleasePreset> idInfo(@RequestParam(name = "id") Long id);

}
