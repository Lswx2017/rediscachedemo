package com.jn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RediscachedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RediscachedemoApplication.class, args);
    }

}
