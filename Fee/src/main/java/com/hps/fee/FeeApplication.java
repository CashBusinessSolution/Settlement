package com.hps.fee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hps")
public class FeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeeApplication.class, args);
    }

}
