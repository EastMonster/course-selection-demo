package io.github.eastmonster.dbsim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
@MapperScan("io.github.eastmonster.dbsim.mapper")
public class CourseSelectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseSelectionApplication.class, args);
    }
}
