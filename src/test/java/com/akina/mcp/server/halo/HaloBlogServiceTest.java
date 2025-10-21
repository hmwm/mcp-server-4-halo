package com.akina.mcp.server.halo;

import com.akina.mcp.server.halo.domain.service.IHaloBlogService;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Halo Blog Service 测试类
 */
@SpringBootTest
@TestPropertySource(properties = {
    "halo.blog.base-url=https://yaemiko.live",
    "halo.blog.pat=your-pat-token-here"
})
public class HaloBlogServiceTest {
    
    @Autowired
    private IHaloBlogService haloBlogService;
    
    @Test
    public void testHaloBlogServiceBean() {
        assertNotNull(haloBlogService, "IHaloBlogService bean should be created");
    }
    
    @Test
    public void testCreatePostRequest() {
        CreatePostRequest request = new CreatePostRequest();
        CreatePostRequest.Post post = new CreatePostRequest.Post();
        CreatePostRequest.Metadata metadata = new CreatePostRequest.Metadata();
        CreatePostRequest.PostSpec spec = new CreatePostRequest.PostSpec();
        CreatePostRequest.Content content = new CreatePostRequest.Content();
        
        metadata.setName("test-post-123");
        spec.setTitle("测试文章标题");
        spec.setSlug("test-slug");
        spec.setOwner("admin");
        spec.setTags(new String[]{"test"});
        
        content.setRaw("<p>测试内容</p>");
        content.setContent("<p>测试内容</p>");
        
        post.setMetadata(metadata);
        post.setSpec(spec);
        request.setPost(post);
        request.setContent(content);
        
        assertNotNull(request, "CreatePostRequest should be created");
        assertNotNull(request.getPost(), "Post should be set");
        assertNotNull(request.getContent(), "Content should be set");
    }
}