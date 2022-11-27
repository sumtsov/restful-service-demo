package com.dsumtsov.restful.feign;

import com.dsumtsov.restful.dto.response.AuthorGeneratorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "author-generator", url = "${app.feign.url}")
public interface AuthorGeneratorClient {
    @GetMapping
    AuthorGeneratorResponse retrieveRandomAuthor();
}
