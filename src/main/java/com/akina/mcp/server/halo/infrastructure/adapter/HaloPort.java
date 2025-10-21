package com.akina.mcp.server.halo.infrastructure.adapter;

import com.akina.mcp.server.halo.domain.adapter.IHaloPort;
import com.akina.mcp.server.halo.domain.model.CreatePostRequest;
import com.akina.mcp.server.halo.domain.model.CreatePostResponse;
import com.akina.mcp.server.halo.domain.model.PublishPostResponse;
import com.akina.mcp.server.halo.infrastructure.gateway.HaloBlogApi;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequestDTO;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostResponseDTO;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.PublishPostResponseDTO;
import com.akina.mcp.server.halo.type.constants.ResponseCode;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class HaloPort implements IHaloPort {

    @Value("$halo.blog.default-owner")
    private String defaultOwner;

    @Resource
    private HaloBlogApi haloBlogApi;




    @Override
    public CreatePostResponse createPost(CreatePostRequest request, String postId) throws IOException {


        // MetaData
        CreatePostRequestDTO.Metadata metadata = new CreatePostRequestDTO.Metadata();
        metadata.setName(postId); // 目前对接haloApi暂时用uuid后续有数据库后采用数据库自增id

        // PostSpec
        CreatePostRequestDTO.PostSpec postSpec = new CreatePostRequestDTO.PostSpec();
        postSpec.setTitle(request.getTitle());
        postSpec.setSlug(request.getSlug());
        postSpec.setTags(request.getTags());
        postSpec.setOwner(defaultOwner);

        // Content
        CreatePostRequestDTO.Content content = new CreatePostRequestDTO.Content();
        content.setRaw(request.getMarkdown()); // Halo 中 Raw 为文章内容 markdown原文
        content.setContent(request.getContent()); // 转为html

        // Post
        CreatePostRequestDTO.Post post = new CreatePostRequestDTO.Post();
        post.setMetadata(metadata);
        post.setSpec(postSpec);

        CreatePostRequestDTO createPostRequestDTO = new CreatePostRequestDTO();
        createPostRequestDTO.setPost(post);
        createPostRequestDTO.setContent(content);

        // 调用封装好的HaloApi创建文章
        // Retrofit2 + OkHttp 的架构 AuthInterceptor 里写的 Authorization: Bearer <PAT> 会被自动加到请求头
        // 也就是自动触发拦截器并带上pat
        Call<CreatePostResponseDTO> call = haloBlogApi.createPost(createPostRequestDTO);
        Response<CreatePostResponseDTO> response = call.execute(); // retrofit2框架的响应体

        log.info("请求Halo创建文章 \nreq:{} \nres:{}", JSON.toJSONString(createPostRequestDTO), JSON.toJSONString(response.body()));

//        if (response.isSuccessful()) {
//            CreatePostResponseDTO createPostResponseDTO = response.body();
//            if (createPostResponseDTO == null) return null;
//            CreatePostResponse.ArticleData articleData = CreatePostResponse.ArticleData.builder()
//                    .postId(createPostResponseDTO.getMetadata().getName())
//                    .title(createPostResponseDTO.getSpec().getTitle())
//                    .published(createPostResponseDTO.getSpec().getPublish())
//                    .build();
//
//            return CreatePostResponse.builder()
//                    .code(response.isSuccessful() ? 200 : response.code())
//                    .message(response.isSuccessful() ? "SUCCESS" : "Halo API调用失败")
//                    .articleData(articleData)
//                    .build();
//        }
        CreatePostResponseDTO createPostResponseDTO = response.body();
        CreatePostResponse.ArticleData articleData =
                createPostResponseDTO != null
                        ? CreatePostResponse.ArticleData.builder()
                            .postId(createPostResponseDTO.getMetadata().getName())
                            .title(createPostResponseDTO.getSpec().getTitle())
                            .published(createPostResponseDTO.getSpec().getPublish())
                            .build()
                        : null;

        return CreatePostResponse.builder()
                .code(response != null && response.isSuccessful() ? ResponseCode.SUCCESS.getCode() : ResponseCode.HALO_API_ERROR.getCode())
                .message(response != null && response.isSuccessful() ? ResponseCode.SUCCESS.getMessage() : ResponseCode.HALO_API_ERROR.getMessage())
                .articleData(articleData)
                .build();
    }

    @Override
    public PublishPostResponse publishPost(String postName) throws IOException {
        Call<PublishPostResponseDTO> publishPostResponseDTOCall = haloBlogApi.publishPost(postName, true);
        Response<PublishPostResponseDTO> response = publishPostResponseDTOCall.execute();

        log.info("请求Halo发布文章 \nreqParam:{} \nres:{}", JSON.toJSONString(postName), JSON.toJSONString(response.body()));

        if (!response.isSuccessful()) {
            return PublishPostResponse.builder()
                    .code(ResponseCode.HALO_API_ERROR.getCode())
                    .message(ResponseCode.HALO_API_ERROR.getMessage())
                    .data(null)
                    .build();
        }

        PublishPostResponseDTO dto = response.body();
        if (dto == null || dto.getStatus() == null) {
            return PublishPostResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message("Halo API 响应成功但结构不完整")
                    .data(null)
                    .build();
        }

        boolean published = "PUBLISHED".equals(dto.getStatus().getPhase());

        // TODO 后续增强：phase 增加到响应结构中，支持 DRAFT/PUBLISHING/PUBLISHED 等多状态
        PublishPostResponse.PublishPostData data = PublishPostResponse.PublishPostData.builder()
                .postId(dto.getMetadata().getName())
                .permalink(dto.getStatus().getPermalink())
                .publishTime(dto.getSpec().getPublishTime().toInstant())
                .success(published)
                .build();


        return PublishPostResponse.builder()
                .code(ResponseCode.SUCCESS.getCode()) // 接口调用成功
                .message(published ? "发布成功" : "调用成功但未进入 PUBLISHED 状态")
                .data(data)
                .build();


    }
}
