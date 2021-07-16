package com.github.ct.slacksoff.application.internal.outboundservices;

import com.github.ct.slacksoff.interfaces.dto.sonar.hotspotssearch.HotspotsSearchResultDto;
import com.github.ct.slacksoff.interfaces.dto.sonar.issuessearch.IssuesSearchResultDto;
import com.github.ct.slacksoff.interfaces.dto.sonar.measures.component.MeasuresComponentDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sonarqube", url = "${sonarUrl}")
public interface SonarClient {

        /**
         * 取得該專案問題清單 https://${sonarqube_domain}/web_api/api/issues/search
         * 
         * @param authorization
         * @param componentKeys
         * @param types         類型
         * @param ps            分頁大小
         * @return
         */
        @RequestMapping(method = RequestMethod.GET, path = "/issues/search")
        IssuesSearchResultDto searchIssues(@RequestHeader("Authorization") String authorization,
                        @RequestParam("componentKeys") String componentKeys, @RequestParam("types") String types,
                        @RequestParam("ps") Integer ps);

        /**
         * 搜尋安全問題
         * https://${sonarqube_domain}/web_api/api/hotspots/search?internal=true
         * @param authorization
         * @param projectKey
         * @param ps
         * @return
         */
        @RequestMapping(method = RequestMethod.GET, path = "/hotspots/search")
        HotspotsSearchResultDto searchHotspots(@RequestHeader("Authorization") String authorization,
                        @RequestParam("projectKey") String projectKey, @RequestParam("ps") Integer ps);
        /**
         * 取得分析後指標
         * https://sonarqube.cathaycloudteam.net/web_api/api/measures/component
         * @param authorization
         * @param component
         * @param metricKeys
         * @return
         */
        @RequestMapping(method = RequestMethod.GET, path = "/measures/component")
        MeasuresComponentDto getMeasuresComponent(@RequestHeader("Authorization") String authorization,
                        @RequestParam("component") String component, @RequestParam("metricKeys") String metricKeys);

}
