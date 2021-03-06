package com.rincentral.test.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rincentral.test.models.external.ExternalCarInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExternalCarsApiServiceTest {

    @Autowired
    private ExternalCarsApiService externalCarsApiService;

    @Test
    void loadCarInformationById() throws JsonProcessingException {
        String jsonString = "{\"id\":1,\"segment\":\"A-segment\",\"brand_id\":9 ,\"model\":\"Picanto\",\"generation\":\"" +
                "III generation\",\"modification\":\"1.2 GT Line\",\"year_range\":\"2017-present\",\"engine_type\":\"" +
                "GASOLINE\",\"engine_cylinders\":\"L4\",\"engine_displacement\":1248,\"engine_horsepower\":84,\"gearbox\"" +
                ":\"AUTO\",\"wheel_drive\":\"FWD\",\"body_length\":3595,\"body_width\":1595,\"body_height\":1495," +
                "\"body_style\":\"Hatchback\",\"acceleration\":13.7,\"max_speed\":161}";
        ExternalCarInfo realCar = new ObjectMapper().readValue(jsonString, ExternalCarInfo.class);
        Assert.assertEquals(realCar, externalCarsApiService.loadCarInformationById(1));
    }
}