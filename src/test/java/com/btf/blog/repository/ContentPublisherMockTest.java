package com.btf.blog.repository;

import com.btf.blog.model.Post;
import com.btf.blog.model.User;
import com.btf.blog.service.PostSvc;
import com.btf.blog.service.UserSvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ContentPublisherMockTest {

  @InjectMocks ContentPublisher publisher;

  @Mock private PostSvc postServiceMock;

  @Mock private UserSvc userServiceMock;

  private ObjectMapper mapper = new ObjectMapper();
  List<User> users;
  List<Post> posts;

  @BeforeEach
  public void init() {
    try (InputStream usersInStream = getClass().getClassLoader().getResourceAsStream("users.json");
        InputStream postsInStream =
            getClass().getClassLoader().getResourceAsStream("posts.json"); ) {
      users = Arrays.asList(mapper.readValue(usersInStream, User[].class));
      posts = Arrays.asList(mapper.readValue(postsInStream, Post[].class));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testContentProcess_PublishUsersFull() {
    // Arrange
    Mockito.when(userServiceMock.getUsers()).thenReturn(users);
    Mockito.when(postServiceMock.getPosts()).thenReturn(posts);
    // Act
    publisher.process();
    // Assert
    Assertions.assertThat(ContentRepository.getContent().length).isGreaterThan(0);
    try {
      User[] users = mapper.readValue(ContentRepository.getContent(), User[].class);

      assertEquals(10, users.length);
      Assertions.assertThat(users[0].getPosts().size()).isGreaterThan(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
