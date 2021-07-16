package com.github.ct.slacksoff.application.internal.outboundservices;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "gitlab", url = "https://gitlab.com/api/v4")
public interface GitlabClient {

        /**
         * 列出專案 issues https://docs.gitlab.com/ee/api/issues.html#list-project-issues
         * 
         * @param authorization
         * @param projectid
         * @return
         */
        @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectid}/issues")
        String listIssue(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid);

        /**
         * 在 Commit 下留言
         * https://docs.gitlab.com/ee/api/commits.html#post-comment-to-commit
         * 
         * @param authorization
         * @param projectid
         * @param sha
         * @param note
         * @return
         */
        @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectid}/repository/commits/{sha}/comments", consumes = APPLICATION_FORM_URLENCODED_VALUE)
        String createCommitComments(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid, @PathVariable("sha") String sha,
                        @RequestParam("note") String note);

        /**
         * 在 Merge request 註記
         * https://docs.gitlab.com/ee/api/notes.html#create-new-merge-request-note
         * 
         * @param authorization
         * @param projectid
         * @param mergeRequestIid
         * @param body
         * @return
         */
        @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectid}/merge_requests/{mergeRequestIid}/notes", consumes = APPLICATION_FORM_URLENCODED_VALUE)
        String createMergeRequestsNotes(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid,
                        @PathVariable("mergeRequestIid") String mergeRequestIid, @RequestParam("body") String body);

}
