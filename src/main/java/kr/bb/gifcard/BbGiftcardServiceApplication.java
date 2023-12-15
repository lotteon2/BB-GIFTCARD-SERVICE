package kr.bb.gifcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BbGiftcardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbGiftcardServiceApplication.class, args);
    }

}
