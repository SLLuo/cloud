package com.newtouch.sysmgr;

import com.newtouch.sysmgr.config.DruidConfiguration;
import com.newtouch.sysmgr.config.MyBaitsConfiguration;
import com.newtouch.sysmgr.config.ShiroConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
@Import({DruidConfiguration.class, MyBaitsConfiguration.class, ShiroConfiguration.class})
public class WebAppServer {

    public static void main(String[] args) {
        SpringApplication.run(WebAppServer.class, args);
    }

}
