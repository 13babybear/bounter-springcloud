package cn.bounter.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.bounter.oauth2.dao")
public class BounterOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(BounterOauth2Application.class, args);
    }
}
