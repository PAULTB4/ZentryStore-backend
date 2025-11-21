package com.zentry.zentrystore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class ZentryStoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZentryStoreBackendApplication.class, args);
    }

}
