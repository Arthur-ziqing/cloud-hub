package com.arthur.cloud.mc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootApplication
@ComponentScan(basePackages = "com.arthur.cloud",excludeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ,pattern = "com.arthur..**.handler.*")})
public class Appliction {

    public static void main(String[] args) {
        // -Dspring.profiles.active=dev
        SpringApplication.run(Appliction.class,args);
    }
}
