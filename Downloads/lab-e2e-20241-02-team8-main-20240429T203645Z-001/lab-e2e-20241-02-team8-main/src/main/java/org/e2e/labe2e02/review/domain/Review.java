package org.e2e.labe2e02.review.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.e2e.labe2e02.ride.domain.Ride;
import org.e2e.labe2e02.user.domain.User;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String comment;


    @Column(nullable = false)
    private Integer rating;

    @OneToOne
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private User target;
}