package com.pingok.external;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 第三方系统通讯模块
 *
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.external.mapper")
@SpringBootApplication
public class ExernalSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExernalSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  第三方系统通讯服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
