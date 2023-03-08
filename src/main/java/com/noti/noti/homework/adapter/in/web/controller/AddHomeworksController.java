package com.noti.noti.homework.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.homework.adapter.in.web.request.AddHomeworksRequest;
import com.noti.noti.homework.application.port.in.AddHomeworksUsecase;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddHomeworksController {

  private final AddHomeworksUsecase addHomeworksUsecase;

  @PostMapping("/api/teacher/homeworks")
  public ResponseEntity<SuccessResponse> addHomeworks(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody @Valid AddHomeworksRequest addHomeworksRequest) {
    Long teacherId = Long.valueOf(userDetails.getUsername());
    addHomeworksUsecase.addHomeworks(addHomeworksRequest.toCommand(teacherId));

    return new ResponseEntity<>(SuccessResponse.create201SuccessResponse(), HttpStatus.CREATED);
  }
}
