package com.akina.mcp.server.halo.infrastructure.gateway;

import com.akina.mcp.server.halo.domain.service.IHaloBlogService;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequest;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostResponse;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.PublishPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

/**
 * Halo Blog Service 实现类
 * 使用 Retrofit2 调用 Halo 博客 API
 */
@Slf4j
@Service
public class HaloBlogServiceImpl implements IHaloBlogService {
    
    private final HaloBlogApi haloBlogApi;
    
    public HaloBlogServiceImpl(HaloBlogApi haloBlogApi) {
        this.haloBlogApi = haloBlogApi;
    }
    
    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            log.info("Creating post with title: {}", request.getPost().getSpec().getTitle());
            Response<CreatePostResponse> response = haloBlogApi.createPost(request).execute();
            
            if (response.isSuccessful() && response.body() != null) {
                log.info("Post created successfully with name: {}", response.body().getMetadata().getName());
                return response.body();
            } else {
                log.error("Failed to create post. Response code: {}, Message: {}", 
                    response.code(), response.message());
                throw new RuntimeException("Failed to create post: " + response.message());
            }
        } catch (IOException e) {
            log.error("Error creating post", e);
            throw new RuntimeException("Error creating post", e);
        }
    }
    
    @Override
    public PublishPostResponse publishPost(String postName) {
        try {
            log.info("Publishing post: {}", postName);
            Response<PublishPostResponse> response = haloBlogApi.publishPost(postName, false).execute();
            
            if (response.isSuccessful() && response.body() != null) {
                log.info("Post published successfully: {}", postName);
                return response.body();
            } else {
                log.error("Failed to publish post. Response code: {}, Message: {}", 
                    response.code(), response.message());
                throw new RuntimeException("Failed to publish post: " + response.message());
            }
        } catch (IOException e) {
            log.error("Error publishing post", e);
            throw new RuntimeException("Error publishing post", e);
        }
    }
}