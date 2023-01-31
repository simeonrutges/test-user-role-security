package com.example.les16.repository;

import com.example.les16.model.Ride;
import com.example.les16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    Optional<Ride> findById(Long id);

    List<Ride> findAllRidesByDestinationEqualsIgnoreCase(String destination);

}
