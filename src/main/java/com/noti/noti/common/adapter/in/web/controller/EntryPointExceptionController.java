package com.noti.noti.common.adapter.in.web.controller;

import com.noti.noti.error.exception.CustomAuthenticationEntryPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class EntryPointExceptionController {

  @GetMapping("/entrypoint")
  public ResponseEntity entryPointException() {
    throw new CustomAuthenticationEntryPointException();
  }
}
