package com.unimove.unimove.controller;

import com.unimove.unimove.dto.request.CreateReviewRequest;
import com.unimove.unimove.dto.response.ReviewResponse;
import com.unimove.unimove.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(Principal principal, @Valid @RequestBody CreateReviewRequest request){
        return ResponseEntity.status(201).body(reviewService.createReview(principal.getName(), request));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ReviewResponse>> getUserReviews(@PathVariable("username") String username){
        return ResponseEntity.ok(reviewService.getUserReviews(username));
    }
}
