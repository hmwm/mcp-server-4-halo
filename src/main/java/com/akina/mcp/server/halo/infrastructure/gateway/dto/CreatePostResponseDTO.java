package com.akina.mcp.server.halo.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * 创建文章响应对象
 */
@Data
public class CreatePostResponseDTO {

    private String apiVersion;
    private String kind;
    private ResponseMetadata metadata;
    private ResponsePostSpec spec;
    private PostStatus status;
    
    @Data
    public static class ResponseMetadata {

        private String name;
        private String generateName;
        private Map<String, String> labels;
        private Map<String, String> annotations;
        private Integer version;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime creationTimestamp;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime deletionTimestamp;

        private List<String> finalizers;
    }
    
    @Data
    public static class ResponsePostSpec {

        private String title;
        private String slug;
        private String baseSnapshot;
        private String headSnapshot;
        private String releaseSnapshot;
        private String owner;
        private String template;
        private String cover;
        private Boolean deleted;
        private Boolean publish;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime publishTime;

        private Boolean pinned;
        private Boolean allowComment;
        private String visible;
        private Integer priority;
        private ResponseExcerpt excerpt;
        private List<String> categories;
        private List<String> tags;
        private List<Object> htmlMetas;
    }
    
    @Data
    public static class ResponseExcerpt {
        private Boolean autoGenerate;
        private String raw;
    }
    
    @Data
    public static class PostStatus {

        private String phase;
        private List<Object> conditions;
        private String permalink;
        private String excerpt;
        private Boolean inProgress;
        private Integer commentsCount;
        private List<String> contributors;
        private Boolean hideFromList;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime lastModifyTime;

        private Integer observedVersion;
    }
}