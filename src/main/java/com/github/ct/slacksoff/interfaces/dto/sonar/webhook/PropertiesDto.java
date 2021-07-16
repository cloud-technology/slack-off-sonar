
package com.github.ct.slacksoff.interfaces.dto.sonar.webhook;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sonar.analysis.gitPlatform",
    "sonar.analysis.commitTitle",
    "sonar.analysis.commitBranch",
    "sonar.analysis.commitTAG",
    "sonar.analysis.projectID",
    "sonar.analysis.commitSha",
    "sonar.analysis.mergeRequestIID"
})
@Generated("jsonschema2pojo")
@Data
public class PropertiesDto {
    @JsonProperty("sonar\\.analysis\\.gitPlatform")
    private String sonarAnalysisGitPlatform;
    @JsonProperty("sonar.analysis.commitTitle")
    private String sonarAnalysisCommitTitle;
    @JsonProperty("sonar.analysis.commitBranch")
    private String sonarAnalysisCommitBranch;
    @JsonProperty("sonar.analysis.commitTAG")
    private String sonarAnalysisCommitTAG;
    @JsonProperty("sonar.analysis.projectID")
    private String sonarAnalysisProjectID;
    @JsonProperty("sonar.analysis.commitSha")
    private String sonarAnalysisCommitSha;
    @JsonProperty("sonar.analysis.mergeRequestIID")
    private String sonarAnalysisMergeRequestIID;

    @JsonProperty("sonar.analysis.gitPlatform")
    public String getSonarAnalysisGitPlatform() {
        return sonarAnalysisGitPlatform;
    }
    @JsonProperty("sonar.analysis.gitPlatform")
    public void setSonarAnalysisGitPlatform(String sonarAnalysisGitPlatform) {
        this.sonarAnalysisGitPlatform = sonarAnalysisGitPlatform;
    }
    @JsonProperty("sonar.analysis.commitTitle")
    public String getSonarAnalysisCommitTitle() {
        return sonarAnalysisCommitTitle;
    }
    @JsonProperty("sonar.analysis.commitTitle")
    public void setSonarAnalysisCommitTitle(String sonarAnalysisCommitTitle) {
        this.sonarAnalysisCommitTitle = sonarAnalysisCommitTitle;
    }
    @JsonProperty("sonar.analysis.commitBranch")
    public String getSonarAnalysisCommitBranch() {
        return sonarAnalysisCommitBranch;
    }
    @JsonProperty("sonar.analysis.commitBranch")
    public void setSonarAnalysisCommitBranch(String sonarAnalysisCommitBranch) {
        this.sonarAnalysisCommitBranch = sonarAnalysisCommitBranch;
    }
    @JsonProperty("sonar.analysis.commitTAG")
    public String getSonarAnalysisCommitTAG() {
        return sonarAnalysisCommitTAG;
    }
    @JsonProperty("sonar.analysis.commitTAG")
    public void setSonarAnalysisCommitTAG(String sonarAnalysisCommitTAG) {
        this.sonarAnalysisCommitTAG = sonarAnalysisCommitTAG;
    }
    @JsonProperty("sonar.analysis.projectID")
    public String getSonarAnalysisProjectID() {
        return sonarAnalysisProjectID;
    }
    @JsonProperty("sonar.analysis.projectID")
    public void setSonarAnalysisProjectID(String sonarAnalysisProjectID) {
        this.sonarAnalysisProjectID = sonarAnalysisProjectID;
    }
    @JsonProperty("sonar.analysis.commitSha")
    public String getSonarAnalysisCommitSha() {
        return sonarAnalysisCommitSha;
    }
    @JsonProperty("sonar.analysis.commitSha")
    public void setSonarAnalysisCommitSha(String sonarAnalysisCommitSha) {
        this.sonarAnalysisCommitSha = sonarAnalysisCommitSha;
    }
    @JsonProperty("sonar.analysis.mergeRequestIID")
    public String getSonarAnalysisMergeRequestIID() {
        return sonarAnalysisMergeRequestIID;
    }
    @JsonProperty("sonar.analysis.mergeRequestIID")
    public void setSonarAnalysisMergeRequestIID(String sonarAnalysisMergeRequestIID) {
        this.sonarAnalysisMergeRequestIID = sonarAnalysisMergeRequestIID;
    }
}
