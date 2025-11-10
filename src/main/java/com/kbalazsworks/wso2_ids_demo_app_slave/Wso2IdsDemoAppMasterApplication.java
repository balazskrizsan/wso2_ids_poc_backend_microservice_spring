package com.kbalazsworks.wso2_ids_demo_app_slave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class Wso2IdsDemoAppMasterApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(Wso2IdsDemoAppMasterApplication.class, args);
    }
}
