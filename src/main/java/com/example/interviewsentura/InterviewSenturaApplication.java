package com.example.interviewsentura;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InterviewSenturaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewSenturaApplication.class, args);
    }

    @Bean
    OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

}
