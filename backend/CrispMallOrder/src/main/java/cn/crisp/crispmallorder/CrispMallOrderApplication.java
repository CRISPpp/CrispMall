package cn.crisp.crispmallorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RefreshScope
@EnableFeignClients("cn.crisp.crispmallorder.feign")
@EnableDiscoveryClient
@SpringBootApplication()
public class CrispMallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrispMallOrderApplication.class, args);
    }

}
