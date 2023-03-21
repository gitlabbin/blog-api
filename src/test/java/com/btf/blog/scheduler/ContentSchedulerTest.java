package com.btf.blog.scheduler;

import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentSchedulerTest {
  @SpyBean private ContentScheduler contentScheduler;

  @Test
  public void jobRuns() {
    Awaitility.await()
        .atMost(4, TimeUnit.SECONDS)
        .untilAsserted(() -> verify(contentScheduler, Mockito.atLeastOnce()).scheduleTask());
  }
}
