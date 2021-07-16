
package com.github.ct.slacksoff.interfaces.dto.sonar.webhook;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "metric",
    "operator",
    "value",
    "status",
    "errorThreshold"
})
@Generated("jsonschema2pojo")
@Data
public class ConditionDto {

    @JsonProperty("metric")
    private String metric;
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("value")
    private String value;
    @JsonProperty("status")
    private String status;
    @JsonProperty("errorThreshold")
    private String errorThreshold;

    @JsonProperty("metric")
    public String getMetric() {
        return metric;
    }

    @JsonProperty("metric")
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("errorThreshold")
    public String getErrorThreshold() {
        return errorThreshold;
    }

    @JsonProperty("errorThreshold")
    public void setErrorThreshold(String errorThreshold) {
        this.errorThreshold = errorThreshold;
    }

}
