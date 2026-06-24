package com.unimove.unimove.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBookingRequest {

    @NotNull(message = "L'id della corsa è obbligatorio")
    private UUID rideId;

    private String hotspotChosen;
}
