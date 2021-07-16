package com.github.ct;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ct.component.MyJsonMessageConverter;
import com.github.ct.slacksoff.application.internal.outboundservices.GitlabClient;
import com.github.ct.slacksoff.application.internal.outboundservices.SonarClient;
import com.github.ct.slacksoff.interfaces.dto.sonar.hotspotssearch.HotspotsSearchResultDto;
import com.github.ct.slacksoff.interfaces.dto.sonar.issuessearch.IssuesSearchResultDto;
import com.github.ct.slacksoff.interfaces.dto.sonar.measures.component.MeasureDto;
import com.github.ct.slacksoff.interfaces.dto.sonar.measures.component.MeasuresComponentDto;
import com.github.ct.slacksoff.interfaces.dto.sonar.webhook.SonarWebHookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class SlackOffSonarApplication {
	@Autowired
	private GitlabClient gitlabClient;
	@Autowired
	private SonarClient sonarClient;
	@Value("${gitlabToken}")
	private String gitlabToken;
	@Value("${sonarToken}")
	private String sonarToken;
	private final String line_separator = "  \r\n";
	private final String metricKeys = "new_bugs,bugs,new_coverage,coverage,new_vulnerabilities,vulnerabilities,new_duplicated_lines_density,duplicated_lines_density";

	public static void main(String[] args) {
		SpringApplication.run(SlackOffSonarApplication.class, args);
	}

	// @Bean
	// public Function<String, String> uppercase() {
	// 	return value -> value.toUpperCase();
	// }

	@Bean
	public MyJsonMessageConverter customMessageConverter() {
		return new MyJsonMessageConverter(new JacksonMapper(new ObjectMapper()));
	}

	@Bean
	public Consumer<SonarWebHookDto> sonarWebhook() {
		return sonarWebHookDto -> {
			log.info("input requestBody={}", sonarWebHookDto);
			StringBuffer note = new StringBuffer();
			// 第一件事情先去取得結果
			String componentKeys = sonarWebHookDto.getProject().getKey();
			log.info("componentKeys={}", componentKeys);
			log.info("properties={}", sonarWebHookDto.getProperties());

			IssuesSearchResultDto issuesSearchResultDto = sonarClient
					.searchIssues(String.format("Basic %s", sonarToken), componentKeys, "", 500);
			HotspotsSearchResultDto hotspotsSearchResultDto = sonarClient
					.searchHotspots(String.format("Basic %s", sonarToken), componentKeys, 500);
			MeasuresComponentDto measuresComponentDto = sonarClient
					.getMeasuresComponent(String.format("Basic %s", sonarToken), componentKeys, metricKeys);

			List<MeasureDto> measures = measuresComponentDto.getComponent().getMeasures();

			MeasureDto measureDtoBugs = measures.stream().filter(measure -> "bugs".equals(measure.getMetric()))
					.findFirst().get();

			MeasureDto measureDtoVulnerabilities = measures.stream()
					.filter(measure -> "vulnerabilities".equals(measure.getMetric())).findFirst().get();
			MeasureDto measureDtoCoverage = measures.stream().filter(measure -> "coverage".equals(measure.getMetric()))
					.findFirst().get();
			MeasureDto measureDtoDuplicatedLinesDensity = measures.stream()
					.filter(measure -> "duplicated_lines_density".equals(measure.getMetric())).findFirst().get();

			log.info("issuesSearchResultDto={}", issuesSearchResultDto);

			note.append("## SonarQube Code Analysis" + line_separator);
			note.append("### Quality Gate" + line_separator);
			// OK, ERROR
			note.append(sonarWebHookDto.getQualityGate().getStatus() + line_separator);
			note.append("### Additional information" + line_separator);
			note.append("Line1" + line_separator);
			note.append("Line2" + line_separator);
			note.append("Line3" + line_separator);
			note.append("#### " + issuesSearchResultDto.getIssues().size() + " Issues" + line_separator);
			note.append(measureDtoBugs.getValue() + " Bugs" + line_separator);

			note.append(measureDtoVulnerabilities.getValue() + " Vulnerabilities (and "
					+ hotspotsSearchResultDto.getHotspots().size() + " Security Hostpots to review)" + line_separator);
			note.append(issuesSearchResultDto.getIssues().stream().filter(issue -> issue.getType().equals("CODE_SMELL"))
					.collect(Collectors.toList()).size() + " Code Smells" + line_separator);
			//
			note.append("#### Coverage and Duplications" + line_separator);
			note.append(measureDtoCoverage.getValue() + "% Coverage" + line_separator);
			note.append(measureDtoDuplicatedLinesDensity.getValue() + "% Duplication" + line_separator);

			Integer projectid = Integer.valueOf(sonarWebHookDto.getProperties().getSonarAnalysisProjectID());
			String sha = sonarWebHookDto.getProperties().getSonarAnalysisCommitSha();
			String mrid = sonarWebHookDto.getProperties().getSonarAnalysisMergeRequestIID();

			String createResult = null;
			// 一般 push
			if (StringUtils.hasText(sonarWebHookDto.getProperties().getSonarAnalysisCommitBranch())) {
				createResult = gitlabClient.createCommitComments(String.format("Bearer %s", gitlabToken), projectid,
						sha, note.toString());
			}
			if (StringUtils.hasText(mrid)) {
				createResult = gitlabClient.createMergeRequestsNotes(String.format("Bearer %s", gitlabToken), projectid,
						mrid, note.toString());
			}
			log.info("createResult={}", createResult);

		};
	}

}
