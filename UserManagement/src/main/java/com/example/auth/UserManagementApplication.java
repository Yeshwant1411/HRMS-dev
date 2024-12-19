package com.example.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@Profile("dev")
public class UserManagementApplication {
	   @Value("${spring.application.name}")
	   private String name;
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}
	   @RequestMapping(value = "/")
	   public String name() {
	      return name;
	   }
}
