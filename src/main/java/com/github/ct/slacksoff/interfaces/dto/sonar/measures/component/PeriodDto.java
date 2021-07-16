package com.github.ct.slacksoff.interfaces.dto.sonar.measures.component;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "index", "value", "bestValue" })
@Generated("jsonschema2pojo")
@Data
public class PeriodDto {
    @JsonProperty("index")
    private Integer index;
    @JsonProperty("value")
    private String value;
    @JsonProperty("bestValue")
    private Boolean bestValue;
}
