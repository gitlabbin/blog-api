package com.btf.blog.service;

import com.btf.blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PostSvcImpl implements PostSvc {
  @Autowired private RestTemplate restTemplate;

  // inject value from application.properties
  @Value("${api.post.url}")
  private String API_URL;

  @Override
  public List<Post> getPosts() {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(API_URL);

    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(uriBuilder.toUriString(), Post[].class)));
  }
}
