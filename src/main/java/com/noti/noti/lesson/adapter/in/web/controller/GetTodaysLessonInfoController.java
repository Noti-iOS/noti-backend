package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.lesson.adapter.in.web.dto.response.TodaysLessonHomeworkDto;
import com.noti.noti.lesson.application.port.in.GetTodaysLessonQuery;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GetTodaysLessonInfoController {

  private final GetTodaysLessonQuery getTodaysLessonQuery;

  @GetMapping("/api/teacher/home")
  public ResponseEntity<TodaysLessonHomeworkDto> todaysLessonInfo(@AuthenticationPrincipal UserDetails userDetails) {
    long teacherId = Long.parseLong((userDetails.getUsername()));
    System.out.println(teacherId);
    TodaysLessonHomework todaysLessonHomework = getTodaysLessonQuery.getTodaysLessons(teacherId);

    TodaysLessonHomeworkDto todaysLessonHomeworkDto = TodaysLessonHomeworkDto.from(todaysLessonHomework);

    return ResponseEntity.ok(todaysLessonHomeworkDto);
  }
}
