package com.hejingwei.jwojbackendjudgeservice;

import com.hejingwei.jwojbackendjudgeservice.judge.InitRabbitMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.hejingwei")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.hejingwei.jwojbackendserviceclient.service"})
public class JwojBackendJudgeServiceApplication {

	public static void main(String[] args) {
		InitRabbitMq.doInit();
		SpringApplication.run(JwojBackendJudgeServiceApplication.class, args);
	}

}
