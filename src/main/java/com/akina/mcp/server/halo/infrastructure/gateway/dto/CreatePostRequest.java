package com.akina.mcp.server.halo.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 创建文章请求对象
 */
@Data
public class CreatePostRequest {
    
    @JsonProperty("post")
    private Post post;
    
    @JsonProperty("content")
    private Content content;
    
    @Data
    public static class Post {
        @JsonProperty("apiVersion")
        private String apiVersion = "content.halo.run/v1alpha1";
        
        @JsonProperty("kind")
        private String kind = "Post";
        
        @JsonProperty("metadata")
        private Metadata metadata;
        
        @JsonProperty("spec")
        private PostSpec spec;
    }
    
    @Data
    public static class Metadata {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("annotations")
        private Object annotations = new Object();
    }
    
    @Data
    public static class PostSpec {
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("slug")
        private String slug;
        
        @JsonProperty("template")
        private String template = "";
        
        @JsonProperty("cover")
        private String cover = "";
        
        @JsonProperty("deleted")
        private Boolean deleted = false;
        
        @JsonProperty("publish")
        private Boolean publish = false;
        
        @JsonProperty("publishTime")
        private String publishTime;
        
        @JsonProperty("pinned")
        private Boolean pinned = false;
        
        @JsonProperty("allowComment")
        private Boolean allowComment = true;
        
        @JsonProperty("visible")
        private String visible = "PUBLIC";
        
        @JsonProperty("priority")
        private Integer priority = 0;
        
        @JsonProperty("excerpt")
        private Excerpt excerpt;
        
        @JsonProperty("categories")
        private String[] categories = new String[0];
        
        @JsonProperty("tags")
        private String[] tags;
        
        @JsonProperty("htmlMetas")
        private Object[] htmlMetas = new Object[0];
        
        @JsonProperty("owner")
        private String owner;
    }
    
    @Data
    public static class Excerpt {
        @JsonProperty("autoGenerate")
        private Boolean autoGenerate = true;
        
        @JsonProperty("raw")
        private String raw = "";
    }
    
    @Data
    public static class Content {
        @JsonProperty("raw")
        private String raw;
        
        @JsonProperty("content")
        private String content;
        
        @JsonProperty("rawType")
        private String rawType = "HTML";
    }
}