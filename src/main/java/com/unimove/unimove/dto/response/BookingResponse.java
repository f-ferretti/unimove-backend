package com.unimove.unimove.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@JsonPropertyOrder({
        "id",
        "rideId",
        "passengerUsername",
        "passengerFullName",
        "hotspotChosen",
        "status",
        "createdAt"
})

public class BookingResponse {

    private UUID id;
    private UUID rideId;
    private String passengerUsername;
    private String passengerFullName;
    private String hotspotChosen;
    private String status;
    private LocalDateTime createdAt;
}
