package com.example.les16.repository;

import com.example.les16.model.Car;
import com.example.les16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findById(Long id);
}
