package cn.crisp.crispmalluser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
@EnableDiscoveryClient
public class CrispMallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrispMallUserApplication.class, args);
    }

}
