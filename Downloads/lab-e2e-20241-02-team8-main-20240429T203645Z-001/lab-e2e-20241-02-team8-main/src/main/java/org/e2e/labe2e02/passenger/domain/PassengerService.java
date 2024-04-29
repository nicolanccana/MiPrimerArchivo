package org.e2e.labe2e02.passenger.domain;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;
import org.e2e.labe2e02.coordinate.infrastructure.CoordinateRepository;
import org.e2e.labe2e02.exceptions.ResourceNotFound;
import org.e2e.labe2e02.exceptions.UniqueResourceAlreadyExist;
import org.e2e.labe2e02.passenger.dto.PassengerLocationResponseDto;
import org.e2e.labe2e02.passenger.dto.PassengerRequestDto;
import org.e2e.labe2e02.passenger.dto.PassengerResponseDto;
import org.e2e.labe2e02.passenger.infrastructure.PassengerRepository;
import org.e2e.labe2e02.user.domain.Role;
import org.e2e.labe2e02.user_locations.domain.UserLocation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;
    @Autowired
    ModelMapper modelMapper;
    public PassengerResponseDto getPassenger(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Passenger not found"));
        return modelMapper.map(passenger,PassengerResponseDto.class);
    }

    public void deletePassenger(Long id) {
        if(!passengerRepository.existsById(id)) throw new ResourceNotFound(" ");

        passengerRepository.deleteById(id);
    }

    public void addPassengerPlace(Long id, PassengerLocationResponseDto passengerLocationResponseDto) {

        Optional<Coordinate> coord =
                coordinateRepository
                        .findByLatitudeAndLongitude(
                                passengerLocationResponseDto.getCoordinate().getLatitude(),
                                passengerLocationResponseDto.getCoordinate().getLongitude());

        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("pasajero no encontrado"));

        if (coord.isEmpty()) {
            Coordinate coordinate= modelMapper.map(passengerLocationResponseDto.getCoordinate(),Coordinate.class);
            coordinateRepository.save(coordinate);
            passenger.addCoordinate(coordinate, passengerLocationResponseDto.getDescription());
            passengerRepository.save(passenger);
        } else {
            passenger.addCoordinate(coord.get(), passengerLocationResponseDto.getDescription());
            passengerRepository.save(passenger);
        }
    }

    public void deletePassengerPlace(Long id, Long coordinateId) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Passenger not found"));

        Coordinate coordinate = coordinateRepository
                .findById(coordinateId)
                .orElseThrow(() -> new ResourceNotFound("Coordinate not found"));

        passenger.removeCoordinate(coordinate);
        passengerRepository.save(passenger);
    }

    public List<PassengerLocationResponseDto> getPassengerPlaces(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Passenger not found"));
        List<PassengerLocationResponseDto> locations= new ArrayList<>();
        for(UserLocation coordinate:passenger.getCoordinates()){
            locations.add(modelMapper.map(coordinate, PassengerLocationResponseDto.class));

        }
        return locations;
    }

    public Passenger createPassenger(PassengerRequestDto passengerRequestDto) {
        if(passengerRepository.existsByEmail(passengerRequestDto.getEmail()))throw new UniqueResourceAlreadyExist("el correo ya existe");
        Passenger passenger = modelMapper.map(passengerRequestDto,Passenger.class);
        passenger.setRole(Role.PASSENGER);
        return passengerRepository.save(passenger);
    }
}