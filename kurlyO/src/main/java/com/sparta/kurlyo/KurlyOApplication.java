package com.sparta.kurlyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KurlyOApplication {

    public static void main(String[] args) {
        SpringApplication.run(KurlyOApplication.class, args);
    }

}
