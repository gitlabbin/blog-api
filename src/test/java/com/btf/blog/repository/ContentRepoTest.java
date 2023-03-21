package com.btf.blog.repository;

import com.btf.blog.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ContentRepoTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void putAndRead() {
    try (InputStream inStream =
        getClass().getClassLoader().getResourceAsStream("users_full.json")) {
      User[] users = mapper.readValue(inStream, User[].class);
      byte[] jsonStr = mapper.writeValueAsBytes(users);
      ContentRepo.putContent(jsonStr);
      Assertions.assertThat(ContentRepo.getContent().length).isGreaterThan(0);

      User[] newUsers = mapper.readValue(ContentRepo.getContent(), User[].class);
      Assert.assertEquals(10, newUsers.length);
      Assertions.assertThat(newUsers[0].getPosts().size()).isGreaterThan(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
