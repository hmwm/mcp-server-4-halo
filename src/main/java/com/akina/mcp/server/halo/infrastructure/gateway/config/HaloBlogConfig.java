package com.akina.mcp.server.halo.infrastructure.gateway.config;

import com.akina.mcp.server.halo.infrastructure.gateway.HaloBlogApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * Halo Blog 配置类
 * 配置 Retrofit2 和 OkHttp
 */
@Configuration
public class HaloBlogConfig {
    
    @Value("${halo.blog.base-url:https://yaemiko.live}")
    private String baseUrl;
    
    @Value("${halo.blog.pat:}")
    private String pat;
    
    /**
     * 配置 Jackson ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    /**
     * 配置 OkHttpClient，添加认证拦截器
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(pat))
                .build();
    }
    
    /**
     * 配置 Retrofit
     */
    @Bean
    public Retrofit retrofit(ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }
    
    /**
     * 创建 HaloBlogApi Bean
     */
    @Bean
    public HaloBlogApi haloBlogApi(Retrofit retrofit) {
        return retrofit.create(HaloBlogApi.class);
    }
    
    /**
     * 认证拦截器
     */
    private static class AuthInterceptor implements Interceptor {
        private final String pat;
        
        public AuthInterceptor(String pat) {
            this.pat = pat;
        }
        
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer " + pat)
                    .build();
            return chain.proceed(newRequest);
        }
    }
}