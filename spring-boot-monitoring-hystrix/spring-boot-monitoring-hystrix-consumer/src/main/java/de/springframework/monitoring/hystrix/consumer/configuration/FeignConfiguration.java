package de.springframework.monitoring.hystrix.consumer.configuration;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.hystrix.HystrixFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FeignConfiguration {

    private final FeignProperties feignProperties;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(feignProperties.getPeriod(), feignProperties.getMaxPeriod(), feignProperties.getMaxAttempts());
    }

    @Bean
    public Feign.Builder feignHystrixBuilder() {
        return HystrixFeign.builder().setterFactory((target, method) -> {
            String groupKey = target.type().getSimpleName();
            String commandKey = groupKey + "#" + method.getName();

            return HystrixCommand.Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(groupKey));
        });
    }

}
