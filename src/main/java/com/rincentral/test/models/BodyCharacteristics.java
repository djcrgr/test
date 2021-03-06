package com.rincentral.test.models;

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
public class BodyCharacteristics {
    @JsonProperty("body-length")
    private Integer bodyLength;

    @JsonProperty("body-width")
    private Integer bodyWidth;

    @JsonProperty("body_height")
    private Integer bodyHeight;

    @JsonProperty("body_style")
    private String bodyStyle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyCharacteristics that = (BodyCharacteristics) o;
        return Objects.equals(getBodyLength(), that.getBodyLength()) &&
                Objects.equals(getBodyWidth(), that.getBodyWidth()) &&
                Objects.equals(getBodyHeight(), that.getBodyHeight()) &&
                Objects.equals(getBodyStyle(), that.getBodyStyle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBodyLength(), getBodyWidth(), getBodyHeight(), getBodyStyle());
    }

    @Override
    public String toString() {
        return "BodyCharacteristics{" +
                "bodyLength=" + bodyLength +
                ", bodyWidth=" + bodyWidth +
                ", bodyHeight=" + bodyHeight +
                ", bodyStyle='" + bodyStyle + '\'' +
                '}';
    }
}
