package org.e2e.labe2e02.review.infrastructure;

import org.e2e.labe2e02.review.domain.Review;
import org.e2e.labe2e02.review.dto.ReviewsByUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Set<Review> findByRating(Integer rating);
    Set<Review> findByAuthor_Id(Long authorId);
    Long countByAuthor_Id(Long authorId);
    Page<ReviewsByUser> findAllByTarget_Id(Long id, Pageable pageable);
}