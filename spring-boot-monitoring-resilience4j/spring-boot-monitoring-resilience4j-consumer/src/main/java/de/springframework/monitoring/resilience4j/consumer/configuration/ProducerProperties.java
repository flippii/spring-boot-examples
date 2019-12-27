package de.springframework.monitoring.resilience4j.consumer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "producer.service", ignoreUnknownFields = false)
public class ProducerProperties {

    private String url;
    private String path;

}
