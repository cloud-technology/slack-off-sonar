package com.github.ct.slacksoff.interfaces.dto.sonar.measures.component;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "key", "name", "qualifier", "measures" })
@Generated("jsonschema2pojo")
@Data
public class ComponentDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("qualifier")
    private String qualifier;
    @JsonProperty("measures")
    private List<MeasureDto> measures = null;
}
