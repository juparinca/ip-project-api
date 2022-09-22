package com.ip.project.api.config;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@Configuration
@EnableSwagger2
public class AppConfig {

	@Value("${aws.region}")
	private String awsRegion;

	@Bean
	public AWSSimpleSystemsManagement ssmManager() throws IOException {
		return AWSSimpleSystemsManagementClientBuilder.standard()
				.withRegion(awsRegion)
				.build();
	}

}
