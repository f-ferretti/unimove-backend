package com.unimove.unimove.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateReviewRequest {

    @NotNull(message = "L'id della corsa è obbligatorio")
    private UUID rideId;

    @Min(value = 1, message = "Il rating minimo è 1")
    @Max(value = 5, message = "Il rating massimo è 5")
    private int rating;

    private String comment;
}
