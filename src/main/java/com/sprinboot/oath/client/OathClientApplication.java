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

/*
Complete Flow

Open
http://localhost:8082/ui

Click "Login to OAuth here"
<a href="secure">Login to OAuth here</a>

/secure requires authentication because:
.anyRequest().authenticated();

@EnableOAuth2Sso starts OAuth flow and redirects to:
http://localhost:8081/auth/oauth/authorize

User is not logged into Authorization Server.

Spring Security intercepts /oauth/authorize and redirects to its default login page:
http://localhost:8081/auth/login

You enter:
Username: ramu
Password: ramu
which comes from:
.inMemoryAuthentication()
    .withUser("ramu")
    .password("ramu")
    .roles("USER");

After successful login, Spring Security sends you back to:
/auth/oauth/authorize

Authorization code is generated and sent to the client.

Client exchanges the code for an access token using:
accessTokenUri:
  http://localhost:8081/auth/oauth/token

Client fetches user details from:
userInfoUri:
  http://localhost:8081/auth/rest/hello/principal

Finally you reach:
http://localhost:8082/ui/secure
 */