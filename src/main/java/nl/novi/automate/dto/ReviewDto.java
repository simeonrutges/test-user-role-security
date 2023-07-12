package nl.novi.automate.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ReviewDto {
    public Long id;
    @NotBlank
    public String text;
    public LocalDate publishDate;
    public String reviewedUserUsername;
    public String reviewerUsername;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getReviewedUserUsername() {
        return reviewedUserUsername;
    }

    public void setReviewedUserUsername(String reviewedUserUsername) {
        this.reviewedUserUsername = reviewedUserUsername;
    }

    public String getReviewerUsername() {
        return reviewerUsername;
    }

    public void setReviewerUsername(String reviewerUsername) {
        this.reviewerUsername = reviewerUsername;
    }
}
