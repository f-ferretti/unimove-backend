package com.unimove.unimove.service;


import com.unimove.unimove.dto.response.RideResponse;
import com.unimove.unimove.model.Ride;
import com.unimove.unimove.model.Role;
import com.unimove.unimove.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RideMapperTest {

    private RideMapper rideMapper;
    private User driver;

    @BeforeEach
    void setUp() {
        rideMapper = new RideMapper();

        driver = User.builder()
                .id(UUID.randomUUID())
                .username("l.lanese")
                .fullName("Luca Lanese")
                .role(Role.STUDENT)
                .build();
    }

    @Test
    void toResponse_conTuttiGliHotspot_mappaListaCompleta() {
        Ride ride = Ride.builder()
                .id(UUID.randomUUID())
                .driver(driver)
                .departureCity("Campobasso")
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalCity("Roma")
                .hotspot1("Stazione FS")
                .hotspot2("Piazza Vittoria")
                .hotspot3("Mc Donald's")
                .totalSeats(4)
                .availableSeats(4)
                .status("OPEN")
                .build();

        RideResponse response = rideMapper.toResponse(ride);

        assertThat(response.getHotspots()).containsExactly("Stazione FS", "Piazza Vittoria", "Mc Donald's");
        assertThat(response.getDriverUsername()).isEqualTo("l.lanese");
        assertThat(response.getDriverFullName()).isEqualTo("Luca Lanese");
        assertThat(response.getDepartureCity()).isEqualTo("Campobasso");
        assertThat(response.getArrivalCity()).isEqualTo("Roma");
        assertThat(response.getStatus()).isEqualTo("OPEN");
    }

    @Test
    void toResponse_conHotspotParziali_mappaSoloQuelliPresenti() {
        Ride ride = Ride.builder()
                .id(UUID.randomUUID())
                .driver(driver)
                .departureCity("Isernia")
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalCity("Napoli")
                .hotspot1("Stazione")
                .hotspot2(null)
                .hotspot3(null)
                .totalSeats(3)
                .availableSeats(3)
                .status("OPEN")
                .build();

        RideResponse response = rideMapper.toResponse(ride);

        assertThat(response.getHotspots()).containsExactly("Stazione");
    }

    @Test
    void toResponse_senzaHotspot_restituisceListaVuota() {
        Ride ride = Ride.builder()
                .id(UUID.randomUUID())
                .driver(driver)
                .departureCity("Isernia")
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalCity("Napoli")
                .totalSeats(3)
                .availableSeats(3)
                .status("OPEN")
                .build();

        RideResponse response = rideMapper.toResponse(ride);

        assertThat(response.getHotspots()).isEmpty();
    }

    @Test
    void toResponse_conPostiParzialmenteOccupati_mappaCorrettamenteDisponibilita() {
        Ride ride = Ride.builder()
                .id(UUID.randomUUID())
                .driver(driver)
                .departureCity("Campobasso")
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalCity("Roma")
                .totalSeats(4)
                .availableSeats(2)
                .status("OPEN")
                .build();

        RideResponse response = rideMapper.toResponse(ride);

        assertThat(response.getTotalSeats()).isEqualTo(4);
        assertThat(response.getAvailableSeats()).isEqualTo(2);
    }
}
