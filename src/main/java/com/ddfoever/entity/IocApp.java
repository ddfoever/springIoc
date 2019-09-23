package com.ddfoever.entity;

import com.ddfoever.ioc.ApplicatonContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocApp {
    public static void main(String[] args) {
        //使用xml
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
//        User u = applicationContext.getBean("user",User.class);
//        System.out.println(u.toString());

        //使用注解的形式
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IocConfig.class);
//        User u = applicationContext.getBean("user",User.class);
//        System.out.println(u.toString());

          //自定义 IOC 容器
        ApplicatonContext applicatonContext = new com.ddfoever.ioc.ClassPathXmlApplicationContext("spring.xml");

        User user = (User) applicatonContext.getBean("user");
        System.out.println(user);
    }
}
