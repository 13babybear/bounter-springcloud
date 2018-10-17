package cn.bounter.susan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.bounter.susan.dao")
public class SusanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SusanApplication.class, args);
    }
}
