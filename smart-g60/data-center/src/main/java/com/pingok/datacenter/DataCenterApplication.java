package com.pingok.datacenter;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 数据中心模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.datacenter.mapper")
@SpringBootApplication
public class DataCenterApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DataCenterApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  数据中心模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
