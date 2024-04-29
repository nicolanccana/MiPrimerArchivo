package org.e2e.labe2e02.passenger.application;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.passenger.domain.PassengerService;
import org.e2e.labe2e02.passenger.dto.PassengerLocationResponseDto;
import org.e2e.labe2e02.passenger.dto.PassengerRequestDto;
import org.e2e.labe2e02.passenger.dto.PassengerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;


    /*
        POST /passenger
        Request Body: PassengerRequestDto
        Response Body: Void
        Response Status: 201 Created
     */

    @PostMapping
    ResponseEntity<Void> postPassenger(@RequestBody PassengerRequestDto newPassenger){
        return  ResponseEntity.created(URI.create("/passenger/"+passengerService.createPassenger(newPassenger).getId())).build();
    }
    @GetMapping("/{id}")
    ResponseEntity<PassengerResponseDto> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassenger(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/places")
    ResponseEntity<Void> addPlace(
            @PathVariable Long id,
            @RequestBody PassengerLocationResponseDto passengerLocationResponseDto) {
        passengerService.addPassengerPlace(id, passengerLocationResponseDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/places/{coordinateId}")
    ResponseEntity<Void> deletePlace(
            @PathVariable Long id,
            @PathVariable Long coordinateId) {
        passengerService.deletePassengerPlace(id, coordinateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/places")
    ResponseEntity<List<PassengerLocationResponseDto>> getPlaces(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerPlaces(id));
    }

}