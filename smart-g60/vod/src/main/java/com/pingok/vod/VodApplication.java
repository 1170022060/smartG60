package com.pingok.vod;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 视频模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.vod.mapper")
@SpringBootApplication
@EnableScheduling
public class VodApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(VodApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  视频模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
