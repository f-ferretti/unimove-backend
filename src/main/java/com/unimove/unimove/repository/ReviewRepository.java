package com.unimove.unimove.repository;

import com.unimove.unimove.model.Review;
import com.unimove.unimove.model.Ride;
import com.unimove.unimove.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    boolean existsByRideAndReviewer(Ride ride, User reviewer);

    List<Review> findByReviewed(User reviewed);
}
