package com.pingok.algorithm;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 算法业务模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.algorithm.**.mapper")
@SpringBootApplication
public class AlgorithmApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(AlgorithmApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  算法业务模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
