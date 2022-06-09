package com.pingok.devicemonitor;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 设备监控模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.devicemonitor.mapper")
@SpringBootApplication
public class DeviceMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeviceMonitorApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  设备监控启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
