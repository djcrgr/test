package com.rincentral.test.models.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCarInfo {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand_id")
    private Integer brandId;

    @JsonProperty("model")
    private String model;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;

    @JsonProperty("year_range")
    private String yearsRange;

    @JsonProperty("engine_type")
    private FuelType fuelType;

    @JsonProperty("engine_cylinders")
    private EngineType engineType;

    @JsonProperty("engine_displacement")
    private Integer engineDisplacement;

    @JsonProperty("engine_horsepower")
    private Integer hp;

    @JsonProperty("gearbox")
    private GearboxType gearboxType;

    @JsonProperty("wheel_drive")
    private WheelDriveType wheelDriveType;

    @JsonProperty("body_length")
    private Integer bodyLength;

    @JsonProperty("body_width")
    private Integer bodyWidth;

    @JsonProperty("body_height")
    private Integer bodyHeight;

    @JsonProperty("body_style")
    private String bodyStyle;

    @JsonProperty("acceleration")
    private Double acceleration;

    @JsonProperty("max_speed")
    private Integer maxSpeed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalCarInfo that = (ExternalCarInfo) o;
        return getId().equals(that.getId()) &&
                getSegment().equals(that.getSegment()) &&
                getBrandId().equals(that.getBrandId()) &&
                getModel().equals(that.getModel()) &&
                getGeneration().equals(that.getGeneration()) &&
                getModification().equals(that.getModification()) &&
                getYearsRange().equals(that.getYearsRange()) &&
                getFuelType() == that.getFuelType() &&
                getEngineType() == that.getEngineType() &&
                getEngineDisplacement().equals(that.getEngineDisplacement()) &&
                getHp().equals(that.getHp()) &&
                getGearboxType() == that.getGearboxType() &&
                getWheelDriveType() == that.getWheelDriveType() &&
                getBodyLength().equals(that.getBodyLength()) &&
                getBodyWidth().equals(that.getBodyWidth()) &&
                getBodyHeight().equals(that.getBodyHeight()) &&
                getBodyStyle().equals(that.getBodyStyle()) &&
                getAcceleration().equals(that.getAcceleration()) &&
                getMaxSpeed().equals(that.getMaxSpeed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModel());
    }

    @Override
    public String toString() {
        return "ExternalCarInfo{" +
                "id=" + id +
                ", segment='" + segment + '\'' +
                ", brandId=" + brandId +
                ", model='" + model + '\'' +
                ", generation='" + generation + '\'' +
                ", modification='" + modification + '\'' +
                ", yearsRange='" + yearsRange + '\'' +
                ", fuelType=" + fuelType +
                ", engineType=" + engineType +
                ", engineDisplacement=" + engineDisplacement +
                ", hp=" + hp +
                ", gearboxType=" + gearboxType +
                ", wheelDriveType=" + wheelDriveType +
                ", bodyLength=" + bodyLength +
                ", bodyWidth=" + bodyWidth +
                ", bodyHeight=" + bodyHeight +
                ", bodyStyle='" + bodyStyle + '\'' +
                ", acceleration=" + acceleration +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
