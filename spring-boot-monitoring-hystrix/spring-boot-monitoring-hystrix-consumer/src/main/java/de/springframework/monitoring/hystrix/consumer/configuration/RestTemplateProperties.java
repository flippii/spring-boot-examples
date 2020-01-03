package de.springframework.monitoring.hystrix.consumer.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Setter
@ConfigurationProperties(prefix = "rest.template", ignoreUnknownFields = false)
public class RestTemplateProperties {

    private long connectTimeout = 2;
    private long readTimeout = 1;

    public Duration getConnectTimeout() {
        return Duration.ofSeconds(connectTimeout);
    }

    public Duration getReadTimeout() {
        return Duration.ofSeconds(readTimeout);
    }

}
