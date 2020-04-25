package com.pc;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pc.stock.rest.client.AlphaVantageClient;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories
public class StockRecorderApplication {
	
	private static final String REST_URL = "https://gturnquist-quoters.cfapps.io/api/random";

	public static void main(String[] args) {
		SpringApplication.run(StockRecorderApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
