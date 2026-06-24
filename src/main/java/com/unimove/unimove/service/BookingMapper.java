package com.unimove.unimove.service;

import com.unimove.unimove.dto.response.BookingResponse;
import com.unimove.unimove.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .rideId(booking.getRide().getId())
                .passengerUsername(booking.getPassenger().getUsername())
                .passengerFullName(booking.getPassenger().getFullName())
                .hotspotChosen(booking.getHotspotChosen())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
