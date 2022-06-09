package com.pingok.event;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 事件服务
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.event.mapper")
@SpringBootApplication
public class EventApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EventApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  事件服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
