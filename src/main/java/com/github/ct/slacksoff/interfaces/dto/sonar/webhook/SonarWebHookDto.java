package com.github.ct.slacksoff.interfaces.dto.sonar.webhook;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "serverUrl",
    "taskId",
    "status",
    "analysedAt",
    "revision",
    "changedAt",
    "project",
    "branch",
    "qualityGate",
    "properties"
})
@Generated("jsonschema2pojo")
@Data
public class SonarWebHookDto {
    @JsonProperty("serverUrl")
    private String serverUrl;
    @JsonProperty("taskId")
    private String taskId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("analysedAt")
    private String analysedAt;
    @JsonProperty("revision")
    private String revision;
    @JsonProperty("changedAt")
    private String changedAt;
    @JsonProperty("project")
    private ProjectDto project;
    @JsonProperty("branch")
    private BranchDto branch;
    @JsonProperty("qualityGate")
    private QualityGateDto qualityGate;
    @JsonProperty("properties")
    private PropertiesDto properties;

    @JsonProperty("serverUrl")
    public String getServerUrl() {
        return serverUrl;
    }

    @JsonProperty("serverUrl")
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @JsonProperty("taskId")
    public String getTaskId() {
        return taskId;
    }

    @JsonProperty("taskId")
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("analysedAt")
    public String getAnalysedAt() {
        return analysedAt;
    }

    @JsonProperty("analysedAt")
    public void setAnalysedAt(String analysedAt) {
        this.analysedAt = analysedAt;
    }

    @JsonProperty("revision")
    public String getRevision() {
        return revision;
    }

    @JsonProperty("revision")
    public void setRevision(String revision) {
        this.revision = revision;
    }

    @JsonProperty("changedAt")
    public String getChangedAt() {
        return changedAt;
    }

    @JsonProperty("changedAt")
    public void setChangedAt(String changedAt) {
        this.changedAt = changedAt;
    }

    @JsonProperty("project")
    public ProjectDto getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(ProjectDto project) {
        this.project = project;
    }

    @JsonProperty("branch")
    public BranchDto getBranch() {
        return branch;
    }

    @JsonProperty("branch")
    public void setBranch(BranchDto branch) {
        this.branch = branch;
    }

    @JsonProperty("qualityGate")
    public QualityGateDto getQualityGate() {
        return qualityGate;
    }

    @JsonProperty("qualityGate")
    public void setQualityGate(QualityGateDto qualityGate) {
        this.qualityGate = qualityGate;
    }

    @JsonProperty("properties")
    public PropertiesDto getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(PropertiesDto properties) {
        this.properties = properties;
    }

}
