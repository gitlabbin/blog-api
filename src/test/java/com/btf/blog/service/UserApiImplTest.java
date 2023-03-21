package com.btf.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserApiImplTest {
  @Autowired private UserSvc apiService;

  @Value("${user.limit}")
  private Integer USER_LIMIT;

  @Before
  public void setUp() throws Exception {}

  @Test
  public void getUsers() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      System.out.println(
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiService.getUsers()));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Assert.assertEquals(USER_LIMIT.intValue(), apiService.getUsers().size());
  }
}
