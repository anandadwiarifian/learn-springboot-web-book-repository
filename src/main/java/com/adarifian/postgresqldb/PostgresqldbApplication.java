package com.adarifian.postgresqldb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import lombok.extern.java.Log;

@SpringBootApplication
@Log
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class PostgresqldbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostgresqldbApplication.class, args);
	}

}
