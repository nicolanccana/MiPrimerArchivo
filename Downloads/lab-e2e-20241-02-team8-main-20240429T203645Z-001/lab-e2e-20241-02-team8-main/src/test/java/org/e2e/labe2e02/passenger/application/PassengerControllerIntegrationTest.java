package org.e2e.labe2e02.passenger.application;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.passenger.infrastructure.PassengerRepository;

import org.e2e.labe2e02.utils.Reader;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PassengerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    public void createAPassengerTwiceExpectConflict() throws Exception {
        String jsonContent = Reader.readJsonFile("/passenger/create.json");
        var res = mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")));

        mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void getNonExistingPassengerExpectNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        Assertions.assertFalse(passengerRepository.findById(1L).isPresent());
    }

    @Test
    public void getPassengerDtoAndExpectNullPassword() throws Exception {
        var res = mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/create.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        Assertions.assertTrue(passengerRepository.existsById(Long.parseLong(id)));

        var newPassenger = mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")))
                .andReturn();

        Assertions.assertFalse(newPassenger.getResponse().getContentAsString().contains("mypassword123"));
    }

    @Test
    public void patchPassengerAddNewPlaceExpectOk() throws Exception {
        var res = mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/create.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(patch("/passenger/{id}/places", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/patch.json")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<Passenger> updatedPassenger = passengerRepository.findById(Long.parseLong(id));
        Assertions.assertTrue(updatedPassenger.isPresent());

        Coordinate coordinate = updatedPassenger.get().getCoordinates().get(0).getCoordinate();
        Assertions.assertEquals(42.1234, coordinate.getLatitude());
        Assertions.assertEquals(-71.9876, coordinate.getLongitude());
    }

    @Test
    public void testGetPlaces() throws Exception {
        var res = mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/create.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(patch("/passenger/{id}/places", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/patch.json")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mockMvc.perform(get("/passenger/{id}/places", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("Sample Place")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].coordinate.latitude", Matchers.is(42.1234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].coordinate.longitude", Matchers.is(-71.9876)))
                .andExpect(jsonPath("$[0].passengers").doesNotExist());

    }

    @Test
    public void testDeletePassenger() throws Exception {
        var res = mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/create.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);


        mockMvc.perform(delete("/passenger/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertFalse(passengerRepository.findById(Long.valueOf(id)).isPresent());
    }

    @Test
    public void testDeleteNonExistingPassengerReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/passenger/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Assertions.assertFalse(passengerRepository.existsById(1L));
    }
    @Test
    public void testDeletePlace() throws Exception {
        var res = mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/create.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(patch("/passenger/{id}/places", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/passenger/patch.json")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Optional<Passenger> updatedPassenger = passengerRepository.findById(Long.parseLong(id));
        Assertions.assertTrue(updatedPassenger.isPresent());
        Coordinate coordinate = updatedPassenger.get().getCoordinates().get(0).getCoordinate();
        Assertions.assertEquals(42.1234, coordinate.getLatitude());
        Assertions.assertEquals(-71.9876, coordinate.getLongitude());

        Long coordinateId = updatedPassenger.get().getCoordinates().get(0).getCoordinate().getId();
        mockMvc.perform(
                        delete(
                                "/passenger/{id}/places/{coordinateId}",
                                Long.valueOf(id),
                                coordinateId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        updatedPassenger = passengerRepository.findById(Long.parseLong(id));
        Assertions.assertTrue(updatedPassenger.isPresent());
        Assertions.assertTrue(updatedPassenger.get().getCoordinates().isEmpty());

    }

}
