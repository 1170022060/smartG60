package com.pingok.algorithmBeiJing;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 北京算法模块
 *
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.algorithmBeiJing.mapper")
@SpringBootApplication
public class AlgorithmBeiJingApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(AlgorithmBeiJingApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  北京算法服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}