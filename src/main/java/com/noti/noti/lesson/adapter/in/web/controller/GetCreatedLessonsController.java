package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.lesson.adapter.in.web.dto.response.CreatedLessonsDto;
import com.noti.noti.lesson.application.port.in.CreatedLessonCommand;
import com.noti.noti.lesson.application.port.in.GetCreatedLessonsQuery;
import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GetCreatedLessonsController {

  private final GetCreatedLessonsQuery getCreatedLessonsQuery;

  @GetMapping("/teacher/api")
  @Parameter(name = "UserDetails", hidden = true)
  public ResponseEntity<List<CreatedLessonsDto>> getLessonsByTeacherId(@AuthenticationPrincipal
      UserDetails userDetails) {
    Long teacherId = Long.parseLong(userDetails.getUsername());

    List<InCreatedLesson> inCreatedLessons = getCreatedLessonsQuery.createdLessons(new CreatedLessonCommand(teacherId));
    List<CreatedLessonsDto> dto = inCreatedLessons.stream().map(CreatedLessonsDto::new).collect(Collectors.toList());

    return ResponseEntity.ok(dto);
  }


}
