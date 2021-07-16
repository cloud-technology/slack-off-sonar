package com.github.ct.slacksoff.interfaces.dto.sonar.hotspotssearch;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "paging", "hotspots", "components" })
@Generated("jsonschema2pojo")
@Data
public class HotspotsSearchResultDto {
    @JsonProperty("paging")
    private PagingDto paging;
    @JsonProperty("hotspots")
    private List<HotspotDto> hotspots = null;
    @JsonProperty("components")
    private List<ComponentDto> components = null;
}
