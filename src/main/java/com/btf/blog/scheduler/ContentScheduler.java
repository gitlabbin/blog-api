package com.btf.blog.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btf.blog.repository.ContentPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContentScheduler {
  private static final Logger LOG = LoggerFactory.getLogger(ContentScheduler.class);
  @Autowired private ContentPublisher publisher;

  // To trigger the scheduler every 2 minutes with
  // an initial delay of 2 seconds.
  @Scheduled(fixedDelayString = "${fixedDelay.input}", initialDelayString = "${initialDelay.input}")
  public void scheduleTask() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
    LOG.info("Content Scheduler: refreshing at - " + dateFormat.format(new Date()));
    publisher.process();
    LOG.info("Content Scheduler: refreshed at - " + dateFormat.format(new Date()));
  }
}
