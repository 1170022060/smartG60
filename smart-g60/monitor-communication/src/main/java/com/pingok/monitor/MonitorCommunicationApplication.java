package com.pingok.monitor;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 监控网通讯模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.monitor.mapper")
@SpringBootApplication
public class MonitorCommunicationApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitorCommunicationApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  监控网通讯服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
