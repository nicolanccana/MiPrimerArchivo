package org.e2e.labe2e02.driver.application;

import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.driver.infrastructure.DriverRepository;
import org.e2e.labe2e02.utils.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DriverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;


    @Test
    public void testSaveDriverAndExpectCreated() throws Exception {

        var res = mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/post.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        Assertions.assertTrue(driverRepository.existsById(Long.valueOf(id)));
        mockMvc.perform(MockMvcRequestBuilders.get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.coordinate").doesNotExist())
                .andExpect(jsonPath("$.rides").doesNotExist());
    }

    @Test
    public void testSaveDriverWithExistingEmailExpectConflict() throws Exception {
        String jsonContent = Reader.readJsonFile("/driver/post.json");
        var res = mockMvc.perform(post("/driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)).andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        Optional<Driver> savedDriver = driverRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(savedDriver.isPresent());

        mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetNotExistingDriver() throws Exception {
        mockMvc.perform(get("/driver/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAndDeleteDriver() throws Exception {
        var res = mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/post.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        Optional<Driver> createdDriver = driverRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(createdDriver.isPresent());

        mockMvc.perform(delete(location))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(driverRepository.existsById(Long.valueOf(id)));
    }

    @Test
    public void testDeleteDriver() throws Exception {
        var res = mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/post.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(delete("/driver/{id}", id))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(driverRepository.existsById(Long.valueOf(id)));
    }

    @Test
    public void testUpdateDriver() throws Exception {
        var res = mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/post.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(put("/driver/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/UpdateDriver.json")))
                .andExpect(status().isOk());

        Optional<Driver> updatedDriver = driverRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(updatedDriver.isPresent());
        Assertions.assertEquals(updatedDriver.get().getFirstName(), "Jane");
    }

    @Test
    public void testUpdateDriverCar() throws Exception {
        var res = mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/post.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(patch("/driver/{id}/car", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/updateDriverCar.json")))
                .andExpect(status().isOk());

        Optional<Driver> updatedDriver = driverRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(updatedDriver.isPresent());
        Assertions.assertEquals(updatedDriver.get().getVehicle().getBrand(), "Honda");
        Assertions.assertEquals(updatedDriver.get().getVehicle().getModel(), "Accord");
    }


    @Test
    public void testUpdateDriverLocation() throws Exception {
        var res = mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Reader.readJsonFile("/driver/post.json")))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(patch("/driver/{id}/location", id)
                        .param("latitude", "42.123")
                        .param("longitude", "-71.987"))
                .andExpect(status().isOk());

        Optional<Driver> updatedDriver = driverRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(updatedDriver.isPresent());
        Assertions.assertEquals(updatedDriver.get().getCoordinate().getLatitude(), 42.123);
        Assertions.assertEquals(updatedDriver.get().getCoordinate().getLongitude(), -71.987);
    }

}
