package com.nns.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MyBlogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBlogApiApplication.class, args);
	}


}
