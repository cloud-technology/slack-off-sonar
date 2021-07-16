package com.github.ct.slacksoff.interfaces.dto.sonar.hotspotssearch;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "organization", "key", "qualifier", "name", "longName", "path" })
@Generated("jsonschema2pojo")
@Data
public class ComponentDto {
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("key")
    private String key;
    @JsonProperty("qualifier")
    private String qualifier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("longName")
    private String longName;
    @JsonProperty("path")
    private String path;
}
