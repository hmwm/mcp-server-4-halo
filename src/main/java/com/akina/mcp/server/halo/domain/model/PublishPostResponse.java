package com.akina.mcp.server.halo.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublishPostResponse {

    @JsonProperty(required = true, value = "code")
    @JsonPropertyDescription("code")
    private Integer code;

    @JsonProperty(required = true, value = "message")
    @JsonPropertyDescription("message")
    private String message;

    @JsonProperty(required = true, value = "data")
    @JsonPropertyDescription("data")
    private PublishPostData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PublishPostData {
        /** 针对哪篇文章 */
        @JsonProperty(required = true, value = "postId")
        @JsonPropertyDescription("文章唯一标识")
        private String postId;

        /** 发布后的访问地址 */
        @JsonProperty(required = true, value = "permalink")
        @JsonPropertyDescription("发布后的访问地址")
        private String permalink;

        /** 发布时间 */
        @JsonProperty(required = true, value = "publishTime")
        @JsonPropertyDescription("发布时间")
        private Instant publishTime;

        /** 状态确认 */
        @JsonProperty(required = true, value = "success")
        @JsonPropertyDescription("状态确认")
        private boolean success;
    }
}
