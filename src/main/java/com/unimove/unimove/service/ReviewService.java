package com.unimove.unimove.service;

import com.unimove.unimove.dto.request.CreateReviewRequest;
import com.unimove.unimove.dto.response.ReviewResponse;
import com.unimove.unimove.model.Review;
import com.unimove.unimove.model.Ride;
import com.unimove.unimove.model.User;
import com.unimove.unimove.repository.BookingRepository;
import com.unimove.unimove.repository.ReviewRepository;
import com.unimove.unimove.repository.RideRepository;
import com.unimove.unimove.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private static final String UTENTE_NON_TROVATO = "Utente non trovato";
    private static final String CORSA_NON_TROVATA = "Corsa non trovata";

    private final ReviewRepository reviewRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, RideRepository rideRepository, UserRepository userRepository, BookingRepository bookingRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewResponse createReview(String username, CreateReviewRequest request) {

        User reviewer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(UTENTE_NON_TROVATO));

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException(CORSA_NON_TROVATA));

        if (!ride.getStatus().equals("COMPLETED")) {
            throw new RuntimeException("Puoi recensire solo corse completate");
        }

        if (!bookingRepository.existsByRideAndPassenger(ride, reviewer)) {
            throw new RuntimeException("Non sei stato passeggero di questa corsa");
        }

        if (reviewRepository.existsByRideAndReviewer(ride, reviewer)) {
            throw new RuntimeException("Hai gia recensito questa corsa");
        }

        User reviewed = ride.getDriver();

        Review review = Review.builder()
                .ride(ride)
                .reviewer(reviewer)
                .reviewed(reviewed)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getUserReviews(String username) {

        User reviewed = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(UTENTE_NON_TROVATO));

        return reviewRepository.findByReviewed(reviewed)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

}
