package uk.mushow.risk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MedilaboApplication {

    @Value("${baseUrl")
    private static String baseUrl;

    public static void main(String[] args) {
        System.out.println(baseUrl);
        SpringApplication.run(MedilaboApplication.class, args);
    }

}
