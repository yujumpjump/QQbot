package com.jumpjump;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSimbot
public class MiraiApplication {
    public static void main(String[] args) {
         SpringApplication.run(MiraiApplication.class, args);
    }

}
