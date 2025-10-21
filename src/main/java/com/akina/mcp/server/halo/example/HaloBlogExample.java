package com.akina.mcp.server.halo.example;

import com.akina.mcp.server.halo.domain.service.IHaloBlogService;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequest;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostResponse;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.PublishPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Halo Blog 使用示例
 * 展示如何使用 IHaloBlogService 创建和发布文章
 */
@Component
public class HaloBlogExample {
    
    @Autowired
    private IHaloBlogService haloBlogService;
    
    /**
     * 创建并发布文章的示例
     */
    public void createAndPublishPost() {
        try {
            // 1. 创建文章请求
            CreatePostRequest request = createPostRequest();
            
            // 2. 创建文章
            CreatePostResponse createResponse = haloBlogService.createPost(request);
            System.out.println("文章创建成功，文章名称: " + createResponse.getMetadata().getName());
            
            // 3. 发布文章
            PublishPostResponse publishResponse = haloBlogService.publishPost(createResponse.getMetadata().getName());
            System.out.println("文章发布成功，状态: " + publishResponse.getStatus().getPhase());
            
        } catch (Exception e) {
            System.err.println("操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 创建文章请求对象
     */
    private CreatePostRequest createPostRequest() {
        CreatePostRequest request = new CreatePostRequest();
        
        // 创建 Post 对象
        CreatePostRequest.Post post = new CreatePostRequest.Post();
        CreatePostRequest.Metadata metadata = new CreatePostRequest.Metadata();
        CreatePostRequest.PostSpec spec = new CreatePostRequest.PostSpec();
        
        // 设置元数据
        metadata.setName("post-test-" + System.currentTimeMillis());
        
        // 设置文章规格
        spec.setTitle("API发文测试标题");
        spec.setSlug("api-test-slug-" + System.currentTimeMillis());
        spec.setOwner("yaemiko");
        spec.setTags(new String[]{"api-test"});
        spec.setVisible("PUBLIC");
        spec.setAllowComment(true);
        spec.setPublish(false);
        
        // 设置摘要
        CreatePostRequest.Excerpt excerpt = new CreatePostRequest.Excerpt();
        excerpt.setAutoGenerate(true);
        excerpt.setRaw("");
        spec.setExcerpt(excerpt);
        
        post.setMetadata(metadata);
        post.setSpec(spec);
        request.setPost(post);
        
        // 创建内容
        CreatePostRequest.Content content = new CreatePostRequest.Content();
        content.setRaw("<p>API自动发布测试</p>");
        content.setContent("<p>API自动发布测试</p>");
        content.setRawType("HTML");
        request.setContent(content);
        
        return request;
    }
}