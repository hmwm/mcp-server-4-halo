package com.akina.mcp.server.halo.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 发布文章响应对象
 */
@Data
public class PublishPostResponse {
    
    @JsonProperty("apiVersion")
    private String apiVersion;
    
    @JsonProperty("kind")
    private String kind;
    
    @JsonProperty("metadata")
    private ResponseMetadata metadata;
    
    @JsonProperty("spec")
    private ResponsePostSpec spec;
    
    @JsonProperty("status")
    private PostStatus status;
    
    @Data
    public static class ResponseMetadata {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("generateName")
        private String generateName;
        
        @JsonProperty("labels")
        private Map<String, String> labels;
        
        @JsonProperty("annotations")
        private Map<String, String> annotations;
        
        @JsonProperty("version")
        private Integer version;
        
        @JsonProperty("creationTimestamp")
        private LocalDateTime creationTimestamp;
        
        @JsonProperty("deletionTimestamp")
        private LocalDateTime deletionTimestamp;
        
        @JsonProperty("finalizers")
        private List<String> finalizers;
    }
    
    @Data
    public static class ResponsePostSpec {
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("slug")
        private String slug;
        
        @JsonProperty("baseSnapshot")
        private String baseSnapshot;
        
        @JsonProperty("headSnapshot")
        private String headSnapshot;
        
        @JsonProperty("releaseSnapshot")
        private String releaseSnapshot;
        
        @JsonProperty("owner")
        private String owner;
        
        @JsonProperty("template")
        private String template;
        
        @JsonProperty("cover")
        private String cover;
        
        @JsonProperty("deleted")
        private Boolean deleted;
        
        @JsonProperty("publish")
        private Boolean publish;
        
        @JsonProperty("publishTime")
        private LocalDateTime publishTime;
        
        @JsonProperty("pinned")
        private Boolean pinned;
        
        @JsonProperty("allowComment")
        private Boolean allowComment;
        
        @JsonProperty("visible")
        private String visible;
        
        @JsonProperty("priority")
        private Integer priority;
        
        @JsonProperty("excerpt")
        private ResponseExcerpt excerpt;
        
        @JsonProperty("categories")
        private List<String> categories;
        
        @JsonProperty("tags")
        private List<String> tags;
        
        @JsonProperty("htmlMetas")
        private List<Object> htmlMetas;
    }
    
    @Data
    public static class ResponseExcerpt {
        @JsonProperty("autoGenerate")
        private Boolean autoGenerate;
        
        @JsonProperty("raw")
        private String raw;
    }
    
    @Data
    public static class PostStatus {
        @JsonProperty("phase")
        private String phase;
        
        @JsonProperty("conditions")
        private List<Object> conditions;
        
        @JsonProperty("permalink")
        private String permalink;
        
        @JsonProperty("excerpt")
        private String excerpt;
        
        @JsonProperty("inProgress")
        private Boolean inProgress;
        
        @JsonProperty("commentsCount")
        private Integer commentsCount;
        
        @JsonProperty("contributors")
        private List<String> contributors;
        
        @JsonProperty("hideFromList")
        private Boolean hideFromList;
        
        @JsonProperty("lastModifyTime")
        private LocalDateTime lastModifyTime;
        
        @JsonProperty("observedVersion")
        private Integer observedVersion;
    }
}