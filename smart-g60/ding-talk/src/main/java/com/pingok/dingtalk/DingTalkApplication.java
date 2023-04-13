package com.pingok.dingtalk;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.**.mapper")
@SpringBootApplication
public class DingTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingTalkApplication.class, args);
    }

}
