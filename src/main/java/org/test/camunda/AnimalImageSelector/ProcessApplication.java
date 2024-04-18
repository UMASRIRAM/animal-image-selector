package org.test.camunda.AnimalImageSelector;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = "classpath:bpmn/retrieve-image.bpmn")
public class ProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(
                List.of(new BufferedImageHttpMessageConverter()));
        return restTemplate;
    }
}
