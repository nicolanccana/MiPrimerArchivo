package org.e2e.labe2e02.driver.domain;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.coordinate.infrastructure.CoordinateRepository;
import org.e2e.labe2e02.driver.dto.DriverDto;
import org.e2e.labe2e02.driver.dto.NewDriverRequestDto;
import org.e2e.labe2e02.driver.infrastructure.DriverRepository;
import org.e2e.labe2e02.exceptions.ResourceNotFound;
import org.e2e.labe2e02.exceptions.UniqueResourceAlreadyExist;
import org.e2e.labe2e02.vehicle.domain.Vehicle;
import org.e2e.labe2e02.vehicle.domain.VehicleService;
import org.e2e.labe2e02.vehicle.dto.VehicleBasicDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    private DriverRepository driverRepository;
    private CoordinateRepository coordinateRepository;
    private ModelMapper modelMapper;
    @Autowired
    DriverService(DriverRepository driverRepository,CoordinateRepository coordinateRepository,ModelMapper modelMapper){
        this.driverRepository=driverRepository;
        this.coordinateRepository=coordinateRepository;
        this.modelMapper=modelMapper;
    }
    public DriverDto getDriver(Long id) {

        Driver driver= driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Driver not found"));
        DriverDto driverDto = modelMapper.map(driver,DriverDto.class);
        driverDto.setVehicle(modelMapper.map(driver.getVehicle(), VehicleBasicDto.class));
        return driverDto;
    }

    public Driver saveDriver(NewDriverRequestDto newDriver) {
        try {
            Driver driver= modelMapper.map(newDriver,Driver.class);
            driver.setVehicle(modelMapper.map(newDriver.getVehicle(),Vehicle.class));
            return driverRepository.save(driver);
        }catch (Exception e){ throw new UniqueResourceAlreadyExist("conflicto");}

    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public void updateDriver(Long id, Driver driver) {

        Driver driverToUpdate = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driverToUpdate.setFirstName(driver.getFirstName());
        driverToUpdate.setLastName(driver.getLastName());
        driverToUpdate.setTrips(driver.getTrips());
        driverToUpdate.setAvgRating(driver.getAvgRating());
        driverToUpdate.setCategory(driver.getCategory());
        driverToUpdate.setVehicle(driver.getVehicle());
        driverToUpdate.setCoordinate(driver.getCoordinate());

        driverRepository.save(driverToUpdate);
    }

    public void updateDriverLocation(Long id, Double latitude, Double longitude) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        coordinateRepository.save(coordinate);
        driver.setCoordinate(coordinate);
        driverRepository.save(driver);
    }

    public void updateDriverCar(Long id, Vehicle vehicle) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Driver not found"));

        driver.setVehicle(vehicle);
        driverRepository.save(driver);
    }
}