package org.e2e.labe2e02.ride.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.coordinate.infrastructure.CoordinateRepository;
import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.ride.domain.Status;
import org.e2e.labe2e02.driver.infrastructure.DriverRepository;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.passenger.infrastructure.PassengerRepository;
import org.e2e.labe2e02.ride.domain.Ride;
import org.e2e.labe2e02.ride.infrastructure.RideRepository;
import org.e2e.labe2e02.user.domain.Role;
import org.e2e.labe2e02.utils.Reader;
import org.e2e.labe2e02.vehicle.domain.Vehicle;
import org.e2e.labe2e02.vehicle.infrastructure.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RideControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    Reader reader;

    private Driver driver;

    private Passenger passenger;

    private Ride ride;

    @BeforeEach
    public void setUp() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(42.1234);
        coordinate.setLongitude(-71.9876);

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setFabricationYear(2020);
        vehicle.setCapacity(5);
        vehicle.setLicensePlate("ABC123");
        vehicleRepository.save(vehicle);

        driver = new Driver();
        driver.setFirstName("John");
        driver.setLastName("Doe");
        driver.setEmail("john.doe@example.com");
        driver.setPassword("password");
        driver.setPhoneNumber("123456789");
        driver.setRole(Role.DRIVER);
        driver.setCoordinate(coordinate);
        driver.setVehicle(vehicle);
        driver.setCreatedAt(ZonedDateTime.now());
        driver.setCategory(Category.X);

        passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setEmail("passenger@example.com");
        passenger.setPassword("password");
        passenger.setPhoneNumber("123456789");
        passenger.setRole(Role.PASSENGER);
        passenger.setCreatedAt(ZonedDateTime.now());


        ride = new Ride();
        ride.setDriver(driver);
        ride.setPassenger(passenger);
        ride.setDepartureDate(ZonedDateTime.now().toLocalDateTime());
        ride.setArrivalDate(ZonedDateTime.now().plusHours(1).toLocalDateTime());
        ride.setDestinationName("Destination");
        ride.setOriginName("Origin");
        ride.setStatus(Status.COMPLETED);
        ride.setPrice(100.0);

    }


    @Test
    public void passengerBookRideAndReturnOk() throws Exception {

        Driver currentDriver = driverRepository.save(driver);
        Passenger currentPassenger = passengerRepository.save(passenger);

        String jsonContent = Reader.readJsonFile("/ride/post.json");
        jsonContent = reader.updateId(jsonContent, "passengerId", currentPassenger.getId());
        jsonContent = reader.updateId(jsonContent, "driverId", currentDriver.getId());

        mockMvc.perform(post("/ride")
                        .content(jsonContent)
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        String location = "/ride/" + rideRepository.findAll().get(0).getId();
        String id = location.substring(location.lastIndexOf("/") + 1);

        Ride createdRide = rideRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Ride not found"));
        Assertions.assertEquals(ride.getDriver().getEmail(), createdRide.getDriver().getEmail());
        Assertions.assertEquals(ride.getPassenger().getEmail(), createdRide.getPassenger().getEmail());
        Assertions.assertNotNull(createdRide.getDepartureDate());

    }

    @Test
    public void getRidesByUserAndReturnPageOfRides() throws Exception {

        Passenger currentPassenger = passengerRepository.save(passenger);
        Driver currentDriver = driverRepository.save(driver);

        ride.setDriver(currentDriver);
        ride.setPassenger(currentPassenger);

        Ride currentRide = rideRepository.save(ride);

        var res = mockMvc.perform(get("/ride/{id}", currentRide.getPassenger().getId())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(jsonPath("$.content[0].id").value(currentRide.getId()))
                .andExpect(jsonPath("$.content[0].originName").value(currentRide.getOriginName()))
                .andExpect(jsonPath("$.content[0].destinationName").value(currentRide.getDestinationName()))
                .andExpect(jsonPath("$.content[0].price").value(currentRide.getPrice()))
                .andExpect(jsonPath("$.content[0].departureDate").exists())
                .andExpect((jsonPath("$.content[0].driver").doesNotExist()))
                .andExpect((jsonPath("$.content[0].passenger").doesNotExist()))
                .andExpect(status().isOk())
                .andReturn();
    }
}
