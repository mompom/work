package net.ib.paperless.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = { DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableAutoConfiguration
public class PaperlessApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaperlessApplication.class, args);
	}
}

