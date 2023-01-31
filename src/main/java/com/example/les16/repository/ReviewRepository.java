package com.example.les16.repository;

import com.example.les16.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//   Optional<Review>findById(Long id);
}
