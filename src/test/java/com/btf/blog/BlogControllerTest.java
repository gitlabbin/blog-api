package com.btf.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {
  private static final Logger LOG = LoggerFactory.getLogger(BlogControllerTest.class);

  @Autowired private MockMvc mockMvc;

  @Test
  public void usersEndpoint() throws Exception {

    this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void usersFullContent() throws Exception {
    this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());

    Thread.sleep(1000);

    int iterations = 10;
    for (int i = 0; i <= 10; ++i) {
      try {
        this.mockMvc
            .perform(get("/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(10)))
            .andExpect(jsonPath("$[0].id", is(1)));

        LOG.info(
            "Wait time on asserts was " + TimeUnit.MILLISECONDS.toSeconds(200 * i) + " seconds");
        break;
      } catch (AssertionError er) {
        if (iterations == i) {
          throw er;
        }
        Thread.sleep(200);
      }
    }
  }
}
