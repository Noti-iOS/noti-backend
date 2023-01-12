package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.HomeworkPersistenceAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetFrequencyOfLessonsController {

  private final HomeworkPersistenceAdapter homeworkPersistenceAdapter;

  @GetMapping("/api/teacher/calendar/{year}/{month}")
  public ResponseEntity getFrequencyOfLessons(
      @PathVariable String year,
      @PathVariable String month) {

    System.out.println(homeworkPersistenceAdapter.toString());
    String yearMonth = new StringBuilder().append(year).append("-").append(month).toString();
    List<FrequencyOfLessonsDto> responseDto = homeworkPersistenceAdapter.findFrequencyOfLessons(yearMonth);

    return ResponseEntity.ok(responseDto);
  }


}
