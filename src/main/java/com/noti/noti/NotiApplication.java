package com.noti.noti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NotiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotiApplication.class, args);

	}

}
