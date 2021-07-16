
package com.github.ct.slacksoff.interfaces.dto.sonar.issuessearch;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "startLine",
    "endLine",
    "startOffset",
    "endOffset"
})
@Generated("jsonschema2pojo")
@Data
public class TextRangeDto {
    @JsonProperty("startLine")
    private Integer startLine;
    @JsonProperty("endLine")
    private Integer endLine;
    @JsonProperty("startOffset")
    private Integer startOffset;
    @JsonProperty("endOffset")
    private Integer endOffset;
}
