# Halo Blog Service 使用说明

## 概述

本项目基于 Retrofit2 框架封装了 Halo 博客的 API 调用，提供了创建和发布文章的功能。

## 项目结构

```
src/main/java/com/akina/mcp/server/halo/
├── domain/
│   └── service/
│       └── IHaloBlogService.java          # 服务接口定义
├── infrastructure/
│   └── gateway/
│       ├── dto/                           # 数据传输对象
│       │   ├── CreatePostRequest.java     # 创建文章请求
│       │   ├── CreatePostResponse.java    # 创建文章响应
│       │   └── PublishPostResponse.java   # 发布文章响应
│       ├── HaloBlogApi.java               # Retrofit2 API 接口
│       ├── HaloBlogConfig.java            # Retrofit2 配置
│       └── HaloBlogServiceImpl.java       # 服务实现
├── example/
│   └── HaloBlogExample.java               # 使用示例
└── McpServerApplication.java              # 主应用类
```

## 配置

### 1. 环境变量配置

在 `application.yml` 中配置 Halo 博客的基本信息：

```yaml
halo:
  blog:
    base-url: https://yaemiko.live
    pat: ${HALO_PAT:your-pat-token-here}
```

### 2. 环境变量设置

设置 Halo 的 Personal Access Token：

```bash
export HALO_PAT=your-actual-pat-token
```

## 使用方法

### 1. 注入服务

```java
@Autowired
private IHaloBlogService haloBlogService;
```

### 2. 创建文章

```java
// 创建文章请求
CreatePostRequest request = new CreatePostRequest();
CreatePostRequest.Post post = new CreatePostRequest.Post();
CreatePostRequest.Metadata metadata = new CreatePostRequest.Metadata();
CreatePostRequest.PostSpec spec = new CreatePostRequest.PostSpec();

// 设置文章信息
metadata.setName("post-test-123");
spec.setTitle("我的文章标题");
spec.setSlug("my-article-slug");
spec.setOwner("admin");
spec.setTags(new String[]{"标签1", "标签2"});

post.setMetadata(metadata);
post.setSpec(spec);
request.setPost(post);

// 设置文章内容
CreatePostRequest.Content content = new CreatePostRequest.Content();
content.setRaw("<p>文章内容</p>");
content.setContent("<p>文章内容</p>");
content.setRawType("HTML");
request.setContent(content);

// 调用创建文章接口
CreatePostResponse response = haloBlogService.createPost(request);
```

### 3. 发布文章

```java
// 发布文章
PublishPostResponse publishResponse = haloBlogService.publishPost("post-test-123");
```

## API 接口说明

### 创建文章接口

- **接口**: `POST /apis/api.console.halo.run/v1alpha1/posts`
- **功能**: 创建新的博客文章
- **请求**: `CreatePostRequest`
- **响应**: `CreatePostResponse`

### 发布文章接口

- **接口**: `PUT /apis/api.console.halo.run/v1alpha1/posts/{name}/publish`
- **功能**: 发布指定的博客文章
- **参数**: `postName` - 文章名称
- **响应**: `PublishPostResponse`

## 依赖说明

项目使用的主要依赖：

- **Spring Boot 3.4.3**: 基础框架
- **Retrofit2 2.9.0**: HTTP 客户端
- **Jackson**: JSON 序列化/反序列化
- **Lombok**: 简化代码

## 注意事项

1. 确保正确配置 Halo 的 Personal Access Token
2. 确保网络可以访问 Halo 博客的 API 地址
3. 文章名称（name）必须是唯一的
4. 发布文章前需要先创建文章

## 测试

运行测试类验证配置：

```bash
mvn test -Dtest=HaloBlogServiceTest
```

## 示例代码

参考 `HaloBlogExample.java` 中的完整使用示例。