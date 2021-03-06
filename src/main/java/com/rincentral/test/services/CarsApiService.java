package com.rincentral.test.services;

import com.rincentral.test.models.*;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.*;

@Service                        //already singleton by default
public class CarsApiService {


    private final ExternalCarsApiService externalCarApi;
    private final List<CarFullInfo> fullInfoList = new ArrayList<>();
    private final List<CarInfo> infoList = new ArrayList<>();
    private final Map<Integer, ExternalBrand> brandMap = new HashMap<>();
    private List<ExternalBrand> externalBrandList;
    private List<ExternalCar> externalCars;
    private ExternalCarInfo externalCarInfo;


    @Autowired
    public CarsApiService(ExternalCarsApiService externalCarApi) {
        this.externalCarApi = externalCarApi;
    }

    @PostConstruct
    private void init() {
        externalCars = externalCarApi.loadAllCars();
        externalBrandList = externalCarApi.loadAllBrands();
        for (ExternalBrand brand : externalBrandList) {
            brandMap.put(brand.getId(), brand);
        }
        for (ExternalCar externalCar : externalCars) {
            externalCarInfo = externalCarApi.loadCarInformationById(externalCar.getId());
            fullInfoList.add(new CarFullInfo(externalCarInfo.getId(), externalCarInfo.getSegment(), brandMap.get(externalCarInfo.getBrandId()),
                    externalCarInfo.getModel(), externalCarInfo.getGeneration(), externalCarInfo.getModification(),
                    externalCarInfo.getYearsRange(), new EngineCharacteristics(externalCarInfo.getFuelType(), externalCarInfo.getEngineType(),
                    externalCarInfo.getEngineDisplacement(), externalCarInfo.getHp()), externalCarInfo.getGearboxType(), externalCarInfo.getWheelDriveType(),
                    new BodyCharacteristics(externalCarInfo.getBodyLength(), externalCarInfo.getBodyWidth(), externalCarInfo.getBodyHeight(),
                            externalCarInfo.getBodyStyle()), externalCarInfo.getAcceleration(), externalCarInfo.getMaxSpeed()));
            infoList.add(new CarInfo(externalCarInfo.getId(), externalCarInfo.getSegment(), brandMap.get(externalCarInfo.getBrandId()).getTitle(), externalCarInfo.getModel(),
                    brandMap.get(externalCarInfo.getBrandId()).getCountry(), externalCarInfo.getGeneration(), externalCarInfo.getModification(),
                    fullInfoList.get(fullInfoList.size() - 1)));//oubEx
        }
    }

    public List<CarInfo> getCarsInfo() {
        init();
        return infoList;
    }

    public List<CarFullInfo> getCarsFullInfo() {
        init();
        return fullInfoList;
    }
}
