package com.akina.mcp.server.halo.infrastructure.gateway;

import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequestDTO;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostResponseDTO;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.PublishPostResponseDTO;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Halo Blog API 接口
 * 使用 Retrofit2 封装 Halo 博客 API 调用
 */
public interface HaloBlogApi {
    
    /**
     * 创建文章
     * @param request 创建文章请求
     * @return 创建文章响应
     */
    @POST("apis/api.console.halo.run/v1alpha1/posts")
    @Headers("Content-Type: application/json; charset=UTF-8")
    Call<CreatePostResponseDTO> createPost(@Body CreatePostRequestDTO request);
    
    /**
     * 发布文章
     * @param postName 文章名称 // MetaName 也就是业务层的postId
     * @param async 是否异步发布
     * @return 发布文章响应
     */
    @PUT("apis/api.console.halo.run/v1alpha1/posts/{postName}/publish")
    Call<PublishPostResponseDTO> publishPost(
            @Path("postName") String postName,
            @Query("async") boolean async
    );
}