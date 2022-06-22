package com.nttdata.bc19.msmanagementfixedtermaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsManagementFixedTermAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsManagementFixedTermAccountApplication.class, args);
	}

}
