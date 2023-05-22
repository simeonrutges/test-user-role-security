package nl.novi.automate.repository;

import nl.novi.automate.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findById(Long id);
}
