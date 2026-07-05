package com.unimove.unimove.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "rideId",
        "reviewerUsername",
        "reviewerFullName",
        "reviewedUsername",
        "reviewedFullName",
        "rating",
        "comment",
        "createdAt"
})
public class ReviewResponse {

    private UUID id;
    private UUID rideId;
    private String reviewerUsername;
    private String reviewerFullName;
    private String reviewedUsername;
    private String reviewedFullName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
