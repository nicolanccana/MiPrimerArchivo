package org.e2e.labe2e02.passenger.infrastructure;

import jakarta.transaction.Transactional;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.user.infrastructure.BaseUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface PassengerRepository extends BaseUserRepository<Passenger> {
    boolean existsByEmail(String email);

}