package com.kbalazsworks.wso2_ids_poc_backend_microservice_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class Wso2IdsPocBackendMicroserviceSpringApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(Wso2IdsPocBackendMicroserviceSpringApplication.class, args);
    }
}
