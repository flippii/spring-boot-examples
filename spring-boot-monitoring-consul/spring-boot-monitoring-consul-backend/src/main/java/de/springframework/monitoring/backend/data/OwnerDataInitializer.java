package de.springframework.monitoring.backend.data;

import de.springframework.monitoring.backend.entity.Owner;
import de.springframework.monitoring.backend.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class OwnerDataInitializer implements DataInitializer {

    private final OwnerRepository ownerRepository;

    @Override
    public void initialize() {
        if (ownerRepository.count() > 0) {
            return;
        }

        log.info("Creating default owner entries.");

        save(new Owner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"));
        save(new Owner("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749"));
        save(new Owner("Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763"));
        save(new Owner("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198"));
        save(new Owner("Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765"));
        save(new Owner("Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654"));
        save(new Owner("Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387"));
        save(new Owner("Maria", "Escobito", "345 Maple St.", "Madison", "6085557683"));
        save(new Owner("David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435"));
        save(new Owner("Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487"));
    }

    private void save(Owner owner) {
        ownerRepository.save(owner);
    }

}
