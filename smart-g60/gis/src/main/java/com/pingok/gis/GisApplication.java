package com.pingok.gis;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * GIS模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.gis.mapper")
@SpringBootApplication
public class GisApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GisApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  GIS模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
