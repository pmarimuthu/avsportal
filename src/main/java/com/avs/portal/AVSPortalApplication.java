package com.avs.portal;

import java.sql.Timestamp;
import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AVSPortalApplication {

	public static void main(String... args) {
		System.out.println(ZoneId.systemDefault() + "\n" +	new Timestamp(System.currentTimeMillis()));
		SpringApplication.run(AVSPortalApplication.class, args);
	}
}
