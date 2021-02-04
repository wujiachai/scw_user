package com.offcn.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//不启用datasoures
@EnableDiscoveryClient
@EnableFeignClients //开启Feign的注解
@EnableCircuitBreaker //熔断
public class WebuiStart {
    public static void main(String[] args) {
        SpringApplication.run(WebuiStart.class);
    }
}
