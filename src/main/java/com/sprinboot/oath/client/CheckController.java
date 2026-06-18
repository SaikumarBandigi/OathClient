//package com.sprinboot.oath.client;
//
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//public class CheckController {
//
//    @GetMapping("/test")
//    public String test(Authentication auth) {
//
//        System.out.println(auth.getClass());
//
//        OAuth2Authentication oauth = (OAuth2Authentication) auth;
//
//        System.out.println(oauth.getPrincipal().getClass());
//
//        return "ok";
//    }
//
//}
