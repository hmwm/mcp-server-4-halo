package com.akina.mcp.server.halo.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.*;

/**
 * 创建文章请求对象
 */
@Data
public class CreatePostRequestDTO {

    private Post post;
    private Content content;
    
    @Data
    public static class Post {

        private String apiVersion = "content.halo.run/v1alpha1";
        private String kind = "Post";
        private Metadata metadata;
        private PostSpec spec;
    }
    
    @Data
    public static class Metadata {

        private String name; // 对应文章唯一标识（postId），业务层生成或传入
        private Map<String, String> annotations = Collections.emptyMap();
    }
    
    @Data
    public static class PostSpec {

        private String title; // 文章标题
        private String slug; // 文章别名，业务层生成
        private String template = "";
        private String cover = "";
        private Boolean deleted = false;
        private Boolean publish = false;
        private Instant publishTime;
        private Boolean pinned = false;
        private Boolean allowComment = true;
        private String visible = "PUBLIC";
        private Integer priority = 0;
        private Excerpt excerpt = new Excerpt();// 自动生成摘要
        private List<String> categories = Collections.emptyList();
        private List<String> tags; // Halo 必须传入
        private List<Object> htmlMetas = Collections.emptyList();
        private String owner; // 作者，业务层赋值
    }
    
    @Data
    public static class Excerpt {

        private Boolean autoGenerate = true;
        private String raw = "";
    }
    
    @Data
    public static class Content {

        private String raw;  // markdown 原文
        private String content;  // html 转换后内容，业务层赋值
        private String rawType = "HTML";
    }
}