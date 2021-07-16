package com.github.ct.slacksoff.interfaces.dto.sonar.issuessearch;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "p",
    "ps",
    "paging",
    "effortTotal",
    "debtTotal",
    "issues",
    "components",
    "facets"
})
@Generated("jsonschema2pojo")
@Data
public class IssuesSearchResultDto {
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("p")
    private Integer p;
    @JsonProperty("ps")
    private Integer ps;
    @JsonProperty("paging")
    private PagingDto paging;
    @JsonProperty("effortTotal")
    private Integer effortTotal;
    @JsonProperty("debtTotal")
    private Integer debtTotal;
    @JsonProperty("issues")
    private List<IssueDto> issues = null;
    @JsonProperty("components")
    private List<ComponentDto> components = null;
    @JsonProperty("facets")
    private List<Object> facets = null;
}
