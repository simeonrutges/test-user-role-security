package nl.novi.automate.service;

import nl.novi.automate.dto.ReviewDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserNotFoundException;
import nl.novi.automate.model.Review;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.ReviewRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
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

    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = transferToReview(reviewDto);
        Review savedReview = reviewRepository.save(review);
        return transferToDto(savedReview);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public void updateReview(Long id, ReviewDto reviewDto) {
        if(!reviewRepository.existsById(id)) {
            throw new RecordNotFoundException("No review found");
        }
        Review reviewToUpdate = transferToReview(reviewDto);
        reviewToUpdate.setId(id);
        reviewRepository.save(reviewToUpdate);
    }

    public ReviewDto transferToDto(Review review){
        var dto = new ReviewDto();

        dto.id = review.getId();
        dto.reviewedUserUsername = review.getReviewedUser().getUsername();
        dto.reviewerUsername = review.getReviewer().getUsername();
        dto.text = review.getText();
        dto.publishDate = review.getPublishDate();

        return dto;
    }

public Review transferToReview(ReviewDto reviewDto){
    var review = new Review();

    review.setId(reviewDto.getId());
// Let op: Op dit moment is de foutafhandeling hier gezet. Indien nodig kunnen deze verplaatst worden naar de createReview en updatReview.
    User reviewer = userRepository.findByUsername(reviewDto.getReviewerUsername())
            .orElseThrow(() -> new UserNotFoundException("User with username " + reviewDto.getReviewerUsername() + " not found"));
    User reviewedUser = userRepository.findByUsername(reviewDto.getReviewedUserUsername())
            .orElseThrow(() -> new UserNotFoundException("User with username " + reviewDto.getReviewedUserUsername() + " not found"));

    review.setReviewer(reviewer);
    review.setReviewedUser(reviewedUser);
    review.setText(reviewDto.getText());
    review.setPublishDate(reviewDto.getPublishDate());

    return review;
}

}
