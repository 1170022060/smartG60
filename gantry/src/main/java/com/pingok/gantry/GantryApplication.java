package com.pingok.gantry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 门架主动采集模块
 *
 * @author pingok
 */
@MapperScan("com.pingok.gantry.mapper.*")
@SpringBootApplication
public class GantryApplication {
    public static void main(String[] args) {
        SpringApplication.run(GantryApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  门架主动采集模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}