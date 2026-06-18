package com.sprinboot.oath.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// the client

@SpringBootApplication
public class OathClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OathClientApplication.class, args);
	}

}

/*

the flow is
http://localhost:8082/ui/

then after clicking it Login to OAuth here it takes to
http://localhost:8081/auth/login

after verifying

http://localhost:8082/ui/secure opens with succes


 */
