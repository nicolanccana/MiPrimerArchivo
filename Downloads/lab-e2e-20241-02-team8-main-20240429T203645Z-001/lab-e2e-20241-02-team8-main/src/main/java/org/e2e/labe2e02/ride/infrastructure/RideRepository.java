package org.e2e.labe2e02.ride.infrastructure;

import org.e2e.labe2e02.ride.domain.Status;
import org.e2e.labe2e02.ride.domain.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findAllByPassengerIdAndStatus(Long passenger_id, Status status, Pageable pageable);
}