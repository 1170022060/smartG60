package com.pingok.internetAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * 因特网认证授权中心
 *
 * @author ruoyi
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class InternetAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(InternetAuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  因特网认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
