package com.acme.bank.demo;

import com.acme.bank.demo.config.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class AcmeBankDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(AcmeBankDemoApplication.class);

	private final Environment env;

    public AcmeBankDemoApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AcmeBankDemoApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
		String applicationName = env.getProperty("spring.application.name");
		String serverPort = env.getProperty("server.port");
		String contextPath = Optional
				.ofNullable(env.getProperty("server.servlet.context-path"))
				.filter(StringUtils::isNotBlank)
				.orElse("/");
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"""
    
                ----------------------------------------------------------
                \tApplication '{}' is running! Access URLs:
                \tLocal: \t\t{}://localhost:{}{}
                \tExternal: \t{}://{}:{}{}
                \tProfile(s): \t{}
                ----------------------------------------------------------""",
				applicationName,
				protocol,
				serverPort,
				contextPath,
				protocol,
				hostAddress,
				serverPort,
				contextPath,
				env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
		);
	}

}
