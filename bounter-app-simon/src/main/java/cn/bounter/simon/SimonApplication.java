package cn.bounter.simon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("cn.bounter.simon.dao")
public class SimonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimonApplication.class, args);
    }
}
