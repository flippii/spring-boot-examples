package de.springframework.monitoring.hystrix.producer.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.hystrix.HystrixMetricsBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MicrometerConfiguration {

    private final BuildProperties buildProperties;

    @Bean
    public HystrixMetricsBinder hystrixMetrics(){
        return new HystrixMetricsBinder();
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
            @Value("${spring.application.name}") String applicationName) {

        return registry -> {
            registry.config().commonTags(
                    "application", applicationName,
                    "build.group", buildProperties.getGroup(),
                    "build.artifact", buildProperties.getArtifact(),
                    "build.version", buildProperties.getVersion());
        };
    }

}
