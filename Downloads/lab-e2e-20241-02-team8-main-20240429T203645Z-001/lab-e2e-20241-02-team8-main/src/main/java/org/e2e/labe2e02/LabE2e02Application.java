package org.e2e.labe2e02;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LabE2e02Application {

    public static void main(String[] args) {
        SpringApplication.run(LabE2e02Application.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}
}
