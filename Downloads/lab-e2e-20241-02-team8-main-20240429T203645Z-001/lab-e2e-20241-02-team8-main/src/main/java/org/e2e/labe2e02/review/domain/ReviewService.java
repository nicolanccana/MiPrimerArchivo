package org.e2e.labe2e02.review.domain;

import jakarta.persistence.Id;
import org.e2e.labe2e02.exceptions.ResourceNotFound;
import org.e2e.labe2e02.review.dto.NewReviewDto;
import org.e2e.labe2e02.review.dto.ReviewsByUser;
import org.e2e.labe2e02.review.infrastructure.ReviewRepository;
import org.e2e.labe2e02.ride.domain.Ride;
import org.e2e.labe2e02.ride.infrastructure.RideRepository;
import org.e2e.labe2e02.user.domain.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Set;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RideRepository rideRepository;
    public Review createNewReview(NewReviewDto newReviewDto) {

        Ride ride = rideRepository
                .findById(newReviewDto.getRideId())
                .orElseThrow(() -> new ResourceNotFound(" "));

        Review review= new Review();
        review.setRating(newReviewDto.getRating());
        review.setComment(newReviewDto.getComment());
        review.setRide(ride);
        review.setAuthor(ride.getPassenger());
        review.setTarget(ride.getDriver());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Review not found"));

        reviewRepository.deleteById(id);
    }
    public Page<ReviewsByUser> getPage(Long driverId, int page, int size){
        Page<ReviewsByUser> reviewsByUserPage= reviewRepository.findAllByTarget_Id(driverId, PageRequest.of(page,size));
        if(reviewsByUserPage.isEmpty()) throw new ResourceNotFound("Review not found");
        return reviewsByUserPage;
    }
}