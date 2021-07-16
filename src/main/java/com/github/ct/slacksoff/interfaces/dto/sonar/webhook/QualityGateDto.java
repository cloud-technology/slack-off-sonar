
package com.github.ct.slacksoff.interfaces.dto.sonar.webhook;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "status",
    "conditions"
})
@Generated("jsonschema2pojo")
@Data
public class QualityGateDto {

    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("conditions")
    private List<ConditionDto> conditions = null;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("conditions")
    public List<ConditionDto> getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(List<ConditionDto> conditions) {
        this.conditions = conditions;
    }

}
