package com.holzhausen.MedHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class MedHelperApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MedHelperApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("agda"));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MedHelperApplication.class);
	}

}
