package com.phoenix.blog;

import com.phoenix.blog.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

}
