package com.hejingwei.jwojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.hejingwei.jwojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.hejingwei")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.hejingwei.jwojbackendserviceclient.service"})
public class JwojBackendQuestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwojBackendQuestionServiceApplication.class, args);
	}

}
