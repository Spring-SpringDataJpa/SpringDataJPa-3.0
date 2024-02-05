package com.demo.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.react")
@EnableR2dbcRepositories
public class ReactDemoSb3Application {

	public static void main(String[] args) {
		SpringApplication.run(ReactDemoSb3Application.class, args);
	}

}
