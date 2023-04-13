package com.pingok.dingtalk.feign;

import com.pingok.dingtalk.daemon.base.DeviceInfo;
import com.pingok.dingtalk.daemon.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "base-feign", url = "${ding-talk.base-url}/luqiao/")
public interface BaseFeign {
    @GetMapping("/bas-facilities-info/page")
    Result<DeviceInfo> deviceInfoPage();
}
