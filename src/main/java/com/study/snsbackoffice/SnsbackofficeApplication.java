package com.study.snsbackoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SnsbackofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsbackofficeApplication.class, args);
	}

}
