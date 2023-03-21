package com.btf.blog;

import com.btf.blog.repository.ContentRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
  public static final String NO_CONTENT = "Contents will be available soon";

  @RequestMapping(value = "/users", produces = "application/json")
  public ResponseEntity<?> processWith() {
    byte[] bytes = ContentRepo.getContent();

    if (null == bytes) {
      return new ResponseEntity<>(NO_CONTENT, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(bytes, HttpStatus.OK);
    }
  }
}
