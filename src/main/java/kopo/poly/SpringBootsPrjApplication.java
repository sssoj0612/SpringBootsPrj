package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootsPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootsPrjApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){return  new RestTemplate();}
}
