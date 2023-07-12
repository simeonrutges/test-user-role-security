package nl.novi.automate.repository;

import nl.novi.automate.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
   Optional<Review>findById(Long id);

}
