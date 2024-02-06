package com.demo.react;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.react")
@EnableR2dbcRepositories
public class ReactDemoSb3Application {

	public static void main(String[] args) {
		SpringApplication.run(ReactDemoSb3Application.class, args);
	}

//	R2DBC Doesn't Automatically Create Tables: Unlike JPA's Hibernate, R2DBC doesn't create tables by default from entity classes.
	//Manual Table Creation: You'll need to explicitly create tables for your entities for h2 database
	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
		return initializer;
	}
}
