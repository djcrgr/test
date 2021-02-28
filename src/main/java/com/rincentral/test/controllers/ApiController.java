package com.rincentral.test.controllers;

import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.ICarInfo;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.CarsApiService;
import com.rincentral.test.services.ExternalCarsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api")
public class ApiController {

    private final CarsApiService carsApiService;
    private final ExternalCarsApiService externalCarsApiService;

    @Autowired
    public ApiController(CarsApiService carsApiService, ExternalCarsApiService externalCarsApiService) {
        this.carsApiService = carsApiService;
        this.externalCarsApiService = externalCarsApiService;
    }

    @GetMapping("/paged")
    public ResponseEntity<List<ExternalCar>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(externalCarsApiService.getAllPaginated(pageNo, pageSize));
    }

    @GetMapping("/cars")
    public ResponseEntity<List<? extends ICarInfo>> getCars(CarRequestParameters carRequestParameters) {
        List<? extends ICarInfo> cars;
        if (carRequestParameters.getIsFull() != null && carRequestParameters.getIsFull())
            cars = carsApiService.getCarsFullInfo();
        else cars = carsApiService.getCarsInfo();
        Stream<? extends ICarInfo> carsStream = cars.stream();
        if (carRequestParameters.getCountry() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getCountry().matches(carRequestParameters.getCountry()));
        }
        if (carRequestParameters.getSegment() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getSegment().matches(carRequestParameters.getSegment()));
        }
        if (carRequestParameters.getMinEngineDisplacement() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getEngineDisplacement() >= carRequestParameters.getMinEngineDisplacement());
        }
        if (carRequestParameters.getMinEngineHorsepower() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getEngineDisplacement() >= carRequestParameters.getMinEngineHorsepower());
        }
        if (carRequestParameters.getMinMaxSpeed() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getEngineDisplacement() >= carRequestParameters.getMinMaxSpeed());
        }
        if (carRequestParameters.getSearch() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getModel().matches(carRequestParameters.getSearch()) ||
                    carInfo.getGeneration().matches(carRequestParameters.getSearch()) ||
                    carInfo.getModification().matches(carRequestParameters.getSearch()));
        }
        if (carRequestParameters.getYear() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getStartYear() <= carRequestParameters.getYear() &&
                    carInfo.getEndYear() >= carRequestParameters.getYear());
        }
        if (carRequestParameters.getBodyStyle() != null) {
            carsStream = carsStream.filter(carInfo -> carInfo.getBodyStyle().matches(carRequestParameters.getBodyStyle()));
        }
        return ResponseEntity.ok(carsStream.collect(Collectors.toList()));
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        Set<FuelType> fuelTypeHashSet = new HashSet<>();
        carsApiService.getCarsFullInfo().forEach(fullInfo -> fuelTypeHashSet.add(fullInfo.getEngineCharacteristics().getFuelType()));
        List<String> fuelTypes = fuelTypeHashSet.stream().map(Enum::toString).collect(Collectors.toList());
        return ResponseEntity.ok(fuelTypes);
    }

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        Set<String> stylesSet = new HashSet<>();
        carsApiService.getCarsFullInfo().forEach(carInfo -> stylesSet.add(carInfo.getBodyStyle()));
        return ResponseEntity.ok(new ArrayList<>(stylesSet));
    }

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        Set<EngineType> tengineTypeHashSet = new HashSet<>();
        carsApiService.getCarsFullInfo().forEach(carFullInfo -> tengineTypeHashSet.add(carFullInfo.getEngineCharacteristics().getEngineType()));
        return ResponseEntity.ok(tengineTypeHashSet.stream().map(Enum::toString).collect(Collectors.toList()));
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        Set<WheelDriveType> wheelDriveTypeHashSet = new HashSet<>();
        carsApiService.getCarsFullInfo().forEach(carFullInfo -> wheelDriveTypeHashSet.add(carFullInfo.getWheelDriveType()));
        return ResponseEntity.ok(wheelDriveTypeHashSet.stream().map(Enum::toString).collect(Collectors.toList()));
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        Set<GearboxType> gearboxTypeHashSet = new HashSet<>();
        carsApiService.getCarsFullInfo().forEach(carFullInfo -> gearboxTypeHashSet.add(carFullInfo.getGearboxType()));
        return ResponseEntity.ok(gearboxTypeHashSet.stream().map(Enum::toString).collect(Collectors.toList()));
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
        final String model = requestParameters.getModel();
        final String brand = requestParameters.getBrand();
        if (model != null && brand != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final OptionalDouble result = model != null
                ? carsApiService.getCarsFullInfo().stream()
                .filter(carFullInfo -> carFullInfo.getModel().equals(model))
                .mapToInt(CarFullInfo::getMaxSpeed)
                .average()
                : carsApiService.getCarsFullInfo().stream()
                .filter(carFullInfo -> carFullInfo.getBrand().getTitle().equals(brand))
                .mapToInt(CarFullInfo::getMaxSpeed)
                .average();
        if (result.isPresent()) {
            return ResponseEntity.ok(result.getAsDouble());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
