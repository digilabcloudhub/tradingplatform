package com.trading.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.trading.platform")
public class TradingplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingplatformApplication.class, args);
	}

}
