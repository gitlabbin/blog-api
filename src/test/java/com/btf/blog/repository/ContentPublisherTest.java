package com.btf.blog.repository;

import com.btf.blog.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContentPublisherTest {
  @Autowired private ContentPublisher publisher;

  @Before
  public void setUp() throws Exception {}

  @Test
  public void process() {
    publisher.process();

    Assertions.assertThat(ContentRepository.getContent().length).isGreaterThan(0);
  }

  @Test
  public void fullUsersWithPosts() {
    publisher.process();
    ObjectMapper mapper = new ObjectMapper();
    try {
      User[] users = mapper.readValue(ContentRepository.getContent(), User[].class);

      Assert.assertEquals(10, users.length);
      Assertions.assertThat(users[0].getPosts().size()).isGreaterThan(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
