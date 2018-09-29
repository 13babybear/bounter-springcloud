package cn.bounter.simon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SimonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimonApplication.class, args);
    }
}
