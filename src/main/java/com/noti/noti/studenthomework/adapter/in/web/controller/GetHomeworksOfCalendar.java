package com.noti.noti.studenthomework.adapter.in.web.controller;

import com.noti.noti.studenthomework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import com.noti.noti.studenthomework.adapter.out.persistence.StudentHomeworkAdapter;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHomeworksOfCalendar {

  private final StudentHomeworkAdapter studentHomeworkAdapter;

  @GetMapping("/api/teacher/calendar/{year}/{month}/{day}")
  @Parameter(name = "userDetails", hidden = true)
  ResponseEntity<List<HomeworkOfGivenDateDto>> getHomeworksOfCalendar(
      @PathVariable int year, @PathVariable int month, @PathVariable int day,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    long teacherId = Long.parseLong(userDetails.getUsername());
    LocalDate date = LocalDate.of(year, month, day);
    List<HomeworkOfGivenDateDto> responseDto = studentHomeworkAdapter.findHomeworksOfCalendar(
        date, teacherId);

    return ResponseEntity.ok(responseDto);
  }

}
