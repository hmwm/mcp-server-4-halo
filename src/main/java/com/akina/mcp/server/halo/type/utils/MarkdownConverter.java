package com.akina.mcp.server.halo.type.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class MarkdownConverter {

    private static final Parser parser;

    private static final HtmlRenderer renderer;

    static {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public static String convertHtml(String markdown) {
        if (markdown == null || markdown.length() == 0) {
            return "";
        }
        return renderer.render(parser.parse(markdown));
    }
}
