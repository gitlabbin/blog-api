package com.btf.blog.service;

import com.btf.blog.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private RestTemplate restTemplate;

  // inject value from application.properties
  @Value("${api.user.url}")
  private String API_URL;

  @Override
  public List<User> getUsers() {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(API_URL);

    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(uriBuilder.toUriString(), User[].class)));
  }
}
