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

1) Open
http://localhost:8082/ui

3) Click "Login to OAuth here"
<a href="secure">Login to OAuth here</a>

4) /secure requires authentication because:
.anyRequest().authenticated();

5) @EnableOAuth2Sso starts OAuth flow and redirects to:
http://localhost:8081/auth/oauth/authorize

6) User is not logged into Authorization Server.

7) Spring Security intercepts /oauth/authorize and redirects to its default login page:
http://localhost:8081/auth/login

8) You enter:
Username: ramu
Password: ramu
which comes from:
.inMemoryAuthentication()
    .withUser("ramu")
    .password("ramu")
    .roles("USER");

9) After successful login, Spring Security sends you back to:
/auth/oauth/authorize

10) Authorization code is generated and sent to the client.

11) Client exchanges the code for an access token using:
accessTokenUri:
  http://localhost:8081/auth/oauth/token

12) Client fetches user details from:
userInfoUri:
  http://localhost:8081/auth/rest/hello/principal

Finally you reach:
http://localhost:8082/ui/secure
 */