package com.ddfoever.entity;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IocConfig {

    @Bean(name="user")
    public User getUser(){
        return new User();
    }
}
