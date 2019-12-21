package de.springframework.monitoring.backend.data;

import de.springframework.monitoring.backend.entity.Speciality;
import de.springframework.monitoring.backend.entity.Vet;
import de.springframework.monitoring.backend.repository.SpecialityRepository;
import de.springframework.monitoring.backend.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class VetDataInitializer implements DataInitializer {

    private final VetRepository vetRepository;
    private final SpecialityRepository specialityRepository;

    @Override
    public void initialize() {
        if (vetRepository.count() > 0) {
            return;
        }

        log.info("Creating default vet and speciality entries.");

        Speciality radiology = findOrSave("radiology");
        Speciality surgery = findOrSave("surgery");
        Speciality dentistry = findOrSave("dentistry");

        new HashSet<Speciality>() {{
            add(surgery);
            add(dentistry);
        }};

        save(new Vet("James", "Carter"));
        save(new Vet("Helen", "Leary", Collections.singleton(radiology)));
        save(new Vet("Linda", "Douglas", new HashSet<Speciality>() {{
            add(surgery);
            add(dentistry);
        }}));
        save(new Vet("Rafael", "Ortega", Collections.singleton(surgery)));
        save(new Vet("Henry", "Stevens", Collections.singleton(radiology)));
        save(new Vet("Sharon", "Jenkins"));
    }

    private Speciality findOrSave(String name) {
        return specialityRepository.findByName(name)
                .orElseGet(() -> specialityRepository.save(new Speciality(name)));
    }

    private void save(Vet vet) {
        vetRepository.save(vet);
    }

}
