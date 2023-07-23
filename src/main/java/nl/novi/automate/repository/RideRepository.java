package nl.novi.automate.repository;

import nl.novi.automate.model.Ride;
import nl.novi.automate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    Optional<Ride> findById(Long id);

    List<Ride >findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCase(
            String destination, String pickUpLocation);

    @Query("SELECT r FROM Ride r JOIN r.users u WHERE u = :user")
    List<Ride> findRidesForUser(@Param("user") User user);
}
