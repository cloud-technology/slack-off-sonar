package com.github.ct.slacksoff.interfaces.dto.sonar.measures.component;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "metric", "value", "bestValue", "periods", "period" })
@Generated("jsonschema2pojo")
@Data
public class MeasureDto {
    @JsonProperty("metric")
    private String metric;
    @JsonProperty("value")
    private String value;
    @JsonProperty("bestValue")
    private Boolean bestValue;
    @JsonProperty("periods")
    private List<PeriodDto> periods = null;
    @JsonProperty("period")
    private PeriodDto period;
}
