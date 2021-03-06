package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo implements ICarInfo{
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("model")
    private String model;

    @JsonProperty("country")
    private String country;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;

    @JsonIgnore
    private CarFullInfo carFullInfo;

    @JsonIgnore
    @Override
    public Integer getEngineDisplacement() {
        return carFullInfo.getEngineDisplacement();
    }

    @JsonIgnore
    @Override
    public Integer getHorsePower() {
        return carFullInfo.getHorsePower();
    }

    @JsonIgnore
    @Override
    public Integer getStartYear() {
        return carFullInfo.getStartYear();
    }

    @JsonIgnore
    @Override
    public Integer getEndYear() {
        return carFullInfo.getEndYear();
    }

    @JsonIgnore
    @Override
    public String getBodyStyle() {
        return carFullInfo.getBodyStyle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarInfo carInfo = (CarInfo) o;
        return Objects.equals(getId(), carInfo.getId()) &&
                Objects.equals(getSegment(), carInfo.getSegment()) &&
                Objects.equals(getBrand(), carInfo.getBrand()) &&
                Objects.equals(getModel(), carInfo.getModel()) &&
                Objects.equals(getCountry(), carInfo.getCountry()) &&
                Objects.equals(getGeneration(), carInfo.getGeneration()) &&
                Objects.equals(getModification(), carInfo.getModification()) &&
                Objects.equals(getCarFullInfo(), carInfo.getCarFullInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSegment(), getBrand(), getModel(), getCountry(), getGeneration(), getModification(), getCarFullInfo());
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "id=" + id +
                ", segment='" + segment + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", country='" + country + '\'' +
                ", generation='" + generation + '\'' +
                ", modification='" + modification + '\'' +
                '}';
    }
}
