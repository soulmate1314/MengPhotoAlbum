package com.yansir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@org.mybatis.spring.annotation.MapperScan("com.yansir.po.*.mapper")
public class MengPhotoAlbumApplication {
    public static void main(String[] args) {
        System.out.println("The service to start.");
        ConfigurableApplicationContext run = SpringApplication.run(MengPhotoAlbumApplication.class, args);
        System.out.println(run);
        System.out.println("The service has started.");
    }
}
