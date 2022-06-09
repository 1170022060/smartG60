package com.pingok.idproducer;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 唯一id模块
 * 
 * @author pingOk
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@MapperScan(basePackages = "com.pingok.idproducer.mapper")
@SpringBootApplication
public class IdProducerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(IdProducerApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  唯一id模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
