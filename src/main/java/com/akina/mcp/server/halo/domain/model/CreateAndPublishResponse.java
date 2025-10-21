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
public class CreateAndPublishResponse {

    @JsonProperty(required = true, value = "createPostResponse")
    @JsonPropertyDescription("创建文章响应对象")
    private CreatePostResponse createPostResponse;

    @JsonProperty(required = true, value = "publishPostResponse")
    @JsonPropertyDescription("发布文章响应对象")
    private PublishPostResponse publishPostResponse;

}
