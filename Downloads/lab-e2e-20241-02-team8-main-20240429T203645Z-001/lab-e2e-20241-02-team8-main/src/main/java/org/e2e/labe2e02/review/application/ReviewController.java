package org.e2e.labe2e02.review.application;

import jakarta.websocket.server.PathParam;
import lombok.Getter;
import org.e2e.labe2e02.review.domain.Review;
import org.e2e.labe2e02.review.domain.ReviewService;
import org.e2e.labe2e02.review.dto.NewReviewDto;
import org.e2e.labe2e02.review.dto.ReviewsByUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<Void> createNewReview (@RequestBody NewReviewDto reviewDto){
        return ResponseEntity.created(URI.create("/review/"+reviewService.createNewReview(reviewDto).getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview (@PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    /*
        GET /review/{driverId}
        Path Variable: Long id
        Response Status: 200 OK

     */
    @GetMapping("/{driverId}")
    public ResponseEntity<Page<ReviewsByUser>> leer(@PathVariable Long driverId, @PathParam("page") int page, @PathParam("size") int size){
        return ResponseEntity.ok(reviewService.getPage(driverId,page,size));
    }

}

