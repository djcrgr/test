package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
