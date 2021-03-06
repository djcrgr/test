package com.rincentral.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.services.CarsApiService;
import com.rincentral.test.services.ExternalCarsApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private static ExternalCarsApiService service;
    @MockBean
    private CarsApiService carsApiService;

    private static List<ExternalCar> externalCarList;
    private static List<CarInfo> carInfoList;
    private static List<CarFullInfo> carFullInfoList;
    private static CarInfo carInfo;
    private static CarFullInfo carFullInfo;

    @BeforeAll
    static void init() {
        ExternalCar externalCar = new ExternalCar();
        externalCar.setId(1);
        externalCarList = new ArrayList<>();
        externalCarList.add(externalCar);
        carFullInfo = new CarFullInfo(2, "E-segment", new ExternalBrand(1, "Mercedes", "germany"),
                "C-class", "W124", "E500", "2000-present",
                new EngineCharacteristics(FuelType.GASOLINE, EngineType.L4,
                        5000, 200), GearboxType.AUTO, WheelDriveType.AWD,
                new BodyCharacteristics(11, 11, 11, "Sedan"), 1.0, 100);
        carFullInfoList = new ArrayList<>();
        carFullInfoList.add(carFullInfo);
        carInfo = new CarInfo(2, "E-segment", "Mercedes", "", "Germany", "W124", "E500", carFullInfo);
        carInfoList = new ArrayList<>();
        carInfoList.add(carInfo);
    }

    @Test
    void getAllEmployees() throws Exception {
        given(service.getAllPaginated(0, 10)).willReturn(externalCarList);
        this.mockMvc.perform(get("/api/paged?pageNo=1")).andExpect(status().is4xxClientError());
        this.mockMvc.perform(get("/api/paged")).andExpect(status().isOk());
    }

    @Test
    void getCars() throws Exception {
        given(carsApiService.getCarsInfo()).willReturn(carInfoList);
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        MvcResult result = this.mockMvc.perform(get("/api/cars")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        CarInfo expectedCar = new ObjectMapper().readValue(content.substring(1, content.length() - 1), CarInfo.class);
        assertEquals(carInfo.toString(), expectedCar.toString());

        this.mockMvc.perform(get("/api/cars?minEngineDisplacement=min")).andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/api/cars?minEngineHorsepower=min")).andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/api/cars?minMaxSpeed=min")).andExpect(status().isBadRequest());

        MvcResult resultForFull = this.mockMvc.perform(get("/api/cars?isFull=true")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String contentForFull = resultForFull.getResponse().getContentAsString();
        CarFullInfo expectedCarFull = new ObjectMapper().readValue(contentForFull.substring(1, contentForFull.length() - 1), CarFullInfo.class);
        assertEquals(carFullInfo, expectedCarFull);
    }

    @Test
    void testGetCars() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/cars?search=qqq")).andExpect(status().isOk()).andExpect(content().string("[]"));
        MvcResult result = this.mockMvc.perform(get("/api/cars?search=W124&isFull=true")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        CarFullInfo expectedCar = new ObjectMapper().readValue(content.substring(1, content.length() - 1), CarFullInfo.class);
        assertEquals(carFullInfo, expectedCar);
    }

    @Test
    void testYear() throws Exception {
        given(carsApiService.getCarsInfo()).willReturn(carInfoList);
        this.mockMvc.perform(get("/api/cars?year=1999")).andExpect(status().isOk()).andExpect(content().string("[]"));
        this.mockMvc.perform(get("/api/cars?year=2010")).andExpect(content()
                .string("[{\"id\":2,\"segment\":\"E-segment\",\"brand\":\"Mercedes\",\"model\":\"\"," +
                        "\"country\":\"Germany\",\"generation\":\"W124\",\"modification\":\"E500\"}]"));
    }

    @Test
    void testForRequestParameters() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/cars?country=germany&segment=E-segment&minEngineDisplacement=5000&minEngineHorsepower=200&minMaxSpeed=100&search=W124&isFull=true"))
                .andExpect(status().isOk()).andExpect(content()
                .string("[{\"id\":2,\"segment\":\"E-segment\",\"brand\":{\"id\":1,\"title\":\"Mercedes\"," +
                        "\"country\":\"germany\"},\"model\":\"C-class\",\"generation\":\"W124\",\"modification\":\"E500\"," +
                        "\"years_range\":\"2000-present\",\"engine_characteristics\":{\"engine_type\":\"GASOLINE\"," +
                        "\"engine_cylinders\":\"L4\",\"engine_displacement\":5000,\"engine_horsepower\":200},\"gearbox\":" +
                        "\"AUTO\",\"wheel_drive\":\"AWD\",\"body_characteristics\":{\"body-length\":11,\"body-width\":11,\"" +
                        "body_height\":11,\"body_style\":\"Sedan\"},\"acceleration\":1.0,\"max_speed\":100}]"));
    }

    @Test
    void getFuelTypes() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/fuel-types")).andExpect(content().string("[" + (char) 34
                + "GASOLINE" + (char) 34 + "]"));
    }

    @Test
    void getBodyStyles() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/body-styles")).andExpect(content().string("[" + (char) 34
                + "Sedan" + (char) 34 + "]"));
    }

    @Test
    void getEngineTypes() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/engine-types")).andExpect(content().string("[" + (char) 34
                + "L4" + (char) 34 + "]"));
    }

    @Test
    void getWheelDrives() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/wheel-drives")).andExpect(content().string("[" + (char) 34
                + "AWD" + (char) 34 + "]"));
    }

    @Test
    void getGearboxTypes() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        this.mockMvc.perform(get("/api/wheel-drives")).andExpect(content().string("[" + (char) 34
                + "AUTO" + (char) 34 + "]"));
    }

    @Test
    void getMaxSpeed() throws Exception {
        given(carsApiService.getCarsFullInfo()).willReturn(carFullInfoList);
        given(carsApiService.getCarsInfo()).willReturn(carInfoList);
        this.mockMvc.perform(get("/api/max-speed")).andExpect(status().isNotFound());
        this.mockMvc.perform(get("/api/max-speed?brand=audi&model=tt")).andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/api/max-speed?brand=Mercedes")).andExpect(content().string("100.0"));
    }
}