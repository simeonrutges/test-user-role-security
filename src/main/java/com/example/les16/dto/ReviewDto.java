package com.example.les16.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReviewDto {
    public Long id;
    @NotBlank
    public String text;
    public LocalDate publishDate;
    public String reviewedUserUsername;
    public String reviewerUsername;

//    public ReviewDto(){
//
//    }
//
//    public ReviewDto(Long id, String text, LocalDate publishDate, Long reviewedUserId, Long reviewerId) {
//        this.id = id;
//        this.text = text;
//        this.publishDate = publishDate;
//        this.reviewedUserId = reviewedUserId;
//        this.reviewerId = reviewerId;
//    }

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
