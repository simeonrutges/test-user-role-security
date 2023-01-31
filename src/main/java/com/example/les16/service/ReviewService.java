package com.example.les16.service;

import com.example.les16.dto.CarDto;
import com.example.les16.dto.ReviewDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.exceptions.UserNotFoundException;
import com.example.les16.model.Car;
import com.example.les16.model.Review;
import com.example.les16.model.User;
import com.example.les16.repository.ReviewRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewDto createReview(ReviewDto reviewDto) {
        com.example.les16.model.User reviewedUser = userRepository.findByUsername(reviewDto.getReviewedUserUsername())
                .orElseThrow(() -> new UserNotFoundException("User with username " + reviewDto.getReviewerUsername() + " not found"));
        User reviewer = userRepository.findByUsername(reviewDto.getReviewerUsername())
                .orElseThrow(() -> new UserNotFoundException("User with username " + reviewDto.getReviewerUsername() + " not found"));

        Review review = new Review();

        review.setReviewedUser(reviewedUser);
        review.setReviewer(reviewer);
        review.setText(reviewDto.getText());
        review.setPublishDate(reviewDto.getPublishDate());

        Review savedReview = reviewRepository.save(review);
        return transferToDto(savedReview);
    }

    public List<ReviewDto> getAllReviews() {
        List<ReviewDto> dtos = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAll();
        for (Review rc : reviews) {
            dtos.add(transferToDto(rc));
        }
        return dtos;
    }

    public ReviewDto getReview(long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isPresent()) {
            return transferToDto(review.get());
        } else {
            throw new RecordNotFoundException("No review found");
        }
    }

//    public ReviewDto addReview(ReviewDto reviewDto) {
//        Review rc =  transferToReview(reviewDto);
//        reviewRepository.save(rc);
//        return reviewDto;
//    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

//    public void updateReview(Long id, ReviewDto reviewDto) {
//        if(!reviewRepository.existsById(id)) {
//            throw new RecordNotFoundException("No review found");
//        }
//        Review storedReview = reviewRepository.findById(id).orElse(null);
//        storedReview.setId(reviewDto.getId());
//        storedReview.setReviewer(reviewDto.getReviewer());
//        storedReview.setReceiver(reviewDto.getReceiver());
//        storedReview.setText(reviewDto.getText());
//        storedReview.setPublishDate(reviewDto.getPublishDate());
//        reviewRepository.save(storedReview);
//    }

//    public Review transferToReview(ReviewDto reviewDto){
//        var review = new Review();
//
//        review.setId(reviewDto.getId());
//        review.setReviewer(reviewDto.getReviewer());
//        review.setReceiver(reviewDto.getReceiver());
//        review.setText(reviewDto.getText());
//        review.setPublishDate(reviewDto.getPublishDate());
//
//        return review;
//    }
    public ReviewDto transferToDto(Review review){
        var dto = new ReviewDto();

        dto.id = review.getId();
        dto.reviewedUserUsername = review.getReviewedUser().getUsername();
        dto.reviewerUsername = review.getReviewer().getUsername();
        dto.text = review.getText();
        dto.publishDate = review.getPublishDate();

        return dto;
    }
}
