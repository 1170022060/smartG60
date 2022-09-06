package com.pingok.kafka;

import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Kafka模块
 * 
 * @author 邱敏
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class KafkaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(KafkaApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Kafka模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
