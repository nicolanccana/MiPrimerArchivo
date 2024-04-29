package org.e2e.labe2e02.review.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.driver.infrastructure.DriverRepository;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.ride.domain.Status;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.passenger.infrastructure.PassengerRepository;
import org.e2e.labe2e02.review.domain.Review;
import org.e2e.labe2e02.review.infrastructure.ReviewRepository;
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
import org.springframework.http.MediaType;
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
public class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Review review;
    private Passenger author;
    private Driver target;
    private Ride ride;

    @Autowired
    Reader reader;

    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(1.0);
        coordinate.setLongitude(1.0);

        vehicle = new Vehicle();
        vehicle.setBrand("Mercedes-Benz");
        vehicle.setModel("GLB 200");
        vehicle.setLicensePlate("ABC123");
        vehicle.setFabricationYear(2020);
        vehicle.setCapacity(5);


        author = new Passenger();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setEmail("author@example.com");
        author.setPassword("password");
        author.setPhoneNumber("123456789");
        author.setRole(Role.PASSENGER);
        author.setCreatedAt(ZonedDateTime.now());

        target = new Driver();
        target.setFirstName("Alice");
        target.setLastName("Smith");
        target.setCoordinate(coordinate);
        target.setVehicle(vehicle);
        target.setEmail("target@example.com");
        target.setPassword("password");
        target.setPhoneNumber("987654321");
        target.setRole(Role.DRIVER);
        target.setCategory(Category.X);
        target.setCreatedAt(ZonedDateTime.now());

        ride = new Ride();
        ride.setDriver(target);
        ride.setPassenger(author);
        ride.setDepartureDate(ZonedDateTime.now().toLocalDateTime());
        ride.setArrivalDate(ZonedDateTime.now().plusHours(1).toLocalDateTime());
        ride.setDestinationName("Destination");
        ride.setOriginName("Origin");
        ride.setStatus(Status.COMPLETED);
        ride.setPrice(100.0);


        review = new Review();
        review.setComment("Great ride!");
        review.setRating(5);
    }

    @Test
    public void testCreateNewReview() throws Exception {
        passengerRepository.save(author);
        target.setVehicle(vehicleRepository.save(vehicle));
        driverRepository.save(target);
        Ride currentRide = rideRepository.save(this.ride);

        String jsonContent = Reader.readJsonFile("/review/post.json");
        jsonContent = reader.updateId(jsonContent, "rideId", currentRide.getId());
        jsonContent = reader.updateId(jsonContent, "targetId", target.getId());


        var req = mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andReturn();

        Review createdReview = reviewRepository.findAll().get(0);
        Assertions.assertEquals("/review/" + createdReview.getId().toString(), req.getResponse().getHeader("Location"));


        Review savedReview = reviewRepository.findAll().get(0);
        Assertions.assertEquals(createdReview.getComment(), savedReview.getComment());
        Assertions.assertEquals(createdReview.getRating(), savedReview.getRating());
        Assertions.assertEquals(createdReview.getAuthor().getId(), savedReview.getAuthor().getId());
        Assertions.assertEquals(createdReview.getTarget().getId(), savedReview.getTarget().getId());
        Assertions.assertEquals(createdReview.getRide().getId(), savedReview.getRide().getId());
    }


    @Test
    public void testCreateNewReviewWithInvalidRideId() throws Exception {
        passengerRepository.save(author);
        target.setVehicle(vehicleRepository.save(vehicle));
        driverRepository.save(target);
        rideRepository.save(ride);

        mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"comment\": \"Great ride!\"," +
                                        "\"rating\": 5," +
                                        "\"rideId\": 9999," +
                                        "\"targetId\":" + "\"" + target.getId() + "\"" +
                                        "}"
                        ))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetPageofReviewsByDriver() throws Exception {
        passengerRepository.save(author);
        target.setVehicle(vehicleRepository.save(vehicle));
        driverRepository.save(target);

        String jsonContent = Reader.readJsonFile("/review/post.json");
        jsonContent = reader.updateId(jsonContent, "targetId", target.getId());

        for (int i = 1; i < 6; i++) {
            ride.setId((long) i);
            Ride currentRide = rideRepository.save(ride);

            jsonContent = reader.updateId(jsonContent, "rideId", currentRide.getId());
            mockMvc.perform(post("/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent));
        }

        mockMvc.perform(get("/review/{driverId}", target.getId())
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.content[0].author").doesNotExist())
                .andExpect(jsonPath("$.content[0].ride").doesNotExist())
                .andReturn();
    }

    @Test
    public void testGetPageofReviewsByDriverWithInvalidDriverId() throws Exception {
        mockMvc.perform(get("/review/{driverId}", 9999)
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(status().isNotFound());
    }
}
