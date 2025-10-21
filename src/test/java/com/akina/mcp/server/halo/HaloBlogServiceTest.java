package com.akina.mcp.server.halo;


import com.akina.mcp.server.halo.domain.model.CreatePostRequest;
import com.akina.mcp.server.halo.domain.service.HaloArticleService;
import com.akina.mcp.server.halo.infrastructure.gateway.dto.CreatePostRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Halo Blog Service 测试类
 */
@SpringBootTest
@TestPropertySource(properties = {
    "halo.blog.base-url=https://yaemiko.live",
    "halo.blog.pat=pat_eyJraWQiOiJ1eFZJZ1NtT2FWSmtIQkpmTUJXSmJ2U09FYzExSlQ3LTZnam1NWktGcXk4IiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwOi8vMTE3LjcyLjU0LjEwNDo4MDkwLyIsInN1YiI6InlhZW1pa28iLCJpYXQiOjE3NjEwMjg3MjQsImp0aSI6ImQ1M2ZkNDZhLWI0ZDQtODE5ZC0wYzNjLWM3OWExMzQzODc1NCIsInBhdF9uYW1lIjoicGF0LXJ2dm82ZnJ6In0.U1nBg9yI-jKnY3Y5mS7atCqVkIhi_sLrT5JhMWqPg0xr2-7N37FYtTFLSYfg-xXg97xcDZ5eFyzrgAO0tLBGeVQkPrNHQ-LR93iE-wi-xn_1W-aZbXD2FIxSAY6NmiLxkXZPDoRHQ3hl6KY_SMG7GaCm0Du_Lyd8u3E0Wg5Zxdw2mnZSWkW02NY-A-uChvrzyuWj104eqen_d3O-ByEuIRTLN4fjilhQD3nePsvhd25nHsBbijEo8kjtCsDtltrJdP-yL1nlxxil2DMrNq3ZHQaRjHl_TYo-CtpIRwL_uQD37KPaWnHeaFmtcpMO8_BK-x8w9ijdaJZ2iDkTG5zMQK2iQ2cXii6C-7tazmRo0pMXVkELCXiL2qWH1haJGgKyGwRMk7FtAMggOhIBXsyQPOnDZLFWUmLw0Z1Ae_rPGiVAJhFJDJkDN8a6q2glGxbInifnKS4gqtBuh1zhhqRCPcqnu66omfAPctWrqRd-oxeTH6DC6cIwDMpfoNmv12A5GXoF2iObrmOEu7uCHCh0MhswFWK2jJpKrAc92M5qQiXyGx_XfepolWPaVYCrfe7U-KN04YvK0XtP9ydMeD9h4oqqqm8DIdyT805ZN4KRH5ztnsCMCBoWY6sqayKyK2PrLOZDrm58NZMXNED8FoyNgy81TUaOEiHeYie6_lEIhoI"
})
public class HaloBlogServiceTest {
    
    @Autowired
    private HaloArticleService haloArticleService;

    @Test
    public void testHaloBlogServiceBean() {
        assertNotNull(haloArticleService, "IHaloBlogService bean should be created");
    }
    
    @Test
    public void testCreatePostRequest() throws IOException {

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setTitle("Title");
        createPostRequest.setSlug("Slug");
        createPostRequest.setMarkdown("# 测试文章内容\n这是一篇测试文章");
        createPostRequest.setCategories(Arrays.asList("Spring", "live"));
        createPostRequest.setTags(Arrays.asList("Spring", "学习"));
        createPostRequest.setPublishNow(true);



        haloArticleService.createAndPublish(createPostRequest);
    }
}