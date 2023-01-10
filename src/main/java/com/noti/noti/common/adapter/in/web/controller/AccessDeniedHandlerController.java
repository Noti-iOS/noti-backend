package com.noti.noti.common.adapter.in.web.controller;

import com.noti.noti.error.exception.CustomAccessDeniedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class AccessDeniedHandlerController {

  @GetMapping("/access-denied")
  public ResponseEntity accessDeniedException() {
    throw new CustomAccessDeniedException();
  }
}
