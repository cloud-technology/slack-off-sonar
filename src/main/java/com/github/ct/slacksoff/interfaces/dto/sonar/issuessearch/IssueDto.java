package com.github.ct.slacksoff.interfaces.dto.sonar.issuessearch;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "rule",
    "severity",
    "component",
    "project",
    "line",
    "hash",
    "textRange",
    "flows",
    "status",
    "message",
    "effort",
    "debt",
    "author",
    "tags",
    "creationDate",
    "updateDate",
    "type",
    "organization",
    "scope"
})
@Generated("jsonschema2pojo")
@Data
public class IssueDto {
    @JsonProperty("key")
    private String key;
    @JsonProperty("rule")
    private String rule;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("component")
    private String component;
    @JsonProperty("project")
    private String project;
    @JsonProperty("line")
    private Integer line;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("textRange")
    private TextRangeDto textRange;
    @JsonProperty("flows")
    private List<Object> flows = null;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("effort")
    private String effort;
    @JsonProperty("debt")
    private String debt;
    @JsonProperty("author")
    private String author;
    @JsonProperty("tags")
    private List<String> tags = null;
    @JsonProperty("creationDate")
    private String creationDate;
    @JsonProperty("updateDate")
    private String updateDate;
    @JsonProperty("type")
    private String type;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("scope")
    private String scope;
}
