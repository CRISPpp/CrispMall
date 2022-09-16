package cn.crisp.crispmallproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CrispMallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrispMallProductApplication.class, args);
    }

}
