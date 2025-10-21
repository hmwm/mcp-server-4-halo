package com.akina.mcp.server.halo.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostResponse {

    @JsonProperty(required = true, value = "code")
    @JsonPropertyDescription("code")
    private Integer code;

    @JsonProperty(required = true, value = "message")
    @JsonPropertyDescription("message")
    private String message;

    @JsonProperty(required = true, value = "articleData")
    @JsonPropertyDescription("articleData")
    private ArticleData articleData;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ArticleData {

        /** 文章唯一标识（from halo）*/
        @JsonProperty(required = true, value = "postId")
        @JsonPropertyDescription("文章唯一标识")
        private String postId;

        /** 标题 */
        @JsonProperty(required = true, value = "title")
        @JsonPropertyDescription("文章标题")
        private String title;

        /** 是否已发布 */
        @JsonProperty(required = true, value = "published")
        @JsonPropertyDescription("是否已发布")
        private boolean published;

        /*
        都可能为空，所以不在这里使用
         */
//        /** 如果已发布则有 permalink */
//        @JsonProperty(required = true, value = "urlIfPublished")
//        @JsonPropertyDescription("文章链接")
//        private String urlIfPublished;
//
//        /** 供 LLM 复述展示的简短摘要 */
//        @JsonProperty(required = true, value = "summary")
//        @JsonPropertyDescription("简短摘要")
//        private String summary;
    }

}
