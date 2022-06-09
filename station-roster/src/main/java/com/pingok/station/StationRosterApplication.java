package com.pingok.station;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 收费网状态名单模块
 *
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.station.mapper")
@SpringBootApplication
public class StationRosterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StationRosterApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  收费站状态名单服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
