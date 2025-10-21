package com.akina.mcp.server.halo;

import com.akina.mcp.server.halo.infrastructure.gateway.HaloBlogApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {
    
    /**
     * 配置 IHaloBlogService Bean
     * @param haloBlogApi Halo Blog API 接口
     * @return IHaloBlogService 实例
     */
    @Bean
    public IHaloBlogService haloBlogService(HaloBlogApi haloBlogApi) {
        return new HaloBlogServiceImpl(haloBlogApi);
    }
}
