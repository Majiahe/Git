package cn.qnm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.qnm.modules.*.dao")
class QnmApplication {

    public static void main(String[] args) {

        SpringApplication.run(QnmApplication.class, args);

    }

}
