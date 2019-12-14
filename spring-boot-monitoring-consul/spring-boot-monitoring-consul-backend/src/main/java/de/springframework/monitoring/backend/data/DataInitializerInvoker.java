package de.springframework.monitoring.backend.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.springframework.monitoring.backend.configuration.ApplicationProfiles.DEVELOPMENT;

@Profile(DEVELOPMENT)
@Component
@RequiredArgsConstructor
public class DataInitializerInvoker implements ApplicationRunner {

    private final List<DataInitializer> initializers;

    @Override
    public void run(ApplicationArguments args) {
        initializers.forEach(DataInitializer::initialize);
    }

    @Component
    static class NoOpDataInitializer implements DataInitializer {

        @Override
        public void initialize() {}

    }

}
