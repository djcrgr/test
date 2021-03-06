package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EngineCharacteristics {
    @JsonProperty("engine_type")
    private FuelType fuelType;

    @JsonProperty("engine_cylinders")
    private EngineType engineType;

    @JsonProperty("engine_displacement")
    private Integer engineDisplacement;

    @JsonProperty("engine_horsepower")
    private Integer horsePower;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EngineCharacteristics that = (EngineCharacteristics) o;
        return getFuelType() == that.getFuelType() &&
                getEngineType() == that.getEngineType() &&
                Objects.equals(getEngineDisplacement(), that.getEngineDisplacement()) &&
                Objects.equals(getHorsePower(), that.getHorsePower());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFuelType(), getEngineType(), getEngineDisplacement(), getHorsePower());
    }

    @Override
    public String toString() {
        return "EngineCharacteristics{" +
                "fuelType=" + fuelType +
                ", engineType=" + engineType +
                ", engineDisplacement=" + engineDisplacement +
                ", horsePower=" + horsePower +
                '}';
    }
}
