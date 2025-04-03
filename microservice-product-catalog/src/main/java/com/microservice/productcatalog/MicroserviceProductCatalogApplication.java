package com.microservice.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching descomentar cuando funcione redis
public class MicroserviceProductCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProductCatalogApplication.class, args);
	}

}
