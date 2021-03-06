package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarFullInfo implements ICarInfo{
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand")
    private ExternalBrand brand;

    @JsonProperty("model")
    private String model;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;

    @JsonProperty("years_range")
    private String yearsRange;

    @JsonProperty("engine_characteristics")
    private EngineCharacteristics engineCharacteristics;

    @JsonProperty("gearbox")
    private GearboxType gearboxType;

    @JsonProperty("wheel_drive")
    private WheelDriveType wheelDriveType;

    @JsonProperty("body_characteristics")
    private BodyCharacteristics bodyCharacteristics;

    @JsonProperty("acceleration")
    private Double acceleration;

    @JsonProperty("max_speed")
    private Integer maxSpeed;

    @JsonIgnore
    @Override
    public String getCountry() {
        return brand.getCountry();
    }

    @JsonIgnore
    @Override
    public Integer getEngineDisplacement() {
        return engineCharacteristics.getEngineDisplacement();
    }

    @JsonIgnore
    @Override
    public Integer getHorsePower() {
        return engineCharacteristics.getHorsePower();
    }

    @JsonIgnore
    @Override
    public String getBodyStyle() {
        return bodyCharacteristics.getBodyStyle();
    }

    @JsonIgnore
    @Override
    public Integer getStartYear() {
        return Integer.parseInt(yearsRange.split("-")[0]);
    }

    @JsonIgnore
    @Override
    public Integer getEndYear() {
        String endYear = yearsRange.split("-")[1];
        if (endYear.equals("present")) {
            return Calendar.getInstance().get(Calendar.YEAR);
        } else {
            return Integer.parseInt(endYear);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarFullInfo that = (CarFullInfo) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getSegment(), that.getSegment()) &&
                Objects.equals(getBrand(), that.getBrand()) &&
                Objects.equals(getModel(), that.getModel()) &&
                Objects.equals(getGeneration(), that.getGeneration()) &&
                Objects.equals(getModification(), that.getModification()) &&
                Objects.equals(getYearsRange(), that.getYearsRange()) &&
                Objects.equals(getEngineCharacteristics(), that.getEngineCharacteristics()) &&
                getGearboxType() == that.getGearboxType() &&
                getWheelDriveType() == that.getWheelDriveType() &&
                Objects.equals(getBodyCharacteristics(), that.getBodyCharacteristics()) &&
                Objects.equals(getAcceleration(), that.getAcceleration()) &&
                Objects.equals(getMaxSpeed(), that.getMaxSpeed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSegment(), getBrand(), getModel(), getGeneration(), getModification(), getYearsRange(), getEngineCharacteristics(), getGearboxType(), getWheelDriveType(), getBodyCharacteristics(), getAcceleration(), getMaxSpeed());
    }

    @Override
    public String toString() {
        return "CarFullInfo{" +
                "id=" + id +
                ", segment='" + segment + '\'' +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", generation='" + generation + '\'' +
                ", modification='" + modification + '\'' +
                ", yearsRange='" + yearsRange + '\'' +
                ", engineCharacteristics=" + engineCharacteristics +
                ", gearboxType=" + gearboxType +
                ", wheelDriveType=" + wheelDriveType +
                ", bodyCharacteristics=" + bodyCharacteristics +
                ", acceleration=" + acceleration +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
