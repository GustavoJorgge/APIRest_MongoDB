package com.example.tabelaFipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableMongoRepositories("com.example.tabelaFipe.repository")
public class TabelaFipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

}
