package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.lesson.adapter.in.web.dto.response.GetAllLessonsResponse;
import com.noti.noti.lesson.application.port.in.GetAllLessonsQuery;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetAllLessonsController {

  private final GetAllLessonsQuery getAllLessonsQuery;

  @GetMapping("/api/teacher/lessons")
  public ResponseEntity<SuccessResponse<List<GetAllLessonsResponse>>> getAllLessons(
      @AuthenticationPrincipal UserDetails userDetails){
    Long teacherId = Long.valueOf(userDetails.getUsername());

    ArrayList<GetAllLessonsResponse> response = new ArrayList<>();
    getAllLessonsQuery.getAllLessons(teacherId).forEach(
        lessonDto -> response.add(GetAllLessonsResponse.from(lessonDto)));

    return ResponseEntity.ok().body(SuccessResponse.create200SuccessResponse(response));
  }
}
