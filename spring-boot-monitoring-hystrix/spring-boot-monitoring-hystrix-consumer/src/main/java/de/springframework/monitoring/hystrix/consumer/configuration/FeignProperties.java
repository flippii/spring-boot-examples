package de.springframework.monitoring.hystrix.consumer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static java.util.concurrent.TimeUnit.SECONDS;

@Setter
@ConfigurationProperties(prefix = "client.feign", ignoreUnknownFields = false)
public class FeignProperties {

    @Getter
    private long period;

    private long maxPeriod;

    @Getter
    private int maxAttempts;

    public long getMaxPeriod() {
        return  SECONDS.toMillis(maxPeriod);
    }

}
