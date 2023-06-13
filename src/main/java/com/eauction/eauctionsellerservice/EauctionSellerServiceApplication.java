package com.eauction.eauctionsellerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EauctionSellerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EauctionSellerServiceApplication.class, args);
	}

}
