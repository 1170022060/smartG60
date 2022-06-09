package com.pingok.vocational;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 基础业务模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.vocational.mapper")
@SpringBootApplication
public class VocationalApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(VocationalApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  基础业务模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
