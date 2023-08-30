package com.github.lmoraes7.tcc.uva.recruitment.selection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ConfigurationPropertiesScan("com.github.lmoraes7.tcc.uva.recruitment.selection")
@EnableAsync
public class RecruitmentSelectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentSelectionApplication.class, args);
	}

}
