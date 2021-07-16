package com.github.ct.slacksoff.interfaces.dto.sonar.measures.component;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "component" })
@Generated("jsonschema2pojo")
@Data
public class MeasuresComponentDto {
    @JsonProperty("component")
    private ComponentDto component;
}

/**
 * https://sonarqube.cathaycloudteam.net/web_api/api/measures/component
{
    "component": {
        "id": "AXqBQDyWgw4Ml22n_WVF",
        "key": "dev.samzhu:easycron",
        "name": "easycron",
        "qualifier": "TRK",
        "measures": [
            {
                "metric": "bugs",
                "value": "11",
                "bestValue": false
            },
            {
                "metric": "new_bugs",
                "periods": [
                    {
                        "index": 1,
                        "value": "0",
                        "bestValue": true
                    }
                ],
                "period": {
                    "index": 1,
                    "value": "0",
                    "bestValue": true
                }
            },
            {
                "metric": "duplicated_lines_density",
                "value": "0.0",
                "bestValue": true
            },
            {
                "metric": "new_vulnerabilities",
                "periods": [
                    {
                        "index": 1,
                        "value": "0",
                        "bestValue": true
                    }
                ],
                "period": {
                    "index": 1,
                    "value": "0",
                    "bestValue": true
                }
            },
            {
                "metric": "vulnerabilities",
                "value": "0",
                "bestValue": true
            },
            {
                "metric": "coverage",
                "value": "0.0",
                "bestValue": false
            }
        ]
    }
}
 */