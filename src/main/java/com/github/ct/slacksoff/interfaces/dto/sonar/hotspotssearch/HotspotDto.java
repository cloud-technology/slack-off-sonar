package com.github.ct.slacksoff.interfaces.dto.sonar.hotspotssearch;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "key", "component", "project", "securityCategory", "vulnerabilityProbability", "status", "line",
        "message", "author", "creationDate", "updateDate" })
@Generated("jsonschema2pojo")
@Data
public class HotspotDto {
    @JsonProperty("key")
    private String key;
    @JsonProperty("component")
    private String component;
    @JsonProperty("project")
    private String project;
    @JsonProperty("securityCategory")
    private String securityCategory;
    @JsonProperty("vulnerabilityProbability")
    private String vulnerabilityProbability;
    @JsonProperty("status")
    private String status;
    @JsonProperty("line")
    private Integer line;
    @JsonProperty("message")
    private String message;
    @JsonProperty("author")
    private String author;
    @JsonProperty("creationDate")
    private String creationDate;
    @JsonProperty("updateDate")
    private String updateDate;
}
