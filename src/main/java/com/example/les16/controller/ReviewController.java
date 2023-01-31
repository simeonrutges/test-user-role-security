package com.example.les16.controller;

import com.example.les16.dto.CarDto;
import com.example.les16.dto.ReviewDto;
import com.example.les16.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
private  final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @GetMapping("")
    public List<ReviewDto> getAllReviews() {

        List<ReviewDto> dtos = reviewService.getAllReviews();

        return dtos;
    }

    @GetMapping("/{id}")
    public ReviewDto getReview(@PathVariable("id") Long id) {

        ReviewDto dto = reviewService.getReview(id);

        return dto;
    }

//    @PostMapping("")
//    public ReviewDto addReview(@RequestBody ReviewDto dto) {
//        ReviewDto dto1 = reviewService.addReview(dto);
//        return dto1;
//    }
//    @PostMapping("/reviews")
//    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
//        ReviewDto createdReview = ReviewService.createReview(reviewDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
//    }
@PostMapping("")
public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
    ReviewDto savedReview = reviewService.createReview(reviewDto);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedReview.getId())
            .toUri();

    return ResponseEntity.created(location).body(savedReview);
}

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
    }

    //    ALles werkt behalvde de PUT
//    @PutMapping("/{id}")
//    public ReviewDto updateReview(@PathVariable("id") Long id, @RequestBody ReviewDto dto) {
//        reviewService.updateReview(id, dto);
//        return dto;
//    }
    // alles werkt behalve update
}
