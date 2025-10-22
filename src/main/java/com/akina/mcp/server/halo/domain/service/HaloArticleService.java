package com.akina.mcp.server.halo.domain.service;

import com.akina.mcp.server.halo.domain.adapter.IHaloPort;
import com.akina.mcp.server.halo.domain.model.CreateAndPublishResponse;
import com.akina.mcp.server.halo.domain.model.CreatePostRequest;
import com.akina.mcp.server.halo.domain.model.CreatePostResponse;
import com.akina.mcp.server.halo.domain.model.PublishPostResponse;
import com.akina.mcp.server.halo.type.constants.ResponseCode;
import com.akina.mcp.server.halo.type.utils.CharsetFixer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HaloArticleService {

    @Resource
    private IHaloPort port;


    @Tool(name = "queryHaloBlogConfig", description = "创建并发布 Halo 文章")
    public CreateAndPublishResponse createAndPublish(CreatePostRequest request) throws IOException {

        // 解码补丁
        // 在接收到请求后立即进行编码修复
//        CreatePostRequest fixedRequest = fixEncodingFromStdio(request);

        ObjectMapper mapper = new ObjectMapper();

        // ① 打原始 request
        Files.writeString(
                Paths.get("E:/MCP_test/debug-request.log"),
                "\n\n=== FULL REQUEST ===\n" + mapper.writeValueAsString(request),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );

// ② 打修复后的 request
//        Files.writeString(
//                Paths.get("E:/MCP_test/debug-request.log"),
//                "\n\n=== FULL REQUEST AFTER FIX ===\n" + mapper.writeValueAsString(fixedRequest),
//                StandardOpenOption.CREATE, StandardOpenOption.APPEND
//        );

        // 生成文章唯一标识
        String postId = UUID.randomUUID().toString();

        // 1) 尝试创建
        CreatePostResponse createPostResponse = port.createPost(request, postId);

        Files.writeString(
                Paths.get("E:/MCP_test/debug-request.log"),
                "\n=== CREATE RESPONSE ===\n" + mapper.writeValueAsString(createPostResponse),
                StandardOpenOption.APPEND
        );

        // 如果创建失败则直接返回，不继续发布
        if (createPostResponse == null || !ResponseCode.SUCCESS.getCode().equals(createPostResponse.getCode())) {

            return CreateAndPublishResponse.builder()
                    .createPostResponse(createPostResponse)
                    .publishPostResponse(null) // 未发布
                    .build();
        }

        // 2) 创建成功 → 再发布
        PublishPostResponse publishPostResponse = port.publishPost(postId);

        // ④ 发布后立刻记录 publish 响应
        Files.writeString(
                Paths.get("E:/MCP_test/debug-request.log"),
                "\n=== PUBLISH RESPONSE ===\n" + mapper.writeValueAsString(publishPostResponse),
                StandardOpenOption.APPEND
        );

        return CreateAndPublishResponse.builder()
                .createPostResponse(createPostResponse)
                .publishPostResponse(publishPostResponse)
                .build();

    }

//    private CreatePostRequest fixEncodingFromStdio(CreatePostRequest req) {
//        if (req == null) return null;
//
//        // 针对stdio通信的特定编码修复
//        req.setTitle(fixStdioEncoding(req.getTitle()));
//        req.setSlug(fixStdioEncoding(req.getSlug()));
//        req.setMarkdown(fixStdioEncoding(req.getMarkdown()));
//        req.setCategories(fixListEncoding(req.getCategories()));
//        req.setTags(fixListEncoding(req.getTags()));
//
//        return req;
//    }
//
//    private String fixStdioEncoding(String text) {
//        if (text == null || text.isEmpty()) return text;
//
//        // 常见的stdio编码问题修复
//        String[] fromEncodings = {"GBK", "ISO-8859-1", "Windows-1252"};
//        String[] toEncodings = {"UTF-8", "GBK", "ISO-8859-1"};
//
//        for (String from : fromEncodings) {
//            for (String to : toEncodings) {
//                try {
//                    String fixed = new String(text.getBytes(from), to);
//                    if (isValidChineseText(fixed)) {
//                        return fixed;
//                    }
//                } catch (Exception e) {
//                    // 继续尝试其他编码组合
//                }
//            }
//        }
//
//        // 如果自动修复失败，尝试手动替换常见乱码模式
//        return manualFixCommonPatterns(text);
//    }
//
//    private boolean isValidChineseText(String text) {
//        if (text == null) return false;
//
//        // 检查是否包含合理的中文字符
//        boolean hasChinese = text.matches(".*[\\u4e00-\\u9fa5].*");
//        boolean noReplacementChars = !text.contains("�") && !text.contains("?");
//        boolean reasonableLength = text.length() > 0 && text.length() < 10000; // 合理长度
//
//        return hasChinese && noReplacementChars && reasonableLength;
//    }
//
//    private String manualFixCommonPatterns(String text) {
//        // 基于你提供的日志，手动修复已知的乱码模式
//        Map<String, String> commonFixes = new HashMap<>();
//
//        // 根据你的日志添加已知的乱码映射
//        commonFixes.put("鑱傚崥", "博客");
//        commonFixes.put("寮�鍙�", "开发");
//        commonFixes.put("鏃ヨ", "日记");
//        commonFixes.put("淇濆畨", "保安");
//
//        String result = text;
//        for (Map.Entry<String, String> entry : commonFixes.entrySet()) {
//            result = result.replace(entry.getKey(), entry.getValue());
//        }
//
//        return result;
//    }
//
//    private List<String> fixListEncoding(List<String> list) {
//        if (list == null) return null;
//        return list.stream()
//                .map(this::fixStdioEncoding)
//                .collect(Collectors.toList());
//    }
}
