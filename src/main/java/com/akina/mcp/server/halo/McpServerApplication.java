package com.akina.mcp.server.halo;

import com.akina.mcp.server.halo.domain.service.HaloArticleService;
import com.akina.mcp.server.halo.infrastructure.gateway.HaloBlogApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class McpServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider haloTools(HaloArticleService haloArticleService) {
        return MethodToolCallbackProvider.builder().toolObjects(haloArticleService).build();
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Halo Server Application started");
    }
}
