
package com.github.ct.slacksoff.interfaces.dto.sonar.issuessearch;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organization",
    "key",
    "uuid",
    "enabled",
    "qualifier",
    "name",
    "longName",
    "path"
})
@Generated("jsonschema2pojo")
@Data
public class ComponentDto {
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("key")
    private String key;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("qualifier")
    private String qualifier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("longName")
    private String longName;
    @JsonProperty("path")
    private String path;
}
