package com.btf.blog.repository;

import com.btf.blog.model.Post;
import com.btf.blog.service.PostSvc;
import com.btf.blog.service.UserSvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.btf.blog.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentPublisher {
  private static final Logger LOG = LoggerFactory.getLogger(ContentPublisher.class);

  @Autowired private PostSvc postService;

  @Autowired private UserSvc userService;

  public ContentPublisher(PostSvc postService, UserSvc userService) {
    this.postService = postService;
    this.userService = userService;
  }

  public void process() {
    try {
      List<Post> posts = postService.getPosts();
      Map<Integer, List<Post>> postsGrouped =
          posts.stream().collect(Collectors.groupingBy(Post::getUserId));

      LOG.info("Posts size: " + posts.size());

      List<User> users = userService.getUsers();
      users.forEach(u -> u.setPosts(postsGrouped.get(u.getId())));
      LOG.info("Users size: " + users.size());

      publish(users);
    } catch (Exception ex) {
      LOG.warn("content publish failed:", ex);
    }
  }

  private void publish(List<User> users) {
    try {
      if (users.size() > 0) {
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonStr = mapper.writeValueAsBytes(users);
        ContentRepository.putContent(jsonStr);

        LOG.debug(new String(jsonStr));
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
