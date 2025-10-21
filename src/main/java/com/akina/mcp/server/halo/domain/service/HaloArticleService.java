package com.akina.mcp.server.halo.domain.service;

import com.akina.mcp.server.halo.domain.adapter.IHaloPort;
import com.akina.mcp.server.halo.domain.model.CreateAndPublishResponse;
import com.akina.mcp.server.halo.domain.model.CreatePostRequest;
import com.akina.mcp.server.halo.domain.model.CreatePostResponse;
import com.akina.mcp.server.halo.domain.model.PublishPostResponse;
import com.akina.mcp.server.halo.type.constants.ResponseCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class HaloArticleService {

    @Resource
    private IHaloPort port;


    @Tool(description = "创建并发布 Halo 文章")
    public CreateAndPublishResponse createAndPublish(CreatePostRequest request) throws IOException {

        // 生成文章唯一标识
        String postId = UUID.randomUUID().toString();

        // 1) 尝试创建
        CreatePostResponse createPostResponse = port.createPost(request, postId);

        // 如果创建失败则直接返回，不继续发布
        if (createPostResponse == null
                || !ResponseCode.SUCCESS.getCode().equals(createPostResponse.getCode())) {

            return CreateAndPublishResponse.builder()
                    .createPostResponse(createPostResponse)
                    .publishPostResponse(null) // 未发布
                    .build();
        }

        // 2) 创建成功 → 再发布
        PublishPostResponse publishPostResponse = port.publishPost(postId);

        return CreateAndPublishResponse.builder()
                .createPostResponse(createPostResponse)
                .publishPostResponse(publishPostResponse)
                .build();

    }


}
