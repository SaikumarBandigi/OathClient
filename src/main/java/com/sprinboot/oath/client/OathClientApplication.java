package com.sprinboot.oath.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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

/*

Let's trace your exact flow.

1. User opens
http://localhost:8082/ui/
Client application shows:
<a href="secure">Login to OAuth here</a>

2. User clicks Secure Page
http://localhost:8082/ui/secure
Spring Security checks:
.anyRequest().authenticated();
User is not authenticated.
So @EnableOAuth2Sso starts OAuth flow.


3. Spring redirects to Authorization Endpoint
Using:
userAuthorizationUri:
  http://localhost:8081/auth/oauth/authorize
Browser gets redirected to:
http://localhost:8081/auth/oauth/authorize
    ?client_id=ClientId
    &response_type=code
    &redirect_uri=http://localhost:8082/ui/login
You never call this URL manually.
Spring generates it automatically.

4. Authorization Server checks login
Because of:
.formLogin()
.permitAll();
User is redirected to:
http://localhost:8081/auth/login
and enters:
username = ramu
password = ramu


5. Authorization Code generated
After successful login:
http://localhost:8082/ui/login?code=abc123

The Authorization Server generated:
code=abc123
and sent it back to the client.


6. Spring internally calls Token Endpoint
Using:
accessTokenUri:
  http://localhost:8081/auth/oauth/token

Spring sends a POST request behind the scenes:
POST /auth/oauth/token

client_id=ClientId
client_secret=secret
grant_type=authorization_code
code=abc123

Browser never sees this request.
It is server-to-server communication.

Authorization Server returns:

{
  "access_token":"xyz123",
  "token_type":"bearer"
}


7. Spring internally calls UserInfo Endpoint
Using:

userInfoUri:
  http://localhost:8081/auth/rest/hello/principal

Spring now sends:

GET /auth/rest/hello/principal

Authorization: Bearer xyz123

Again:

Browser does NOT call it.
Spring Security calls it internally.

Your controller executes:

@GetMapping("/principal")
public Principal user(Principal principal) {
    return principal;
}

and returns something like:

{
  "authorities":[
      {
         "authority":"ROLE_USER"
      }
   ],
   "details":{},
   "authenticated":true,
   "name":"ramu"
}


8. Spring creates Authentication object
From the user info response Spring creates:
Authentication authentication

Now:

#authentication.name

contains:

ramu

Therefore Thymeleaf renders:

Welcome to Oath Example!

ramu

 */