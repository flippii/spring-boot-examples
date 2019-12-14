package de.springframework.monitoring.backend.data;

import de.springframework.monitoring.backend.entity.Owner;
import de.springframework.monitoring.backend.entity.Pet;
import de.springframework.monitoring.backend.entity.PetType;
import de.springframework.monitoring.backend.entity.Visit;
import de.springframework.monitoring.backend.repository.OwnerRepository;
import de.springframework.monitoring.backend.repository.PetRepository;
import de.springframework.monitoring.backend.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Order(3)
@Component
@RequiredArgsConstructor
public class PetDataInitializer implements DataInitializer {

    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public void initialize() {
        if (petRepository.count() > 0) {
            return;
        }

        log.info("Creating default petType, pet, and visit entries.");

        PetType cat = findOrSave("cat");
        PetType dog = findOrSave("dog");
        PetType lizard = findOrSave("lizard");
        PetType snake = findOrSave("snake");
        PetType bird = findOrSave("bird");
        PetType hamster = findOrSave("hamster");

        save(new Pet("Leo", LocalDate.of(2000, 9, 7), cat, getOwner("George", "Franklin")));
        save(new Pet("Basil", LocalDate.of(2002, 8, 6), hamster, getOwner("Betty", "Davis")));
        save(new Pet("Rosy", LocalDate.of(2001, 4, 17), dog, getOwner("Eduardo", "Rodriquez")));
        save(new Pet("Jewel", LocalDate.of(2000, 3, 7), dog, getOwner("Harold", "Davis")));
        save(new Pet("Iggy", LocalDate.of(2000, 11, 30), lizard, getOwner("Peter", "McTavish")));
        save(new Pet("George", LocalDate.of(2000, 1, 20), snake, getOwner("Jean", "Coleman")));
        save(new Pet("Samantha", LocalDate.of(1995, 9, 4), cat, getOwner("Jeff", "Black"))
                .addVisit(new Visit(LocalDate.of(2010, 3, 4), "rabies shot"))
                .addVisit(new Visit(LocalDate.of(2008, 9, 4), "spayed")));
        save(new Pet("Max", LocalDate.of(1995, 9, 4), cat, getOwner("Maria", "Escobito"))
                .addVisit(new Visit(LocalDate.of(2011, 3, 4), "rabies shot"))
                .addVisit(new Visit(LocalDate.of(2009, 6, 4), "neutered")));
        save(new Pet("Lucky", LocalDate.of(1999, 8, 6), bird, getOwner("David", "Schroeder")));
        save(new Pet("Mulligan", LocalDate.of(1997, 2, 24), dog, getOwner("Carlos", "Estaban")));
        save(new Pet("Freddy", LocalDate.of(2000, 3, 9), bird, getOwner("David", "Schroeder")));
        save(new Pet("Lucky", LocalDate.of(2000, 6, 24), dog, getOwner("Carlos", "Estaban")));
        save(new Pet("Sly", LocalDate.of(2002, 6, 8), cat, getOwner("George", "Franklin")));
    }

    private PetType findOrSave(String name) {
        return petTypeRepository.findByName(name)
                .orElseGet(() -> petTypeRepository.save(new PetType(name)));
    }

    private Owner getOwner(String firstName, String lastName) {
        return ownerRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new DataInitializationException("Run owner data initialization before pet data initialization."));
    }

    private void save(Pet pet) {
        petRepository.save(pet);
    }

}
