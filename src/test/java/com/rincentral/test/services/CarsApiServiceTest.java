package com.rincentral.test.services;

import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CarsApiServiceTest {

    @MockBean
    private ExternalCarsApiService externalCarsApiService;

    @Autowired
    private CarsApiService carsApiService;

    private static List<ExternalCar> externalCarList;
    private static List<ExternalBrand> externalBrandList;
    private static ExternalCarInfo externalCarInfo;
    private static CarFullInfo carFullInfo;
    private static CarInfo carInfo;

    @BeforeEach
    public void init() {
        externalCarInfo = new ExternalCarInfo(2, "E-segment", 1, "C-class", "W124", "E500", "2000-present",
                FuelType.GASOLINE, EngineType.L4, 100, 100, GearboxType.AUTO, WheelDriveType.AWD, 100, 100, 100, "sedan"
                , 1.0, 100);
        externalCarList = Collections.singletonList(new ExternalCar(2, "E-segment", 1, "C-class", "W124", "E500"));
        ExternalBrand externalBrand = new ExternalBrand(1, "Mercedes", "Germany");
        externalBrandList = new ArrayList<>();
        externalBrandList.add(externalBrand);
        carFullInfo = new CarFullInfo(2, "E-segment", new ExternalBrand(1, "Mercedes", "Germany"),
                "C-class", "W124", "E500", "2000-present",
                new EngineCharacteristics(FuelType.GASOLINE, EngineType.L4,
                        100, 100), GearboxType.AUTO, WheelDriveType.AWD,
                new BodyCharacteristics(100, 100, 100, "sedan"), 1.0, 100);
        carInfo = new CarInfo(2, "E-segment", "Mercedes", "", "Germany", "W124", "E500", carFullInfo);
    }

    @Test
    void getCarsInfo() {
        doReturn(externalCarList).when(externalCarsApiService).loadAllCars();
        doReturn(externalBrandList).when(externalCarsApiService).loadAllBrands();
        doReturn(externalCarInfo).when(externalCarsApiService).loadCarInformationById(2);
        assertEquals(carInfo.toString(), carsApiService.getCarsInfo().get(0).toString());
    }

    @Test
    void getCarsFullInfo() {
        doReturn(externalCarList).when(externalCarsApiService).loadAllCars();
        doReturn(externalBrandList).when(externalCarsApiService).loadAllBrands();
        doReturn(externalCarInfo).when(externalCarsApiService).loadCarInformationById(2);
        assertEquals(carFullInfo.toString(), carsApiService.getCarsFullInfo().get(0).toString());
    }
}