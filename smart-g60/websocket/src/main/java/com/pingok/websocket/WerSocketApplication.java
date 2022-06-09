package com.pingok.websocket;

import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * webSocket模块
 * 
 * @author 邱敏
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WerSocketApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WerSocketApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  webSocket模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
