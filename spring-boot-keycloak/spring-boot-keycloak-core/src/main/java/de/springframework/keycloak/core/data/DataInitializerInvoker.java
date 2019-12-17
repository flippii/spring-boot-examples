package de.springframework.keycloak.core.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

@RequiredArgsConstructor
public class DataInitializerInvoker implements ApplicationRunner {

    private final List<DataInitializer> initializers;

    @Override
    public void run(ApplicationArguments args) {
        initializers.forEach(DataInitializer::initialize);
    }

}
