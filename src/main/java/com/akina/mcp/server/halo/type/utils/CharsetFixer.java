package com.akina.mcp.server.halo.type.utils;

import com.akina.mcp.server.halo.domain.model.CreatePostRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CharsetFixer {

    /**
     * 修复 CreatePostRequest 中所有 String 字段的乱码
     */
    public static CreatePostRequest fix(CreatePostRequest req) {
        if (req == null) return null;

        req.setTitle(fixOne(req.getTitle()));
        req.setSlug(fixOne(req.getSlug()));
        req.setMarkdown(fixOne(req.getMarkdown()));
        req.setCategories(fixList(req.getCategories()));
        req.setTags(fixList(req.getTags()));

        return req;
    }

    /**
     * 修复单个字符串
     */
    private static String fixOne(String s) {
        if (s == null) return null;

        // 已经是 UTF-8 就直接返回
        if (isUtf8(s)) return s;

        // GBK 字节 → UTF-8 解码
        try {
            byte[] bytes = s.getBytes("GBK");
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 出现异常则返回原值
            return s;
        }
    }

    /**
     * 修复字符串列表
     */
    private static List<String> fixList(List<String> list) {
        if (list == null) return null;
        return list.stream().map(CharsetFixer::fixOne).collect(Collectors.toList());
    }

    /**
     * 简单 UTF-8 检测
     */
    private static boolean isUtf8(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return input.equals(new String(bytes, StandardCharsets.UTF_8));
    }
}
