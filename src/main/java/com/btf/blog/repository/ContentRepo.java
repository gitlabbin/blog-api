package com.btf.blog.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContentRepo {
  private static final Logger LOG = LoggerFactory.getLogger(ContentRepo.class);
  private static final String GREEN = "GREEN";
  private static final String BLUE = "BLUE";
  private static AtomicBoolean IS_GREEN_SERVE = new AtomicBoolean(false);

  private static final Map<String, byte[]> blueBucket =
      Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, byte[]> greenBucket =
      Collections.synchronizedMap(new HashMap<>());

  public static synchronized void putContent(byte[] content) {
    if (IS_GREEN_SERVE.get()) {
      blueBucket.put(BLUE, content);
      IS_GREEN_SERVE.set(false);
      LOG.info("Ready serve for bucket: " + BLUE);
    } else {
      greenBucket.put(GREEN, content);
      IS_GREEN_SERVE.set(true);
      LOG.info("Ready serve for bucket: " + GREEN);
    }
  }

  public static synchronized byte[] getContent() {
    if (IS_GREEN_SERVE.get()) {
      return greenBucket.get(GREEN);
    } else {
      return blueBucket.get(BLUE);
    }
  }
}
