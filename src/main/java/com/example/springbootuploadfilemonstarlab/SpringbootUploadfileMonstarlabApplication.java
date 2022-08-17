package com.example.springbootuploadfilemonstarlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@RestController
public class SpringbootUploadfileMonstarlabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUploadfileMonstarlabApplication.class, args);
    }
}
