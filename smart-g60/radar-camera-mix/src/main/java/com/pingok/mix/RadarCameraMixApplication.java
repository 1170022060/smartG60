package com.pingok.mix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 监控网通讯模块
 * 
 * @author pingOk
 */
@MapperScan(basePackages = "com.pingok.mix.mapper")
@SpringBootApplication
public class RadarCameraMixApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RadarCameraMixApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  雷视融合通讯服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
