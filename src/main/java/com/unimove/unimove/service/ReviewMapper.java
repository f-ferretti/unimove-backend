package com.unimove.unimove.service;

import com.unimove.unimove.dto.response.ReviewResponse;
import com.unimove.unimove.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rideId(review.getRide().getId())
                .reviewerUsername(review.getReviewer().getUsername())
                .reviewerFullName(review.getReviewer().getFullName())
                .reviewedUsername(review.getReviewed().getUsername())
                .reviewedFullName(review.getReviewed().getFullName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

