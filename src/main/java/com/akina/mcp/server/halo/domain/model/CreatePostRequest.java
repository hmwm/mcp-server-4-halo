package com.akina.mcp.server.halo.domain.model;

import com.akina.mcp.server.halo.type.utils.MarkdownConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostRequest {

    /** 标题 */
    @JsonProperty(required = true, value = "title")
    @JsonPropertyDescription("文章标题")
    private String title;

    /** 固定链接 slug，不传则随机 */
    @JsonProperty(required = true, value = "slug")
    @JsonPropertyDescription("随机图")
    private String slug;

    /** 原始 Markdown（业务层传 markdown 即可） */
    @JsonProperty(required = true, value = "Markdown")
    @JsonPropertyDescription("文章内容")
    private String markdown;

    /** 分类 */
    @JsonProperty(required = true, value = "categories")
    @JsonPropertyDescription("文章分类")
    private List<String> categories;

    /** 标签 */
    @JsonProperty(required = true, value = "tags")
    @JsonPropertyDescription("文章标签")
    private List<String> tags;

    /** 是否立即发布（否则创建草稿） */
    @JsonProperty(required = true, value = "publishNow")
    @JsonPropertyDescription("是否发布")
    private boolean publishNow;

    public String getContent() {
        return MarkdownConverter.convertHtml(markdown);
    }
}
