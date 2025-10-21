package com.akina.mcp.server.halo.domain.adapter;

import com.akina.mcp.server.halo.domain.model.CreatePostRequest;
import com.akina.mcp.server.halo.domain.model.CreatePostResponse;
import com.akina.mcp.server.halo.domain.model.PublishPostResponse;

import java.io.IOException;

/**
 * Halo Blog Service 接口
 * 提供博客文章创建和发布功能
 */
public interface IHaloPort {

    /**
     * 创建文章
     * @param request 创建文章请求
     * @param postId 文章唯一标识
     * @return 创建文章响应
     */
    public CreatePostResponse createPost(CreatePostRequest request, String postId) throws IOException;

    /**
     * 发布文章
     * @param postId 文章标识
     * @return 发布文章响应
     */
    public PublishPostResponse publishPost(String postId) throws IOException;
}
