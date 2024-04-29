package org.e2e.labe2e02.driver.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.ride.domain.Ride;
import org.e2e.labe2e02.user.domain.User;
import org.e2e.labe2e02.vehicle.domain.Vehicle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Driver extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "driver")
    private List<Ride> rides;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id")
    private Coordinate coordinate;

}