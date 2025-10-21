package com.akina.mcp.server.halo.domain.service;

import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequest;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostResponse;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.PublishPostResponse;

/**
 * Halo Blog Service 接口
 * 提供博客文章创建和发布功能
 */
public interface IHaloBlogService {
    
    /**
     * 创建文章
     * @param request 创建文章请求
     * @return 创建文章响应
     */
    CreatePostResponse createPost(CreatePostRequest request);
    
    /**
     * 发布文章
     * @param postName 文章名称
     * @return 发布文章响应
     */
    PublishPostResponse publishPost(String postName);
}