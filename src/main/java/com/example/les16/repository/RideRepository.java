package com.example.les16.repository;

import com.example.les16.model.Ride;
import com.example.les16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    Optional<Ride> findById(Long id);

    List<Ride> findAllRidesByDestinationEqualsIgnoreCase(String destination);

    List<Ride> findAllRidesByPickUpLocationEqualsIgnoreCase(String pickUpLocation);

    List<Ride> findAllRidesByDepartureDateEquals(LocalDate departureDate);

    List<Ride> findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCaseAndDepartureDateEquals(
            String destination, String pickUpLocation, LocalDate departureDate);

    List<Ride >findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCase(
            String destination, String pickUpLocation);

    List<Ride> findAllRidesByDestinationEqualsIgnoreCaseAndDepartureDateEquals(
            String destination, LocalDate departureDate);

    List<Ride> findAllRidesByPickUpLocationEqualsIgnoreCaseAndDepartureDateEquals(
            String pickUpLocation, LocalDate departureDate);

}
