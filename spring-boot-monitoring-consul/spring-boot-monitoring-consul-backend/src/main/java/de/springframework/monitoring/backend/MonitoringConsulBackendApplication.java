package de.springframework.monitoring.backend;

import de.springframework.monitoring.backend.configuration.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import static de.springframework.monitoring.backend.configuration.ApplicationProfiles.*;
import static java.util.Arrays.asList;

@Slf4j
@RequiredArgsConstructor
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class MonitoringConsulBackendApplication implements InitializingBean {

    private final Environment env;

    @Override
    public void afterPropertiesSet() {
        Collection<String> activeProfiles = asList(env.getActiveProfiles());

        if (activeProfiles.contains(DEVELOPMENT) && activeProfiles.contains(PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run with both the " +
                    "'{}' and '{}' profiles at the same time.", DEVELOPMENT, PRODUCTION);
        }
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MonitoringConsulBackendApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";

        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");

        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }

        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }

}
