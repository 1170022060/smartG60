package com.pingok.api;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 英特网接口服务
 *
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.api.mapper")
@SpringBootApplication
public class InternetApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternetApiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 英特网接口服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
